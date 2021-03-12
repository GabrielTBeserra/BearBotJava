package br.com.bearbot.levelsystem;

// REVISAR SISTEMA DE LEVEL UP

import br.com.bearbot.DAO.NumberOfMessagesDAO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class LevelUpNotification extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isFromType(ChannelType.PRIVATE))
            return;

        long idServer = event.getGuild().getIdLong();
        long iduser = event.getAuthor().getIdLong();
        long messagesCounter = new NumberOfMessagesDAO().QntMessages(iduser, idServer) * 10;
        String name = event.getAuthor().getName();
        String rankName = "***Silver***";

        Color[] colorPallet = new Color[10];
        colorPallet[0] = new Color(148, 0, 211);
        colorPallet[1] = new Color(75, 0, 130);
        colorPallet[2] = new Color(0, 0, 255);
        colorPallet[3] = new Color(0, 255, 0);
        colorPallet[4] = new Color(255, 255, 0);
        colorPallet[5] = new Color(255, 127, 0);
        colorPallet[6] = new Color(255, 0, 0);

        EmbedBuilder levelUpNotification = new EmbedBuilder();

        levelUpNotification.setAuthor(name, event.getAuthor().getAvatarUrl(), event.getAuthor().getAvatarUrl());
        levelUpNotification.setThumbnail(event.getAuthor().getAvatarUrl());
        levelUpNotification.setTitle("Your new rank");
        levelUpNotification.setDescription("**" + name + "**");
        levelUpNotification.addField("YOUR NEW LEVEL", "	__***" + Math.ceil(messagesCounter / 1000) + "***__", true);
        levelUpNotification.addField("XP", "	__***" + messagesCounter + "***__", true);

        levelUpNotification.setFooter(event.getGuild().getName(), event.getGuild().getIconUrl());

        if (messagesCounter == 1000) {
            rankName = "***Wood***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[0]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        } else if (messagesCounter == 4000) {
            rankName = "***Plastic***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[1]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        } else if (messagesCounter == 6000) {
            rankName = "***Glass***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[2]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        } else if (messagesCounter == 8000) {
            rankName = "***Aluminium***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[3]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        } else if (messagesCounter == 10000) {
            rankName = "***Steel***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[4]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        } else if (messagesCounter == 12000) {
            rankName = "***Gold***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[5]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        } else if (messagesCounter == 20000) {
            rankName = "***Diamond***";
            levelUpNotification.addField("YOUR NEW RANK", rankName, true);
            levelUpNotification.setColor(colorPallet[6]);
            event.getChannel().sendMessage(levelUpNotification.build()).complete();
        }

    }
}
