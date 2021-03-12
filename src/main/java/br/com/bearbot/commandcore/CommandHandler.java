package br.com.bearbot.commandcore;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;

public class CommandHandler {

    public static HashMap<String, Commands> commands = new HashMap<>();

    public static void handleCommand(String parser, MessageReceivedEvent event, String[] args) {
        if (commands.containsKey(parser)) {
            Commands command = commands.get(parser);

            boolean safe = commands.get(parser).called(args, event);

            if (!safe) {
                command.action(args, event);
                command.executed(safe, event);
            } else {
                command.executed(safe, event);
            }

        }
    }
}
