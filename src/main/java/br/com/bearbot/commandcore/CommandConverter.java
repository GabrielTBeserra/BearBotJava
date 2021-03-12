package br.com.bearbot.commandcore;

import br.com.bearbot.utils.UTILS;

public class CommandConverter {

    public static String parser(String contentDisplay) {
        String args[] = contentDisplay.split(" ");

        return args[0].replace(UTILS.PREFIX, "");
    }

    public static String[] parseArgs(String contentDisplay) {
        return contentDisplay.split(" ");
    }

}
