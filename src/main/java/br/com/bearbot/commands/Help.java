package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class Help implements Commands {
    private static final Logger logger =
            Logger.getLogger(Help.class.getName());

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder configHelp = new EmbedBuilder();

        configHelp.setTitle("All commands", null);
        configHelp.setColor(Color.MAGENTA);

        StringBuilder helpDesc = new StringBuilder();

        for (AllHelpEnum all : AllHelpEnum.values()) {
            helpDesc.append("***" + all.getCommand() + "*** " + all.getMessage() + "\n");
        }

        configHelp.setDescription(helpDesc);
        configHelp.setAuthor("BearBot",
                "https://cdn.discordapp.com/app-icons/592885875277299733/a446f46bc302b18ebbdaadb18edc06e7.png",
                "https://cdn.discordapp.com/app-icons/592885875277299733/a446f46bc302b18ebbdaadb18edc06e7.png");
        configHelp.setTimestamp(event.getMessage().getTimeCreated());
        configHelp.setImage("https://cdn.discordapp.com/attachments/554754159052849154/554757973524742145/Chroma.gif");
        event.getChannel().sendMessage("👽 Enviado para seu privado").queue();

        sendPrivateMessage(event.getAuthor(), configHelp.build());

    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        logger.info("Comando help executado com sucesso por : " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());

    }

    @Override
    public String help() {
        return null;
    }

    private void sendPrivateMessage(User user, final MessageEmbed messageEmbed) {
        user.openPrivateChannel().queue(new Consumer<PrivateChannel>() {
            public void accept(PrivateChannel channel) {
                channel.sendMessage(messageEmbed).queue();
            }
        });
    }
}
