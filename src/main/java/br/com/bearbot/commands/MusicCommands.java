package br.com.bearbot.commands;

import br.com.bearbot.audiocore.MusicPlayerControl;
import br.com.bearbot.utils.UTILS;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicCommands extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE) || event.getAuthor().isBot())
            return;

        String args[] = event.getMessage().getContentDisplay().split(" ");
        String musicArgs[] = event.getMessage().getContentDisplay().split(" ");

        List<String> musicCmds = new ArrayList<>();
        musicCmds.add(".play");
        musicCmds.add(".p");
        musicCmds.add(".skip");
        musicCmds.add(".s");
        musicCmds.add(".now");
        musicCmds.add(".n");
        musicCmds.add(".queue");
        musicCmds.add(".q");
        musicCmds.add(".stop");
        musicCmds.add(".shuffle");
        musicCmds.add(".pause");
        musicCmds.add(".unpause");
        musicCmds.add(".resume");

        if (!musicCmds.contains(args[0])) {
            return;
        }

        if (!(UTILS.GUILDS.get(event.getGuild()).getMusicChannel() == null)) {
            if (UTILS.GUILDS.get(event.getGuild()).getMusicChannel()
                    .equals(event.getMember().getVoiceState().getChannel())) {
            } else {
                event.getChannel().sendMessage("Voce precisa estar no canal de musicas para executar esse comando!")
                        .queue();
                return;
            }
        }

        String music = "";

        try {
            for (int i = 1; i < musicArgs.length; i++)
                music += " " + musicArgs[i];
            music = music.trim();
        } catch (Exception e) {

        }

        MusicPlayerControl musicController = new MusicPlayerControl();

        if (args[0].equals(".play") || args[0].equals(".p")) {
            try {
                musicController.play(event, music.trim());
            } catch (Exception e) {
            }
        }
        if (args[0].equals(".skip") || args[0].equals(".s"))
            musicController.skip(event);
        if (args[0].equals(".now") || args[0].equals(".n"))
            musicController.info(event);
        if (args[0].equals(".queue") || args[0].equals(".q"))
            musicController.queue(event);
        if (args[0].equals(".stop"))
            musicController.stop(event);
        if (args[0].equals(".shuffle"))
            musicController.shuffle(event);
        if (args[0].equals(".pause"))
            musicController.pause(event);
        if (args[0].equals(".unpause") || args[0].equals(".resume"))
            musicController.unpause(event);

    }

}
