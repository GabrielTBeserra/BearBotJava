package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import br.com.bearbot.core.BearBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class Invite implements Commands {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder invite = new EmbedBuilder();
        invite.setTitle("Invite the bot from your server");
        invite.addField("Invite",
                "[Click here](https://discordapp.com/oauth2/authorize?client_id=592885875277299733&permissions=8&scope=bot)",
                false);
        invite.setColor(Color.RED);

        if (event.isFromType(ChannelType.PRIVATE)) {
            event.getChannel().sendMessage(invite.build()).queue();
        } else {
            event.getChannel()
                    .sendMessage(new EmbedBuilder().setTitle("Sucesso!!")
                            .addField("Enviei para o seu DM", event.getMember().getAsMention(), false)
                            .setColor(Color.GREEN).build())
                    .queue();
            long privateId = event.getAuthor().openPrivateChannel().complete().getIdLong();
            BearBot.bot.getPrivateChannelById(privateId).sendMessage(invite.build()).queue();
        }

    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("Comando invite executado com sucesso por : " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());

    }

    @Override
    public String help() {
        return null;
    }

}
