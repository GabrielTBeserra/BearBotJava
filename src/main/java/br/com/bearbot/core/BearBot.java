package br.com.bearbot.core;

import br.com.bearbot.commandcore.CommandList;
import br.com.bearbot.commandcore.CommandListener;
import br.com.bearbot.commands.MusicCommands;
import br.com.bearbot.events.OnGuildJoinInNewServer;
import br.com.bearbot.events.UserEvents;
import br.com.bearbot.levelsystem.LevelUpNotification;
import br.com.bearbot.levelsystem.MessagesCounter;
import br.com.bearbot.logs.OnReady;
import br.com.bearbot.utils.UTILS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.RestAction;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class BearBot {
    public static JDA bot;

    public static void main(String[] args) throws Exception {
        bot = new JDABuilder(UTILS.TOKEN).build();
        bot.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        bot.getPresence().setActivity(Activity.streaming(".help", "https://www.twitch.tv/1Urso"));

        addListener();
        CommandList.addAllCommands();
    }

    /**
     * Retorna a instancia atual do bot
     * @return instacia do bot
     */

    public static JDA getBot() {
        return bot;
    }

    /**
     * Adiciona todos listeners
     */

    private static void addListener() {
        bot.addEventListener(new OnReady());
        bot.addEventListener(new MusicCommands());
        bot.addEventListener(new LevelUpNotification());
        bot.addEventListener(new OnGuildJoinInNewServer());
        bot.addEventListener(new MessagesCounter());
        bot.addEventListener(new UserEvents());
        bot.addEventListener(new CommandListener());
    }

}
