package br.com.bearbot.logs;

import br.com.bearbot.DAO.AddNewGuildDAO;
import br.com.bearbot.DAO.MusicChannelId;
import br.com.bearbot.DAO.VerifyExistenceDAO;
import br.com.bearbot.utils.ListOfAllCommands;
import br.com.bearbot.utils.Server;
import br.com.bearbot.utils.StatusUpdate;
import br.com.bearbot.utils.UTILS;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.List;

public class OnReady extends ListenerAdapter {
    public void onReady(ReadyEvent event) {
        new StatusUpdate();
        UTILS.GUILDS = new HashMap<Guild, Server>();
        UTILS.CONSOLE = new HashMap<User, Boolean>();
        new ListOfAllCommands();

        List<Guild> guildsList = event.getJDA().getGuilds();

        for (Guild guild : guildsList) {
            Server server = new Server();
            server.setMessagesId();
            UTILS.GUILDS.put(guild, server);

            VerifyExistenceDAO ver = new VerifyExistenceDAO();
            if (ver.checkExistence(guild.getIdLong())) {
            } else {
                try {
                    new AddNewGuildDAO(guild.getIdLong());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ver = null;

            UTILS.GUILDS.get(guild).setMsgCnn(guild.getDefaultChannel());

            MusicChannelId getId = new MusicChannelId();

            String id = getId.getIds(guild.getId());

            if (id == null) {
                UTILS.GUILDS.get(guild).setMusicChannel(null);
            } else {
                UTILS.GUILDS.get(guild).setMusicChannel(guild.getVoiceChannelById(id));
            }

        }

        event.getJDA().getTextChannelById("537965147625357313").sendMessage("✅ BearBot is ready.").queue();
    }

    public void onDisconnect(DisconnectEvent event) {
        event.getJDA().getTextChannelById("537965147625357313").sendMessage("BearBot is not ready.").queue();
    }
}