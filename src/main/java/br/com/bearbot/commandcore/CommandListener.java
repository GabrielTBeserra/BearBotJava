package br.com.bearbot.commandcore;

import br.com.bearbot.utils.UTILS;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if (event.isFromType(ChannelType.PRIVATE) || event.getAuthor().isBot())
            return;

        if (event.getMessage().getContentDisplay().startsWith(UTILS.PREFIX)
                && event.getMessage().getAuthor().getId() != event.getJDA().getSelfUser().getId()) {

            CommandHandler.handleCommand(CommandConverter.parser(event.getMessage().getContentDisplay()), event,
                    CommandConverter.parseArgs(event.getMessage().getContentDisplay()));
        }
    }
}
