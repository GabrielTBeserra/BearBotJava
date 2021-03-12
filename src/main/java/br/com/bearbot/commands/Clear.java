package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.logging.Logger;

public class Clear implements Commands {
    private static final Logger logger =
            Logger.getLogger(Clear.class.getName());

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            int qnt = Integer.parseInt(args[1]);

            if (qnt > 100) {
                event.getChannel().sendMessage("🔴 Limite maximo: 100").queue();
                event.getChannel().deleteMessageById(event.getMessageIdLong()).queue();
                return;
            }

            MessageHistory history = new MessageHistory(event.getChannel());
            List<Message> msgs = history.retrievePast(qnt).complete();
            event.getTextChannel().deleteMessages(msgs).queue();
        } catch (Exception e) {

        }

    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        logger.info("Comando clear executado com sucesso por " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());

    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return null;
    }
}
