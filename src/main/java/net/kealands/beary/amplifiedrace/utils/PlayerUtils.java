package net.kealands.beary.amplifiedrace.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerUtils {

    public static List<Player> getPlayersWithin(int range, Player player) {
        int radius = range * range;
        return Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().distanceSquared(player.getLocation()) <= radius).filter(p -> !p.getUniqueId().equals(player.getUniqueId())).collect(Collectors.toList());
    }
    public static String returnClosestPlayer(Player player) {
        Player target = getPlayersWithin(200, player).get(0);
        if(target == null)
            return "No players nearby";

        return target.getName() + " is "  + new DecimalFormat("#.#").format(target.getLocation().distance(player.getLocation())) + "m away!";
    }
}
