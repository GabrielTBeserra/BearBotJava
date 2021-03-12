package br.com.bearbot.audiocore;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MusicPlayerControl {
    private static final Logger logger =
            Logger.getLogger(MusicPlayerControl.class.getName());
    private static final int PLAYLIST_LIMIT = 1000;
    private static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
    private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<Guild, Entry<AudioPlayer, TrackManager>>();
    private static Guild guild;
    private boolean isLink;

    /**
     *
     */

    public MusicPlayerControl() {
        AudioSourceManagers.registerRemoteSources(MANAGER);
    }

    /**
     * @param g
     * @return
     */

    private AudioPlayer createPlayer(Guild g) {
        AudioPlayer audioPlayer = MANAGER.createPlayer();
        TrackManager trackManager = new TrackManager(audioPlayer);
        audioPlayer.addListener(trackManager);
        guild.getAudioManager().setSendingHandler(new PlayerSendHandler(audioPlayer));
        PLAYERS.put(g, new AbstractMap.SimpleEntry<AudioPlayer, TrackManager>(audioPlayer, trackManager));

        return audioPlayer;
    }

    /**
     * @param g
     * @return
     */

    private boolean hasPlayer(Guild g) {
        return PLAYERS.containsKey(g);
    }

    /**
     * @param g
     * @return
     */

    private AudioPlayer getPlayer(Guild g) {
        if (hasPlayer(g)) {
            return PLAYERS.get(g).getKey();
        } else {
            return createPlayer(g);
        }
    }

    /**
     * @param g
     * @return
     */

    private boolean isIdle(Guild g) {
        return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
    }

    /**
     * @param g
     * @return
     */

    private TrackManager getManager(Guild g) {
        return PLAYERS.get(g).getValue();
    }

    /**
     * @param identifier
     * @param author
     * @param msg
     */

    private void loadTrack(String identifier, @NotNull final Member author, @NotNull final Message msg) {
        final Guild guild = author.getGuild();
        getPlayer(guild);

        MANAGER.setFrameBufferDuration(5000);
        MANAGER.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

            /**
             *
             * @param track
             */

            public void trackLoaded(AudioTrack track) {
                getManager(guild).queue(track, author, msg);
            }

            /**
             *
             * @param playlist
             */

            public void playlistLoaded(final AudioPlaylist playlist) {
                int musicsQnt = 0;
                final EmbedBuilder addNew = new EmbedBuilder();
                if (isLink) {
                    for (int i = 0; i < (Math.min(playlist.getTracks().size(), PLAYLIST_LIMIT)); i++) {
                        getManager(guild).queue(playlist.getTracks().get(i), author, msg);
                        musicsQnt = i;
                    }

                    addNew.setTitle("🎶 Musicas adicionadas: " + musicsQnt);
                    addNew.setColor(Color.GREEN);
                } else {
                    musicsQnt = 1;

                    EmbedBuilder nextMusic = new EmbedBuilder();
                    nextMusic.setColor(Color.MAGENTA);
                    nextMusic.setTitle(":radio_button: Select the music for the play: ");

                    StringBuilder desc = new StringBuilder();
                    desc.append("🌓 ` " + playlist.getTracks().get(0).getInfo().title + "`");
                    desc.append("\n🌔 ` " + playlist.getTracks().get(1).getInfo().title + "`");
                    desc.append("\n🌕 ` " + playlist.getTracks().get(2).getInfo().title + "`");
                    desc.append("\n🌖 ` " + playlist.getTracks().get(3).getInfo().title + "`");
                    desc.append("\n\n ❌  `Cancelar`");

                    nextMusic.setFooter("Solicitado por: " + msg.getAuthor().getName(), msg.getAuthor().getAvatarUrl());
                    nextMusic.setTimestamp(msg.getTimeCreated());

                    nextMusic.setDescription(desc.toString());

                    final long idMessage = msg.getChannel().sendMessage(nextMusic.build()).complete().getIdLong();

                    try {
                        msg.getChannel().addReactionById(idMessage, "🌓").queue();
                        msg.getChannel().addReactionById(idMessage, "🌔").queue();
                        msg.getChannel().addReactionById(idMessage, "🌕").queue();
                        msg.getChannel().addReactionById(idMessage, "🌖").queue();
                        msg.getChannel().addReactionById(idMessage, "❌").queue();
                    } catch (Exception e) {
                    }

                    /**
                     * Cria uma thread e espera que o usuario que solicitou a musica escolha a
                     * musica se depois de 10 segundos sem escolher ele pega a primeira musica da
                     * lista
                     */

                    new Thread() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public void run() {
                            try {
                                MusicSelector musicSelector = new MusicSelector(getManager(guild), playlist.getTracks(), author, guild, idMessage, msg);
                                TimeUnit.SECONDS.sleep(10);

                                if (musicSelector.isSelected()) {
                                    musicSelector = null;
                                    interrupt();
                                    stop();
                                }
                                musicSelector = null;
                            } catch (Exception e) {
                                logger.warning(e.getMessage());
                            }

                            msg.getChannel().deleteMessageById(idMessage).queue();
                            getManager(guild).queue(playlist.getTracks().get(0), author, msg);
                            addNew.setTitle("🎶  Adicionado: " + playlist.getTracks().get(0).getInfo().title);
                            msg.getChannel().sendMessage(addNew.build()).queue();
                            addNew.setColor(Color.GREEN);
                            interrupt();
                        }
                    }.start();

                }

                msg.getChannel().sendMessage(addNew.build()).queue();
            }

            public void noMatches() {

            }

            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
            }
        });

    }

    /**
     * @param g
     */
    private void skipMusic(Guild g) {
        getPlayer(g).stopTrack();
    }

    /**
     * @param milis
     * @return
     */
    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    /**
     * @param event
     * @param music
     */
    public void play(MessageReceivedEvent event, String music) throws Exception {
        if (event.getMember().getVoiceState().getChannel() == null) {
            event.getChannel().sendMessage("Voce precisa estar conectado em algum canal de voz para usar esse comando!")
                    .queue();
            return;
        }

        guild = event.getGuild();
        String input = music;

        if (!(input.startsWith("http://") || input.startsWith("https://"))) {
            input = "ytsearch: " + input;
            this.isLink = false;
            loadTrack(input, event.getMember(), event.getMessage());
        } else {
            this.isLink = true;
            loadTrack(input, event.getMember(), event.getMessage());
        }

        event.getMessage().delete().queue();
    }

    /**
     * @param event
     */
    public void stop(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent))
            return;

        getManager(guildEvent).purgeQueue();

        skipMusic(guildEvent);
        guildEvent.getAudioManager().closeAudioConnection();

        EmbedBuilder stopEmbed = new EmbedBuilder();

        stopEmbed.setTitle("🛑 O bot foi parado! 🛑");
        stopEmbed.setColor(Color.RED);
        event.getTextChannel().getManager().setTopic("").queue();
        event.getChannel().sendMessage(stopEmbed.build()).complete();
    }

    /**
     * @param event
     */
    public void shuffle(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent))
            return;
        getManager(guildEvent).shuffleQueue();

        EmbedBuilder addNew = new EmbedBuilder();

        addNew.setTitle("🎶 Musicas misturadas");
        addNew.setColor(Color.MAGENTA);
        event.getChannel().sendMessage(addNew.build()).queue();

    }

    /**
     * @param event
     */

    public void queue(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent)) {
            return;
        }

        Set<AudioInfo> a = getManager(guildEvent).getQueue();
        EmbedBuilder queueInfo = new EmbedBuilder();
        queueInfo.setTitle("Quantidade de musicas restantes: " + a.size());
        queueInfo.setColor(Color.CYAN);
        String queueList = "```";
        int b = 0;
        for (AudioInfo audioInfo : a) {

            queueList += (b + "." + audioInfo.getTrack().getInfo().title + "\n");
            b++;
            if (b == 10) {
                break;
            }
        }

        queueList += "```";

        queueInfo.addField("Musics", queueList, false);
        event.getChannel().sendMessage(queueInfo.build()).queue();

    }

    /**
     * @param event
     */

    public void skip(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent)) {
            return;
        }

        skipMusic(guildEvent);
    }

    /**
     * @param event
     */

    public void pause(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent)) {
            return;
        }
        if (getPlayer(guildEvent).isPaused()) {
            event.getChannel().sendMessage("⏸ Musica ja pausada").queue();
        } else {
            event.getChannel().sendMessage("⏸ Musica pausada").queue();
            getPlayer(guildEvent).setPaused(true);
        }
    }

    /**
     * @param event
     */

    public void unpause(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent)) {
            return;
        }
        if (!getPlayer(guildEvent).isPaused()) {
            event.getChannel().sendMessage("▶ Musica ja reproduzindo").queue();
        } else {
            event.getChannel().sendMessage("▶ Musica despausada").queue();
            getPlayer(guildEvent).setPaused(false);
        }
    }

    /**
     * @param event
     */

    public void info(MessageReceivedEvent event) {
        Guild guildEvent = event.getGuild();

        if (isIdle(guildEvent)) {
            System.out.println("Nao ta tocando nada man");
            return;
        }

        AudioTrack track = getPlayer(guildEvent).getPlayingTrack();
        AudioTrackInfo info = track.getInfo();

        String THUMB = "https://img.youtube.com/vi/" + track.getInfo().identifier + "/default.jpg";

        EmbedBuilder infoEmbed = new EmbedBuilder().setDescription("**CURRENT TRACK INFO:**");
        infoEmbed.addField("Title", info.title, false);
        infoEmbed.addField("Duration",
                "`[ " + getTimestamp(track.getPosition()) + "/ " + getTimestamp(track.getDuration()) + " ]`", false);
        infoEmbed.addField("Author", info.author, false).build();
        infoEmbed.setColor(Color.YELLOW);
        infoEmbed.setThumbnail(THUMB);
        event.getTextChannel().sendMessage(infoEmbed.build()).queue();

    }

}