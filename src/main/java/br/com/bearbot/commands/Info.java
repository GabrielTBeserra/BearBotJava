package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class Info implements Commands {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle("BearBot", "https://imgur.com/a/wTa4rw4");
        info.addField("Version", "```5.1.0.2```", false);
        info.addField("Creator", "1Urso#8730", false);
        info.setColor(Color.ORANGE);

        event.getChannel().sendMessage(info.build()).queue();

    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("Comando info executado com sucesso por : " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());
    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return null;
    }

}
