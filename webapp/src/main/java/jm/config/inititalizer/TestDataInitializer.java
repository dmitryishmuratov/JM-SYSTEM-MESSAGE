package jm.config.inititalizer;

import jm.ConversationService;
import jm.DirectMessageService;
import jm.UserService;
import jm.api.dao.*;
import jm.model.*;
import jm.model.message.DirectMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TestDataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(TestDataInitializer.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ChannelDAO channelDAO;
    @Autowired
    private MessageDAO messageDAO;
    @Autowired
    private WorkspaceDAO workspaceDAO;
    @Autowired
    private BotDAO botDAO;
    @Autowired
    private WorkspaceUserRoleDAO workspaceUserRoleDAO;
    @Autowired
    private SlashCommandDao slashCommandDao;

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private DirectMessageService directMessageService;

    private Set<Role> roles = new HashSet<>();
    private Set<User> users = new HashSet<>();
    private Set<Channel> channels = new HashSet<>();
    private Set<Message> messages = new HashSet<>();
    private Set<Workspace> workspaces = new HashSet<>();
    private Set<Bot> bots = new HashSet<>();
    private Set<SlashCommand> sc = new HashSet<>();

    private Set<Conversation> conversations = new HashSet<>();
    private Set<DirectMessage> directMessages = new HashSet<>();

    @AllArgsConstructor
    private enum UserData {
        JOHN("John", "Doe", "login_1", "pass_1", "john.doe@testmail.com"),
        STEPAN("Степан", "Сидоров", "login_2", "pass_2", "sidorov@testmail.com"),
        PETR("Петр", "Петров", "login_3", "pass_3", "petrov@testmail.com"),
        FOO("foo", "bar", "login_4", "pass_4", "foobar@testmail.com"),
        JAMES("James", "Smith", "login_5", "pass_5", "smith@testmail.com");

        private final String name;
        private final String lastName;
        private final String login;
        private final String password;
        private final String email;
    }

    private void init() {
        logger.info("Data init has been started!!!");
        dataInit();
        logger.info("Data init has been done!!!");
    }

    private void dataInit() {
        createRoles();
        createUsers();
        createSlashCommands();

        createWorkspaces();
        createBots();
        createChannels();
        createMessages();
        createLinkRoles();

        createConversations();
        createDirectMessages();
    }

    private void createSlashCommands(){
        SlashCommand topicChangeCommand = new SlashCommand();
        topicChangeCommand.setName("topic");
        topicChangeCommand.setUrl("/app/bot/slackbot/");
        topicChangeCommand.setDescription("test description");
        topicChangeCommand.setHints("test Hints");
        slashCommandDao.persist(topicChangeCommand);
        sc.add(topicChangeCommand);

        SlashCommand directMessageCommand = new SlashCommand();
        directMessageCommand.setName("dm");
        directMessageCommand.setUrl("/app/bot/slackbot");
        directMessageCommand.setDescription("DM description");
        directMessageCommand.setHints("DM Hints");
        slashCommandDao.persist(directMessageCommand);
        sc.add(directMessageCommand);

        SlashCommand leaveCommand = new SlashCommand();
        leaveCommand.setName("leave");
        leaveCommand.setUrl("/app/bot/slackbot");
        leaveCommand.setDescription("Leave description");
        leaveCommand.setHints("Leave Hints");
        slashCommandDao.persist(leaveCommand);
        sc.add(leaveCommand);

        SlashCommand joinCommand = new SlashCommand();
        joinCommand.setName("join");
        joinCommand.setUrl("/app/bot/slackbot");
        joinCommand.setDescription("Join description");
        joinCommand.setHints("Join Hints");
        slashCommandDao.persist(joinCommand);
        sc.add(joinCommand);

        SlashCommand openCommand = new SlashCommand();
        openCommand.setName("open");
        openCommand.setUrl("/app/bot/slackbot");
        openCommand.setDescription("Open description");
        openCommand.setHints("Open Hints");
        slashCommandDao.persist(openCommand);
        sc.add(openCommand);

        SlashCommand shrugCommand = new SlashCommand();
        shrugCommand.setName("shrug");
        shrugCommand.setUrl("/app/bot/slackbot");
        shrugCommand.setDescription("Shrug description");
        shrugCommand.setHints("Shrug Hints");
        slashCommandDao.persist(shrugCommand);
        sc.add(shrugCommand);

        SlashCommand inviteCommand = new SlashCommand();
        inviteCommand.setName("invite");
        inviteCommand.setUrl("/app/bot/slackbot");
        inviteCommand.setDescription("Invite description");
        inviteCommand.setHints("Invite Hints");
        slashCommandDao.persist(inviteCommand);
        sc.add(inviteCommand);

        SlashCommand whoCommand = new SlashCommand();
        whoCommand.setName("who");
        whoCommand.setUrl("/app/bot/slackbot");
        whoCommand.setDescription("Who description");
        whoCommand.setHints("Who Hints");
        slashCommandDao.persist(whoCommand);
        sc.add(whoCommand);

        SlashCommand kickCommand = new SlashCommand();
        kickCommand.setName("kick");
        kickCommand.setUrl("/app/bot/slackbot");
        kickCommand.setDescription("Kick description");
        kickCommand.setHints("Kick Hints");
        slashCommandDao.persist(kickCommand);
        sc.add(kickCommand);

        SlashCommand removeCommand = new SlashCommand();
        removeCommand.setName("remove");
        removeCommand.setUrl("/app/bot/slackbot");
        removeCommand.setDescription("Remove description");
        removeCommand.setHints("Remove Hints");
        slashCommandDao.persist(removeCommand);
        sc.add(removeCommand);

        SlashCommand msgCommand = new SlashCommand();
        msgCommand.setName("msg");
        msgCommand.setUrl("/app/bot/slackbot");
        msgCommand.setDescription("Msg description");
        msgCommand.setHints("Msg Hints");
        slashCommandDao.persist(msgCommand);
        sc.add(msgCommand);

        SlashCommand renameCommand = new SlashCommand();
        renameCommand.setName("rename");
        renameCommand.setUrl("/app/bot/slackbot");
        renameCommand.setDescription("Rename description");
        renameCommand.setHints("Rename Hints");
        slashCommandDao.persist(renameCommand);
        sc.add(renameCommand);

        SlashCommand archiveCommand = new SlashCommand();
        archiveCommand.setName("archive");
        archiveCommand.setUrl("/app/bot/slackbot");
        archiveCommand.setDescription("Rename description");
        archiveCommand.setHints("Rename Hints");
        slashCommandDao.persist(archiveCommand);
        sc.add(archiveCommand);

        SlashCommand invitePeopleCommand = new SlashCommand();
        invitePeopleCommand.setName("invite_people");
        invitePeopleCommand.setUrl("/app/bot/slackbot");
        invitePeopleCommand.setDescription("Invite_people description");
        invitePeopleCommand.setHints("Invite_people Hints");
        slashCommandDao.persist(invitePeopleCommand);
        sc.add(invitePeopleCommand);
    }

    private void createRoles() {
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleDAO.persist(userRole);
        this.roles.add(userRole);

        Role ownerRole = new Role();
        ownerRole.setRole("ROLE_OWNER");
        roleDAO.persist(ownerRole);
        this.roles.add(ownerRole);
    }

    private void createUsers() {
        Set<Role> userRoleSet = this.roles.stream()
                .filter(role -> "ROLE_USER".equals(role.getAuthority())).collect(Collectors.toSet());

        Set<Role> ownerRoleSet = this.roles.stream()
                .filter(role -> "ROLE_OWNER".equals(role.getAuthority())).collect(Collectors.toSet());

        User userJohn = new User();

        userJohn.setName(UserData.JOHN.name);
        userJohn.setLastName(UserData.JOHN.lastName);
        userJohn.setLogin(UserData.JOHN.login);
        userJohn.setEmail(UserData.JOHN.email);
        userJohn.setPassword(UserData.JOHN.password);
        userJohn.setDisplayName(UserData.JOHN.name + " " + UserData.JOHN.lastName);
        userJohn.setRoles(ownerRoleSet);

        userService.createUser(userJohn);
        this.users.add(userJohn);

        User userStepan = new User();

        userStepan.setName(UserData.STEPAN.name);
        userStepan.setLastName(UserData.STEPAN.lastName);
        userStepan.setLogin(UserData.STEPAN.login);
        userStepan.setEmail(UserData.STEPAN.email);
        userStepan.setPassword(UserData.STEPAN.password);
        userStepan.setDisplayName(UserData.STEPAN.name + " " + UserData.STEPAN.lastName);
        userStepan.setRoles(userRoleSet);

        userService.createUser(userStepan);
        this.users.add(userStepan);

        User userPetr = new User();

        userPetr.setName(UserData.PETR.name);
        userPetr.setLastName(UserData.PETR.lastName);
        userPetr.setLogin(UserData.PETR.login);
        userPetr.setEmail(UserData.PETR.email);
        userPetr.setPassword(UserData.PETR.password);
        userPetr.setDisplayName(UserData.PETR.name + " " + UserData.PETR.lastName);
        userPetr.setRoles(userRoleSet);

        userService.createUser(userPetr);
        this.users.add(userPetr);

        User userFoo = new User();

        userFoo.setName(UserData.FOO.name);
        userFoo.setLastName(UserData.FOO.lastName);
        userFoo.setLogin(UserData.FOO.login);
        userFoo.setEmail(UserData.FOO.email);
        userFoo.setPassword(UserData.FOO.password);
        userFoo.setDisplayName(UserData.FOO.name + " " + UserData.FOO.lastName);
        userFoo.setRoles(userRoleSet);

        userService.createUser(userFoo);
        this.users.add(userFoo);

        User userJames = new User();

        userJames.setName(UserData.JAMES.name);
        userJames.setLastName(UserData.JAMES.lastName);
        userJames.setLogin(UserData.JAMES.login);
        userJames.setEmail(UserData.JAMES.email);
        userJames.setPassword(UserData.JAMES.password);
        userJames.setDisplayName(UserData.JAMES.name + " " + UserData.JAMES.lastName);
        userJames.setRoles(userRoleSet);

        userService.createUser(userJames);
        this.users.add(userJames);
    }

    private void createChannels() {
        List<User> userList = new ArrayList<>(this.users);
        List<Workspace> workspaceList = new ArrayList<>(workspaces);

        Set<User> userSet = new HashSet<>();
        userSet.add(userList.get(0));
        userSet.add(userList.get(1));

        Channel channelGeneral = new Channel();
        channelGeneral.setName("general");
        channelGeneral.setUsers(this.users);
        channelGeneral.setUser(userList.get(0));
        channelGeneral.setIsPrivate(false);
        channelGeneral.setCreatedDate(LocalDateTime.now());
        channelGeneral.setWorkspace(workspaceList.get(0));
        channelGeneral.setIsApp(false);

        channelDAO.persist(channelGeneral);
        this.channels.add(channelGeneral);

        Channel channelRandom = new Channel();
        channelRandom.setName("random");
        channelRandom.setUsers(userSet);
        channelRandom.setUser(userList.get(0));
        channelRandom.setIsPrivate(false);
        channelRandom.setCreatedDate(LocalDateTime.now());
        channelRandom.setWorkspace(workspaceList.get(0));
        channelRandom.setIsApp(false);

        channelDAO.persist(channelRandom);
        this.channels.add(channelRandom);

        Channel channel3 = new Channel();
        channel3.setName("channel_3");
        channel3.setUsers(userSet);
        channel3.setUser(userList.get(2));
        channel3.setIsPrivate(true);
        channel3.setCreatedDate(LocalDateTime.now());
        channel3.setWorkspace(workspaceList.get(1));
        channel3.setIsApp(false);

        channelDAO.persist(channel3);
        this.channels.add(channel3);


    }

    private void createMessages() {
        List<User> userList = new ArrayList<>(this.users);
        List<Channel> channels = new ArrayList<>(this.channels);
        List<Bot> bots = new ArrayList<>(this.bots);

        Message message1 = new Message();
        message1.setChannelId(channels.get(0).getId());
        message1.setUser(userList.get(0));
        message1.setContent("Hello from " + userList.get(0).getDisplayName());
        message1.setDateCreate(LocalDateTime.now());
        message1.setWorkspaceId(1L);

        messageDAO.persist(message1);
        this.messages.add(message1);

        Message message2 = new Message();
        message2.setChannelId(channels.get(1).getId());
        message2.setUser(userList.get(1));
        message2.setContent("Hello from " + userList.get(1).getDisplayName());
        message2.setDateCreate(LocalDateTime.now());
        message2.setWorkspaceId(2L);

        messageDAO.persist(message2);
        this.messages.add(message2);

        Message message3 = new Message();
        message3.setChannelId(channels.get(2).getId());
        message3.setUser(userList.get(2));
        message3.setContent("Hello from " + userList.get(2).getDisplayName());
        message3.setDateCreate(LocalDateTime.now());
        message3.setWorkspaceId(1L);

        messageDAO.persist(message3);
        this.messages.add(message3);

        Message message4 = new Message();
        message4.setChannelId(channels.get(1).getId());
        message4.setBot(bots.get(0));
        message4.setContent("Hello from BOT!");
        message4.setDateCreate(LocalDateTime.now());
        message4.setWorkspaceId(2L);

        messageDAO.persist(message4);
        this.messages.add(message4);

        Message message5 = new Message();
        message5.setChannelId(channels.get(2).getId());
        message5.setUser(userList.get(1));
        message5.setContent("@John hello everybody!");
        message5.setDateCreate(LocalDateTime.now());
        message5.setRecipientUsers(Collections.singleton(userService.getUserById(1L)));
        message5.setWorkspaceId(1L);

        messageDAO.persist(message5);
        this.messages.add(message5);
    }

    private void createWorkspaces() {
        User userJohn = this.users.stream()
                .filter(user -> UserData.JOHN.name.equals(user.getName()))
                .findFirst()
                .orElse(this.users.iterator().next());
        User userStepan = this.users.stream()
                .filter(user -> UserData.STEPAN.name.equals(user.getName()))
                .findFirst()
                .orElse(this.users.iterator().next());

        Workspace workspace1 = new Workspace();
        workspace1.setName("workspace-0");
        workspace1.setUsers(this.users);
        workspace1.setUser(userJohn);
        workspace1.setIsPrivate(false);
        workspace1.setCreatedDate(LocalDateTime.now());
        workspace1.setGoogleClientId("270266382009-o2j9h70k4q0io74df8pm8pla4vko75pq.apps.googleusercontent.com");
        workspace1.setGoogleClientSecret("256bTZoiZUa6eKiGVmI-T4wb");

        workspaceDAO.persist(workspace1);
        this.workspaces.add(workspace1);

        Workspace workspace2 = new Workspace();
        workspace2.setName("workspace-1");
        workspace2.setUsers(this.users);
        workspace2.setUser(userStepan);
        workspace2.setIsPrivate(true);
        workspace2.setCreatedDate(LocalDateTime.now());
        workspace2.setGoogleClientId("270266382009-o2j9h70k4q0io74df8pm8pla4vko75pq.apps.googleusercontent.com");
        workspace2.setGoogleClientSecret("256bTZoiZUa6eKiGVmI-T4wb");

        workspaceDAO.persist(workspace2);
        this.workspaces.add(workspace2);

        Workspace workspace3 = new Workspace();
        workspace3.setName("workspace-2");
        workspace3.setUsers(this.users);
        workspace3.setUser(userJohn);
        workspace3.setIsPrivate(false);
        workspace3.setCreatedDate(LocalDateTime.now());
        workspace3.setGoogleClientId("");
        workspace3.setGoogleClientSecret("");

        workspaceDAO.persist(workspace3);
        this.workspaces.add(workspace3);
    }

    private void createBots() {
        Bot zoom = new Bot("zoom", "Zoom", LocalDateTime.now());
        zoom.getWorkspaces().add(workspaceDAO.getById(1L));


        Bot slackBot = new Bot("bot_2", "SlackBot", LocalDateTime.now());
        slackBot.getWorkspaces().add(workspaceDAO.getById(1L));
        sc.forEach(command -> {
            slackBot.getCommands().add(command);
        });


        this.bots.add(slackBot);
        this.bots.add(zoom);

        botDAO.persist(slackBot);
        botDAO.persist(zoom);

        sc.forEach(command -> {
            command.setBot(slackBot);
            slashCommandDao.merge(command);
        });

    }

    private void createLinkRoles() {
        Role userRole = null;
        for (Role role : this.roles) {
            if ("ROLE_USER".equals(role.getAuthority())) {
                userRole = role;
            }
        }
        Role ownerRole = null;
        for (Role role : this.roles) {
            if ("ROLE_OWNER".equals(role.getAuthority())) {
                ownerRole = role;
            }
        }

        for (User user : this.users) {
            for (Workspace workspace : this.workspaces) {
                if (user.getId().equals(workspace.getId())) {
                    createWorkspaceUserRole(workspace, user, ownerRole);
                } else {
                    createWorkspaceUserRole(workspace, user, userRole);
                }
            }
        }
    }

    private void createWorkspaceUserRole(Workspace workspace, User user, Role role) {
        WorkspaceUserRole workspaceUserRole = new WorkspaceUserRole();
        workspaceUserRole.setWorkspace(workspace);
        workspaceUserRole.setUser(user);
        workspaceUserRole.setRole(role);
        workspaceUserRoleDAO.persist(workspaceUserRole);
    }

    private void createDirectMessages() {
        User user1 = this.userService.getUserById(1L);
        User user2 = this.userService.getUserById(2L);
        User user3 = this.userService.getUserById(3L);
        User user4 = this.userService.getUserById(4L);
        User user5 = this.userService.getUserById(5L);

        Conversation conversation1 = this.conversationService.getConversationById(1L);
        Conversation conversation2 = this.conversationService.getConversationById(2L);
        Conversation conversation3 = this.conversationService.getConversationById(3L);

        DirectMessage dm1 = new DirectMessage();
        dm1.setConversation(conversation1);
        dm1.setUser(user1);
        dm1.setContent("Direct message #1");
        dm1.setDateCreate(LocalDateTime.now());
        dm1.setWorkspaceId(1L);
        this.directMessageService.saveDirectMessage(dm1);
        this.directMessages.add(dm1);

        DirectMessage dm2 = new DirectMessage();
        dm2.setConversation(conversation1);
        dm2.setUser(user2);
        dm2.setContent("Direct message #2");
        dm2.setDateCreate(LocalDateTime.now());
        dm2.setWorkspaceId(1L);
        this.directMessageService.saveDirectMessage(dm2);
        this.directMessages.add(dm2);

        DirectMessage dm3 = new DirectMessage();
        dm3.setConversation(conversation2);
        dm3.setUser(user1);
        dm3.setContent("Direct message #3");
        dm3.setDateCreate(LocalDateTime.now());
        dm3.setWorkspaceId(1L);
        this.directMessageService.saveDirectMessage(dm3);
        this.directMessages.add(dm3);

        DirectMessage dm4 = new DirectMessage();
        dm4.setConversation(conversation3);
        dm4.setUser(user2);
        dm4.setContent("Direct message #4");
        dm4.setDateCreate(LocalDateTime.now());
        this.directMessageService.saveDirectMessage(dm4);
        this.directMessages.add(dm4);
    }

    private void createConversations() {
        User user1 = this.userService.getUserById(1L);
        User user2 = this.userService.getUserById(2L);
        User user3 = this.userService.getUserById(3L);
        User user4 = this.userService.getUserById(4L);
        User user5 = this.userService.getUserById(5L);

        Workspace workspace = this.workspaceDAO.getById(1L);

        Conversation conversation1 = new Conversation();
        conversation1.setOpeningUser(user1);
        conversation1.setAssociatedUser(user2);
        conversation1.setShowForOpener(true);
        conversation1.setShowForAssociated(true);
        conversation1.setWorkspace(workspace);

        conversationService.createConversation(conversation1);
        this.conversations.add(conversation1);

        //this conversation must not created because already exists
        Conversation conversation2 = new Conversation();
        conversation2.setOpeningUser(user2);
        conversation2.setAssociatedUser(user1);
        conversation2.setShowForOpener(true);
        conversation2.setShowForAssociated(false);
        conversation2.setWorkspace(workspace);

        conversationService.createConversation(conversation2);
        this.conversations.add(conversation2);

        Conversation conversation3 = new Conversation();
        conversation3.setOpeningUser(user1);
        conversation3.setAssociatedUser(user3);
        conversation3.setShowForOpener(true);
        conversation3.setShowForAssociated(true);
        conversation3.setWorkspace(workspace);

        conversationService.createConversation(conversation3);
        this.conversations.add(conversation3);

        Conversation conversation4 = new Conversation();
        conversation4.setOpeningUser(user2);
        conversation4.setAssociatedUser(user3);
        conversation4.setShowForOpener(true);
        conversation4.setShowForAssociated(true);
        conversation4.setWorkspace(workspace);

        conversationService.createConversation(conversation4);
        this.conversations.add(conversation4);
    }
}