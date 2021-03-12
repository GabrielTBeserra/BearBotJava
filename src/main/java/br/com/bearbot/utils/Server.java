package br.com.bearbot.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private Guild guild;
    private int musicOption;
    private List<Long> messagesId;
    private MessageChannel musicTextChannel;
    private TextChannel notificationChannel;
    private VoiceChannel musicChannel;

    public MessageChannel getMusicTextChannel() {
        return musicTextChannel;
    }

    public void setMusicTextChannel(MessageChannel musicTextChannel) {
        this.musicTextChannel = musicTextChannel;
    }

    public VoiceChannel getMusicChannel() {
        return musicChannel;
    }

    public void setMusicChannel(VoiceChannel musicChannel) {
        this.musicChannel = musicChannel;
    }

    public MessageChannel getMsgCnn() {
        return musicTextChannel;
    }

    public void setMsgCnn(MessageChannel msgCnn) {
        this.musicTextChannel = msgCnn;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public int getMusicOption() {
        return musicOption;
    }

    public void setMusicOption(int musicOption) {
        this.musicOption = musicOption;
    }

    public List<Long> getMessagesId() {
        return messagesId;
    }

    public void setMessagesId(List<Long> messagesId) {
        this.messagesId = messagesId;
    }

    public void setMessagesId() {
        this.messagesId = new ArrayList<Long>();
    }

    public TextChannel getNotificationChannel() {
        return notificationChannel;
    }

    public void setNotificationChannel(TextChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
    }
}
