package br.com.bearbot.commands;

import br.com.bearbot.DAO.MusicChannelId;
import br.com.bearbot.commandcore.Commands;
import br.com.bearbot.utils.UTILS;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.SQLException;
import java.util.EnumSet;

public class SetChannel implements Commands {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length < 2) {
            return;
        }

        if (args[1].equals("music") && args.length > 2) {
            EnumSet<Permission> lista = event.getMember().getPermissions();
            for (Permission permission : lista) {
                if (permission.getName().equals("Administrator")) {
                    setChannel(args, event);
                    break;
                }
            }
        } else if (args[1].equals("music") && args.length > 2) {
            EnumSet<Permission> lista = event.getMember().getPermissions();
            for (Permission permission : lista) {
                if (permission.getName().equals("Administrator")) {
                    setTextChannel(args, event);
                    break;
                }
            }
        }

    }

    @Override
    public void executed(boolean sucess, MessageReceivedEvent event) {
        System.out.println("Comando set music executado com sucesso por : " + event.getAuthor().getName()
                + ", Na guild: " + event.getGuild().getName() + " / " + event.getGuild().getIdLong());
    }

    @Override
    public String help() {
        return null;
    }

    private void setChannel(String[] args, MessageReceivedEvent event) {
        System.out.println(UTILS.GUILDS.get(event.getGuild()));
        if (args[2].equals("this")) {
            if (event.getMember().getVoiceState().getChannel() == null) {
                event.getChannel()
                        .sendMessage("Primeiro voce deve se conectar em um canal de voz para o definir como padrao.")
                        .queue();
                return;
            } else {

                MusicChannelId newId = new MusicChannelId();
                if (UTILS.GUILDS.get(event.getGuild()).getMusicChannel() == null) {
                    try {
                        newId.setId(event.getGuild().getId(), event.getMember().getVoiceState().getChannel().getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    UTILS.GUILDS.get(event.getGuild()).setMusicChannel(event.getMember().getVoiceState().getChannel());
                } else {
                    try {
                        System.out.println("asd");
                        newId.changeId(event.getGuild().getId(),
                                event.getMember().getVoiceState().getChannel().getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    UTILS.GUILDS.get(event.getGuild()).setMusicChannel(event.getMember().getVoiceState().getChannel());
                }

                event.getChannel().sendMessage("Agora o bot vai tocar apenas no canal: "
                        + event.getMember().getVoiceState().getChannel().getName()).queue();
            }

        } else if (args[2].equals("null")) {
            UTILS.GUILDS.get(event.getGuild()).setMusicChannel(null);
            MusicChannelId newId = new MusicChannelId();

            try {
                newId.deleteId(event.getGuild().getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            event.getChannel().sendMessage("Agora todos canais podem tocar musica!").queue();
        } else {
            UTILS.GUILDS.get(event.getGuild()).setMusicChannel(event.getGuild().getVoiceChannelById(args[2]));

            MusicChannelId newId = new MusicChannelId();

            if (UTILS.GUILDS.get(event.getGuild()) == null) {
                try {
                    newId.setId(event.getGuild().getId(), event.getGuild().getVoiceChannelById(args[2]).getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    newId.changeId(event.getGuild().getId(), event.getGuild().getVoiceChannelById(args[2]).getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            event.getChannel().sendMessage(
                    "Agora o bot vai tocar apenas no canal: " + event.getGuild().getVoiceChannelById(args[2]).getName())
                    .queue();
        }

    }

    private void setTextChannel(String[] args, MessageReceivedEvent event) {

    }

}
