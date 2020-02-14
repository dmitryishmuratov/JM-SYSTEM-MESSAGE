package jm.dao;

import jm.api.dao.BotDAO;
import jm.model.Bot;
import jm.model.Channel;
import jm.model.SlashCommand;
import jm.model.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class BotDAOImpl extends AbstractDao<Bot> implements BotDAO {
    private static final Logger logger = LoggerFactory.getLogger(BotDAOImpl.class);

    @Override
    public List<Bot> getBotsByWorkspaceId(Workspace workspace) {
        try {
            return (List<Bot>) entityManager.createNativeQuery("SELECT b.* FROM workspaces_bots wb JOIN bots b ON b.id = wb.bot_id WHERE wb.workspace_id=?", Bot.class)
                    .setParameter(1, workspace.getId())
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Set<Channel> getChannels(Bot bot) {
        return bot.getChannels();
    }

    @Override
    public Bot getBotByCommand(SlashCommand slashCommand) {
        try {
            return (Bot) entityManager.createNativeQuery("SELECT b.* FROM bots_slash_commands bc JOIN bots b ON b.id = bc.bot_id WHERE bc.slash_command_id=?", Bot.class)
                    .setParameter(1, slashCommand.getId())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}