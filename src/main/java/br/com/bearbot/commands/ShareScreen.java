package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class ShareScreen implements Commands {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String idServer = "";
        String idChannel = "";
        String channelName = "";

        try {
            idServer = event.getGuild().getId();
            idChannel = event.getMember().getVoiceState().getChannel().getId();
            channelName = event.getMember().getVoiceState().getChannel().getName();
        } catch (Exception e) {
            event.getChannel().sendMessage("Voce precisa esta conectado em um canal de voz para usar esse comando!")
                    .queue();
            return;
        }

        String url = "http://discordapp.com/channels/" + idServer + "/" + idChannel;

        EmbedBuilder share = new EmbedBuilder();
        share.setTitle("Share screen :: " + event.getMember().getVoiceState().getChannel());
        share.setColor(Color.MAGENTA);
        share.addField("URL: ", url, true);

        EmbedBuilder sharescreen = new EmbedBuilder();
        sharescreen.setTitle("ShareScreen ", event.getGuild().getIconUrl());
        sharescreen.setColor(Color.MAGENTA);
        sharescreen.setDescription("Canal de voz: " + channelName + "\n" + "Para entrar no compartilhamento >>"
                + " [CLIQUE AQUI](" + url + ")");
        sharescreen.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());
        sharescreen.setTimestamp(event.getMessage().getTimeCreated());
        event.getChannel().sendMessage(sharescreen.build()).queue();

    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("Comando sharescreen executado com sucesso por : " + event.getAuthor().getName()
                + ", Na guild: " + event.getGuild().getName() + " / " + event.getGuild().getIdLong());

    }

    @Override
    public String help() {
        return null;
    }

}
