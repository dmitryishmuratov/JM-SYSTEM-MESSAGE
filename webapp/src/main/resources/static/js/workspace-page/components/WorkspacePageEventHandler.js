import {refreshMemberList} from "/js/member-list/member-list.js";
import {ChannelRestPaginationService} from "/js/rest/entities-rest-pagination.js";
import {NavHeader} from "./navbar/NavHeader.js";
import {UserRestPaginationService} from "/js/rest/entities-rest-pagination.js";
import {WorkspaceRestPaginationService} from "/js/rest/entities-rest-pagination.js";

export class WorkspacePageEventHandler {

    constructor(logged_user) {
        this.logged_user = logged_user;
        this.addChannelModal = $("#addChannelModal");
        this.addDirectMessageModal = $("#addDirectMessageModal");
        this.addChannelBtn = $("#addChannelButton");
        this.addDirectMessage = $("#addDirectMessage");
        this.channel_service = new ChannelRestPaginationService();
        this.user_service = new UserRestPaginationService();
        this.wks_header = new NavHeader();
        this.user_service = new UserRestPaginationService();
    }

    onAddChannelClick() {
        this.addChannelBtn.click(() => {
            this.addChannelModal.css('display', 'block');
        });
    }

    onAddDirectMessageClick() {
        this.addDirectMessage.click(() => {
            new WorkspaceRestPaginationService().getChosenWorkspace().then(workspace => {
                this.user_service.getUsersByWorkspace(workspace.id).then(users => {
                        let data = "<div class=\"list-group\">\n";
                        users.forEach(user => {
                            data += "<a class=\"list-group-item list-group-item-action\">";
                            data += user.name.toString();
                            data += "</a>\n";
                        });
                        data += "</div>";
                        $('#addDirectMessageModal').find('#DirectMessageTo').html(data);
                    }
                );
                this.addDirectMessageModal.css('display', 'block');
            });
        });
    }

    onWindowClick() {
        $(window).click((event) => {
            if (event.target === this.addChannelBtn) {
                this.addChannelModal.css('display', 'none');
                this.addDirectMessageModal.css('display', 'none');
            }
        })
    }

    onSelectChannel() {
        $(".p-channel_sidebar__channels__list").on("click", "button.p-channel_sidebar__name_button", (event) => {
            this.wks_header.setChannelTitle($(event.currentTarget).find('i').text(), $(event.currentTarget).find('span').text()).setInfo();


            const channelId = parseInt($(event.currentTarget).val());
            pressChannelButton(channelId);

            sessionStorage.setItem("channelName", channelId);
            sessionStorage.setItem('conversation_id', '0');

            this.user_service.getUsersByChannelId(channelId).then(users => {
                this.wks_header.setInfo(users.length, 666);
            });

            refreshMemberList();
        });
    }

    onAddChannelSubmit() {
        $("#addChannelSubmit").click(() => {
            const entity = {
                name: $('#exampleInputChannelName').val(),
                isPrivate: $('#exampleCheck1').is(':checked'),
                createdDate: this.getFormattedCreateDate(),
                ownerId: this.logged_user.id
            };

            this.channel_service.getChannelByName(entity.name).then(chn => {
                if (typeof (chn) === 'undefined') {
                    this.channel_service.create(entity).then(chn => {
                        sendChannel(chn);
                    })
                } else {
                    alert('That name is already taken by a channel, username, or user group.');
                }
            });
        });
    }

    getFormattedCreateDate() {
        const date = new Date();
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        };
        return date.toLocaleString("ru", options).replace(/,/g, "");
    }
}