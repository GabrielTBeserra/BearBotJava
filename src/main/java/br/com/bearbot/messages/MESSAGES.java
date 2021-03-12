package br.com.bearbot.messages;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class MESSAGES {
    public static void MUSIC_SELECT(AudioTrack audioTrack, Member author, Message msg) {
        EmbedBuilder embedMusicSelected = new EmbedBuilder();
        embedMusicSelected.setColor(Color.MAGENTA);

        embedMusicSelected.setDescription("Music selected 👉 `" + audioTrack.getInfo().title + "`");
        embedMusicSelected.setTimestamp(msg.getTimeCreated());
        embedMusicSelected.setFooter("Selecionado por: " + msg.getAuthor().getName(), msg.getAuthor().getAvatarUrl());
        msg.getChannel().sendMessage(embedMusicSelected.build()).queue();
    }

    public static void MUSIC_SELECT(AudioTrack audioTrack, Member author , MessageChannel channel) {
        EmbedBuilder embedMusicSelected = new EmbedBuilder();
        embedMusicSelected.setColor(Color.MAGENTA);

        embedMusicSelected.setDescription("Music selected 👉 `" + audioTrack.getInfo().title + "`");
        embedMusicSelected.setTimestamp(author.getTimeCreated());
        embedMusicSelected.setFooter("Selecionado por: " + author.getUser().getName(), author.getUser().getAvatarUrl());
        channel.sendMessage(embedMusicSelected.build()).queue();
    }

}
