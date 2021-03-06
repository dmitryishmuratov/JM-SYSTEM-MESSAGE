package jm;

import jm.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    List<Message> getAllMessages(Boolean isDeleted);

    List<Message> getMessagesByChannelId(Long id, Boolean isDeleted);

    List<Message> getMessagesByContent(String word, Boolean isDeleted);

    Message getMessageById(Long id);

    void createMessage(Message message);

    void deleteMessage(Long id);

    void updateMessage(Message message);

    List<Message> getMessagesByChannelIdForPeriod(Long id, LocalDateTime startDate, LocalDateTime endDate, Boolean isDeleted);

    List<Message> getMessagesByBotIdByChannelIdForPeriod(Long botId, Long channelId, LocalDateTime startDate, LocalDateTime endDate, Boolean isDeleted);

    List<Message> getStarredMessagesForUser(Long id, Boolean isDeleted);

    List<Message> getStarredMessagesForUserByWorkspaceId(Long userId, Long workspaceId, Boolean isDeleted);

    List<Message> getAllMessagesReceivedFromChannelsByUserId(Long userId, Boolean isDeleted);
}
