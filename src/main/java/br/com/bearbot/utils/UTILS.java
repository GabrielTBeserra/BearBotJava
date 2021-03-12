package br.com.bearbot.utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.List;
import java.util.Map;

public class UTILS {
    public static String TOKEN = "";
    public static String PREFIX = ".";
    public static Map<Guild, Server> GUILDS;
    public static Map<User, Boolean> CONSOLE;
    public static List<String> COMMANDS;
}
