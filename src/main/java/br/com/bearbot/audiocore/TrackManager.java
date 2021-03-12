package br.com.bearbot.audiocore;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Predicate;

public class TrackManager extends AudioEventAdapter {

    private final AudioPlayer PLAYER;
    private final Queue<AudioInfo> queue;
    private Message msg;

    public TrackManager(AudioPlayer player) {
        this.PLAYER = player;
        this.queue = new LinkedBlockingQueue<AudioInfo>();
    }

    public void queue(AudioTrack track, Member author, Message msg) {
        AudioInfo info = new AudioInfo(track, author);
        queue.add(info);
        this.msg = msg;
        if (PLAYER.getPlayingTrack() == null) {
            PLAYER.playTrack(track);
        }

    }

    public Set<AudioInfo> getQueue() {
        return new LinkedHashSet<AudioInfo>(queue);
    }

    public AudioInfo getInfo(final AudioTrack track) {
        return queue.stream().filter(new Predicate<AudioInfo>() {
            public boolean test(AudioInfo info) {
                return info.getTrack().equals(track);
            }
        }).findFirst().orElse(null);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void shuffleQueue() {
        List<AudioInfo> cQueue = new ArrayList<AudioInfo>(getQueue());
        AudioInfo current = cQueue.get(0);
        cQueue.remove(0);
        Collections.shuffle(cQueue);
        cQueue.add(0, current);
        purgeQueue();
        queue.addAll(cQueue);
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        AudioInfo info = queue.element();
        VoiceChannel vChan = info.getAuthor().getVoiceState().getChannel();

        if (vChan == null) {
            player.stopTrack();
        } else {
            info.getAuthor().getGuild().getAudioManager().openAudioConnection(vChan);
        }

        String THUMB = "https://img.youtube.com/vi/" + player.getPlayingTrack().getInfo().identifier + "/default.jpg";

        EmbedBuilder nextMusic = new EmbedBuilder();
        nextMusic.setTitle("🎶  Now playing ");
        nextMusic.setThumbnail(THUMB);
        nextMusic.setColor(Color.MAGENTA);

        nextMusic.addField("Music name: ", "`" + player.getPlayingTrack().getInfo().title + "`", false);
        nextMusic.addField("Duration: ", "`" + getTimestamp(player.getPlayingTrack().getDuration()) + "`",
                false);
        nextMusic.addField("Author: ", "`" + player.getPlayingTrack().getInfo().author + "`", false);
        nextMusic.addField("Video link:", "[Video here](" + player.getPlayingTrack().getInfo().uri + ")", false);
        nextMusic.setFooter(msg.getAuthor().getName(), msg.getAuthor().getAvatarUrl());
        nextMusic.setTimestamp(msg.getTimeCreated());
        msg.getChannel().sendMessage(nextMusic.build()).queue();
        msg.getTextChannel().getManager().setTopic("🎶  Now playing: " + player.getPlayingTrack().getInfo().title)
                .queue();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        Guild g = queue.poll().getAuthor().getGuild();
        if (queue.isEmpty()) {
            msg.getTextChannel().getManager().setTopic("").queue();
            g.getAudioManager().closeAudioConnection();
        } else {
            player.playTrack(queue.element().getTrack());
        }
    }

    @Override
    public void onPlayerPause(AudioPlayer player) {
        // TODO Auto-generated method stub
        super.onPlayerPause(player);
        System.out.println("Pausado");
    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        // TODO Auto-generated method stub
        super.onPlayerResume(player);
        System.out.println("Resume");
    }

    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

}