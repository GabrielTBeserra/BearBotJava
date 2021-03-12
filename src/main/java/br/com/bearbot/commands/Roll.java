package br.com.bearbot.commands;

import br.com.bearbot.commandcore.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Roll implements Commands {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            int rollNumber = Integer.parseInt(args[1]);
            Random rollRandomNumber = new Random();
            int responseNumber = rollRandomNumber.nextInt(rollNumber);
            event.getChannel().sendMessage("O numero sorteado foi : " + responseNumber).queue();
        } catch (Exception e) {
            if (args.length < 3) {
                event.getChannel().sendMessage("Para usar o comando digite !bb roll <numero>").queue();
            }
        }
    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("Comando roll executado com sucesso por : " + event.getAuthor().getName() + ", Na guild: "
                + event.getGuild().getName() + " / " + event.getGuild().getIdLong());

    }

    @Override
    public String help() {
        // TODO Auto-generated method stub
        return null;
    }

}
