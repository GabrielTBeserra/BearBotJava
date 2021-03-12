package br.com.bearbot.events;

import br.com.bearbot.DAO.AddNewUserDAO;
import br.com.bearbot.DAO.VerifyExistenceUserDAO;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserEvents extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (event.getUser().isBot())
            return;

        VerifyExistenceUserDAO user = new VerifyExistenceUserDAO();

        if (!user.CheckExistenceUserDAO(event.getGuild().getIdLong(), event.getMember().getUser().getIdLong())) {
            try {
                new AddNewUserDAO(event.getMember().getUser().getIdLong(), event.getGuild().getIdLong(), 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        user = null;


        event.getGuild().getDefaultChannel().sendMessage(
                "Seja bem vindo " + event.getMember().getAsMention() + " ao server " + event.getGuild().getName()).queue();

    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        event.getGuild().getDefaultChannel()
                .sendMessage("O usuario " + event.getUser().getName() + " acabou de sair!").queue();
    }

}
