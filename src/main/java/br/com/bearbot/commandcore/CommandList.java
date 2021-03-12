package br.com.bearbot.commandcore;

import br.com.bearbot.commands.*;

public class CommandList {
    public static void addAllCommands() {
        CommandHandler.commands.put("info", new Info());
        CommandHandler.commands.put("invite", new Invite());
        CommandHandler.commands.put("help", new Help());
        CommandHandler.commands.put("clear", new Clear());
        CommandHandler.commands.put("sharescreen", new ShareScreen());
        CommandHandler.commands.put("rank", new Rank());
        CommandHandler.commands.put("roll", new Roll());
        CommandHandler.commands.put("ping", new Ping());
        CommandHandler.commands.put("set", new SetChannel());
    }
}
