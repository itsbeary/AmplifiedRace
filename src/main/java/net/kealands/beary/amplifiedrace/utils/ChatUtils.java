package net.kealands.beary.amplifiedrace.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', "&6&lAmplifiedRace &8| &e" + message);
    }

}
