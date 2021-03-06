package jm.dto;

import jm.MessageService;
import jm.api.dao.ThreadChannelDAO;
import jm.api.dao.UserDAO;
import jm.model.ThreadChannel;
import jm.model.User;
import jm.model.message.ThreadChannelMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ThreadMessageDtoServiceImpl implements ThreadMessageDtoService {
    private UserDAO userDAO;
    private ThreadChannelDAO threadChannelDAO;
    private MessageService messageService;;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setThreadChannelDAO(ThreadChannelDAO threadChannelDAO) {
        this.threadChannelDAO = threadChannelDAO;
    }

    @Autowired
    public void setMessageService(MessageService messageService){
        this.messageService = messageService;
    }

    @Override
    public ThreadMessageDTO toDto(ThreadChannelMessage threadChannelMessage) {
        if (threadChannelMessage == null) {
            return null;
        }

        ThreadMessageDTO threadMessageDTO = new ThreadMessageDTO(threadChannelMessage);
        User user = threadChannelMessage.getUser();
        ThreadChannel threadChannel = threadChannelMessage.getThreadChannel();
        threadMessageDTO.setUserId(user.getId());
        threadMessageDTO.setUserName(user.getUsername());
        threadMessageDTO.setUserAvatarUrl(user.getAvatarURL());
        threadMessageDTO.setWorkspaceId(threadChannel.getMessage().getWorkspaceId());

        return threadMessageDTO;
    }

    @Override
    public List<ThreadMessageDTO> toDto(List<ThreadChannelMessage> threadChannelMessages) {
        if (threadChannelMessages == null || threadChannelMessages.isEmpty()) {
            return Collections.emptyList();
        }

        List<ThreadMessageDTO> threadMessageDTOList = new ArrayList<>();
        for (ThreadChannelMessage message: threadChannelMessages) {
            threadMessageDTOList.add(toDto(message));
        }

        return threadMessageDTOList;
    }

    @Override
    public ThreadChannelMessage toEntity(ThreadMessageDTO threadMessageDTO) {
        if (threadMessageDTO == null) {
            return null;
        }

        User user = userDAO.getById(threadMessageDTO.getUserId());
        ThreadChannel threadChannel = threadChannelDAO.getByChannelMessageId(threadMessageDTO.getParentMessageId());
        ThreadChannelMessage threadChannelMessage = new ThreadChannelMessage();
        threadChannelMessage.setUser(user);
        threadChannelMessage.setContent(threadMessageDTO.getContent());
        threadChannelMessage.setDateCreate(threadMessageDTO.getDateCreate());
        threadChannelMessage.setFilename(threadMessageDTO.getFilename());
        threadChannelMessage.setIsDeleted(threadMessageDTO.getIsDeleted());
        threadChannelMessage.setThreadChannel(threadChannel);
        threadChannelMessage.setParentMessage(messageService.getMessageById(threadMessageDTO.getParentMessageId()));
        threadChannelMessage.setWorkspaceId(threadMessageDTO.getWorkspaceId());

        return threadChannelMessage;
    }
}
