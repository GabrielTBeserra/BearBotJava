package br.com.bearbot.audiocore;

import br.com.bearbot.core.BearBot;
import br.com.bearbot.messages.MESSAGES;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class MusicSelector extends ListenerAdapter {
    private TrackManager mananger;
    private List<AudioTrack> tracks;
    private Member author;
    private Message msg;
    private Guild guild;
    private Long idMessage;
    private boolean isSelected;

    public MusicSelector(TrackManager mananger, List<AudioTrack> tracks, Member author, Guild guild, long idMessage, Message msg) {
        this.mananger = mananger;
        this.tracks = tracks;
        this.author = author;
        this.guild = guild;
        this.idMessage = idMessage;
        this.msg = msg;
        this.isSelected = false;
        BearBot.getBot().addEventListener(this);
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getMessageIdLong() != idMessage
                || event.getUser().isBot()
                || event.getMember().getVoiceState().getChannel() == null
                || this.author != event.getMember()
                || this.guild != event.getGuild()) {
            return;
        }


        if (event.getReactionEmote().getName().equals("🌓")) {
            this.mananger.queue(tracks.get(0), author, msg);
            removeEventAndListener(event);
            this.isSelected = true;
            MESSAGES.MUSIC_SELECT(tracks.get(0), event.getMember(), event.getChannel());
        } else if (event.getReactionEmote().getName().equals("🌔")) {
            this.isSelected = true;
            this.mananger.queue(tracks.get(1), author, msg);
            removeEventAndListener(event);
            MESSAGES.MUSIC_SELECT(tracks.get(1), event.getMember(), event.getChannel());
        } else if (event.getReactionEmote().getName().equals("🌕")) {
            this.mananger.queue(tracks.get(2), author, msg);
            removeEventAndListener(event);
            this.isSelected = true;
            MESSAGES.MUSIC_SELECT(tracks.get(2), event.getMember(), event.getChannel());
        } else if (event.getReactionEmote().getName().equals("🌖")) {
            this.mananger.queue(tracks.get(3), author, msg);
            removeEventAndListener(event);
            this.isSelected = true;
            MESSAGES.MUSIC_SELECT(tracks.get(3), event.getMember(), event.getChannel());
        } else if (event.getReactionEmote().getName().equals("❌")) {
            event.getChannel().sendMessage("Cancelando musica...").queue();
            removeEventAndListener(event);
            this.isSelected = true;
        }


    }

    private void removeEventAndListener(MessageReactionAddEvent event) {
        event.getChannel().deleteMessageById(this.idMessage).queue();
        BearBot.getBot().removeEventListener(this);
    }

    public boolean isSelected() {
        return this.isSelected;
    }


}
