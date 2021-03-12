package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import br.com.bearbot.utils.CriarImagem;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.logging.Logger;

public class Rank implements Commands {
    private static final Logger logger =
            Logger.getLogger(Rank.class.getName());

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        CriarImagem rankImage = new CriarImagem();

        try {
            event.getChannel().sendFile(rankImage.getImage(event)).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        rankImage = null;
    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        logger.info("Comando rank executado com sucesso por : " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());
    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return null;
    }

}
