import {
    ChannelRestPaginationService,
    DirectMessagesRestController,
    MessageRestPaginationService,
    SlashCommandRestPaginationService,
    StorageService,
    UserRestPaginationService,
    WorkspaceRestPaginationService
} from '/js/rest/entities-rest-pagination.js'
import {FileUploader} from "../FileUploader.js";
import {Command} from "./Command.js";
import {clearUsers, users} from "/js/searchUsersOnInputMessages.js";

export class SubmitMessage {
    user;
    channelID = 0;
    conversationID = 0;
    workspace;

    constructor() {
        this.workspace_service = new WorkspaceRestPaginationService();
        this.user_service = new UserRestPaginationService();
        this.channel_service = new ChannelRestPaginationService();
        this.message_service = new MessageRestPaginationService();
        this.direct_message_service = new DirectMessagesRestController();
        this.storage_service = new StorageService();
        this.slashCommandService = new SlashCommandRestPaginationService();
    }

    onAttachFileClick() {
        const file_uploader = new FileUploader();
        file_uploader.onAttachFileUpload();
        $('#attach_file').on('click', function () {
            file_uploader.selected_file.click();
        });
    }

    onMessageSubmit() {
        $("#form_message").submit(async (event) => {
            event.preventDefault();
            const hasCommand = await this.checkCommand();
            window.hasSlashCommand = await this.checkSlashCommand();
            if (!hasCommand) {
                const content = $("#form_message_input").val();
                if (content.startsWith('/leave ')) {
                    let channelName = content.substring(7);
                    this.leaveChannel(channelName);
                    $("#form_message_input").val("");
                    return
                }

                const channelID = sessionStorage.getItem("channelName");
                const conversationID = sessionStorage.getItem('conversation_id');

                if (channelID !== '0') {
                    this.sendChannelMessage(channelID);
                }

                if (conversationID !== '0') {
                    await this.sendDirectMessage(conversationID);
                }
            }
        });
    }

    async checkCommand() {
        await this.setUser();
        const commands = new Command(this.user);
        return commands.isCommand($("#form_message_input").val());
    }

    checkSlashCommand() {
        let message = $("#form_message_input").val();
        let isCommand = false;
        if (message.startsWith('/')) {
            window.allActions.forEach(action => {
                if (message.substr(1, message.indexOf(" ") < 0 ? message.length : message.indexOf(" ") - 1) === action) {
                    isCommand = true;
                }
            })
        }
        return isCommand;
    }

    getMessageInput() {
        const msg_input = $("#form_message_input");
        const msg = msg_input.val();
        msg_input.val("");
        return msg;
    }

    async getFiles() {
        const file_selector = $("#file_selector");
        const files = file_selector.prop('files')[0];

        if (files !== undefined) {
            const data = new FormData();
            data.append("file", files);
            await this.storage_service.uploadFile(data);
            file_selector.val("");
            $('#attached_file').html("");
            return files.name;
        }
        return null;
    }

    async getVoiceMessage() {
        const audioInput = $("#audioInput");
        const src = audioInput.prop('src');

        if (src !== undefined) {
            let blob = await fetch(src).then(r => r.blob());
            let arrayBuffer = await blob.arrayBuffer();
            let base64 = await btoa(String.fromCharCode(...new Uint8Array(arrayBuffer)));
            $('#inputMe').html("");

            return base64;
        }
        return null;
    }

    async sendChannelMessage(channelID) {
        this.channelID = channelID;
        await this.setUser();
        await this.setWorkspace();

        this.createEntityForChannelMessage().then(entity => {
            if (entity.content !== "" || entity.filename !== null || entity.voiceMessage !== null) {
                this.message_service.create(entity).then(
                    message => sendName(message)
                );
            }

            if (window.hasSlashCommand) {
                await this.sendSlashCommand(entity);
            } else {
                await this.message_service.create(entity).then(
                    msg_id => sendName(msg_id)
                );
            }
            clearUsers();
        });
    }

    async sendDirectMessage(conversation_id) {
        this.conversationID = conversation_id;
        await this.setUser();
        await this.setWorkspace();

        this.createEntityForDirectMessage().then(entity => {
            if (entity.content !== "" || entity.filename !== null || entity.voiceMessage !== null) {
                this.direct_message_service.create(entity).then(
                    message => sendDM(message)
                );
            }
        });
    }

    async sendSlashCommand(entity) {
        if (entity.content.startsWith("/")) {
            const inputCommand = entity.content.slice(1, entity.content.indexOf(" ") < 0 ? entity.content.length : entity.content.indexOf(" "));
            window.currentCommands.forEach(command => {
                if (command.name === inputCommand) {
                    const sendCommand = {
                        channelId: entity.channelId,
                        userId: entity.userId,
                        command: entity.content,
                        name: inputCommand,
                        botId: command.botId,
                        url: command.url
                    };
                    sendSlackBotCommand(sendCommand);
                    // if (command.botId === 1) {
                    //     //если это команда от слакБота, то отправляем через вебсокет.
                    //     sendSlackBotCommand(sendCommand);
                    // } else {
                    //     //иначе просто отправляем пост запрос по урлу
                    //     this.slashCommandService.sendSlashCommand(command.url, sendCommand);
                    // }
                }
            });
        }
    }

    async setUser() {
        await this.user_service.getLoggedUser().then(
            user => this.user = user
        );
    }

    async setChannelByName(channelName) {
        await this.channel_service.getChannelByName(channelName).then(
            channel => this.channel = channel
        )
    }

    async setWorkspace() {
        await this.workspace_service.getChosenWorkspace().then(
            workspace => this.workspace = workspace
        )
    }

    async leaveChannel(channelName) {
        await this.setUser();
        await this.setChannelByName(channelName);
        await this.setWorkspace();
        const channelUsers = this.channel.userIds;
        channelUsers.splice(channelUsers.indexOf(this.user.id), 1);

        const entity = this.createEntityForChannelMessage();

        await this.channel_service.update(entity).then(() => {
            $(".p-channel_sidebar__channels__list").html('');
            this.renewChannels(this.workspace.id, this.user.id)
        })
    }

    async renewChannels(workspace_id, user_id) {
        await this.channel_service.getChannelsByWorkspaceAndUser(workspace_id, user_id).then(
            channels => {
                let firstChannelId = 0;
                channels.forEach(function (channel, i) {
                    if (i === 0) {
                        firstChannelId = channel.id
                    }
                    $('#id-channel_sidebar__channels__list')
                        .append(`<div class="p-channel_sidebar__channel">
                                    <button class="p-channel_sidebar__name_button" id="channel_button_${channel.id}" value="${channel.id}">
                                        <i class="p-channel_sidebar__channel_icon_prefix">#</i>
                                        <span class="p-channel_sidebar__name-3" id="channel_name_${channel.id}">${channel.name}</span>
                                    </button>
                                  </div>`
                        );
                });
                pressChannelButton(firstChannelId);
                sessionStorage.setItem("channelName", firstChannelId);
                let channel_name = document.getElementById("channel_name_" + firstChannelId).textContent;
                $(".p-classic_nav__model__title__info__name").html("").text(channel_name);
                sessionStorage.setItem('conversation_id', '0');
            }
        )
    }

    async createEntityForChannelMessage() {
        return {
            id: null,
            channelId: this.channelID,
            user: {
                id: this.user.id,
                name: this.user.name
            },
            content: this.getMessageInput(),
            dateCreate: convert_date_to_format_Json(new Date()),
            filename: await this.getFiles(),
            voiceMessage: await this.getVoiceMessage(),
            workspaceId: this.workspace.id
        };
    }

    async createEntityForDirectMessage() {
        return {
            id: null,
            channelId: this.channelID,
            user: {
                id: this.user.id,
                name: this.user.name
            },
            conversation: {
                id: this.conversationID
            },
            content: this.getMessageInput(),
            dateCreate: convert_date_to_format_Json(new Date()),
            filename: await this.getFiles(),
            voiceMessage: await this.getVoiceMessage(),
            workspaceId: this.workspace.id
        };
    }
}