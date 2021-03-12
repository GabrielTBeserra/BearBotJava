package br.com.bearbot.utils;

import br.com.bearbot.core.BearBot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class StatusUpdate {
    public StatusUpdate() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    long members = 0;
                    List<Guild> guilds = BearBot.bot.getGuilds();
                    for (Guild guild : guilds) {
                        members += guild.getMembers().size();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(15);
                        BearBot.bot.getPresence()
                                .setActivity(Activity.streaming(
                                        "Estamos em " + BearBot.bot.getGuilds().size() + " servidores",
                                        "https://www.twitch.tv/1Urso"));
                        TimeUnit.SECONDS.sleep(15);
                        BearBot.bot.getPresence().setActivity(
                                Activity.streaming("Servindo " + members + " pessoas", "https://www.twitch.tv/1Urso"));
                        TimeUnit.SECONDS.sleep(15);
                        BearBot.bot.getPresence()
                                .setActivity(Activity.streaming(".help", "https://www.twitch.tv/1Urso"));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }
}
