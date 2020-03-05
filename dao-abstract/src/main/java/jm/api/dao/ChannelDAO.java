package jm.api.dao;

import jm.dto.ChannelDTO;
import jm.model.Channel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChannelDAO {

    List<Channel> getAll ();

    List<ChannelDTO> getAllChanelDTO ();

    void persist (Channel channel);

    void deleteById (Long id);

    Channel merge (Channel channel);

    Channel getById (Long id);

    Channel getChannelByName (String name);

    List<Channel> getChannelsByOwnerId (Long ownerId);

    List<Channel> getChannelsByWorkspaceId (Long id);

    List<ChannelDTO> getChannelDtoListByWorkspaceId (Long workspaceId);

    List<Channel> getChannelsByUserId (Long userId);

    List<ChannelDTO> getChannelDtoListByUserId (Long userId);

    List<Channel> getChannelsByIds (Set<Long> ids);

    Optional<ChannelDTO> getIdByName (String name);

    Optional<ChannelDTO> getChannelDTOByName (String name);

    Optional<ChannelDTO> getChannelDTOById (Long id);

    List<ChannelDTO> getChannelByWorkspaceAndUser (Long workspaceId, Long userId);


}
