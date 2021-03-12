package br.com.bearbot.events;

import br.com.bearbot.DAO.AddNewGuildDAO;
import br.com.bearbot.utils.Server;
import br.com.bearbot.utils.UTILS;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnGuildJoinInNewServer extends ListenerAdapter {
    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        System.out.println("Adicionando");
        try {
            Server server = new Server();
            server.setMessagesId();

            UTILS.GUILDS.put(event.getGuild(), server);
            new AddNewGuildDAO(event.getGuild().getIdLong());
            System.out.println("Entrou em um novo discord");
        } catch (Exception e) {
            System.out.println("Nao foi adicionado");
        }

        event.getGuild().getDefaultChannel().sendMessage("👽Cheguei!! Todas minhas notificacoes serao postadas aqui.")
                .queue();
    }
}
