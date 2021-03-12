package br.com.bearbot.commandcore;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Commands {

    /**
     * @param args
     * @param event
     * @return
     */


    boolean called(String[] args, MessageReceivedEvent event);

    /**
     * @param args
     * @param event
     */

    void action(String[] args, MessageReceivedEvent event);

    /**
     * @param sucess
     * @param event
     */

    void executed(boolean sucess, MessageReceivedEvent event);

    /**
     * @return
     */

    String help();
}
