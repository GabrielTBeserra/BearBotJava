package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import br.com.bearbot.core.BearBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping implements Commands {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        long ping = BearBot.bot.getGatewayPing();
        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " O ping do bot e de: " + ping).queue();
    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("Comando ping executado com sucesso por : " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());

    }

    @Override
    public String help() {
        return null;
    }

}
