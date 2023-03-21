package net.kealands.beary.amplifiedrace.commands.impl;

import net.kealands.beary.amplifiedrace.commands.SubCommand;
import net.kealands.beary.amplifiedrace.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends SubCommand {
    public HelpCommand() {
        super(false, "help");
    }

    @Override
    public String onCommandExecute(Player player, String[] args) {
        player.sendMessage("§6/amplifiedrace create §8| §eCreates a game");
        player.sendMessage("§6/amplifiedrace start §8| §eStarts a game");
        player.sendMessage("§6/amplifiedrace end §8| §eForce-ends a game");
        player.sendMessage("§6/amplifiedrace help §8| §eReturns you to this menu");
        return "Plugin was created by twitter.com/bearyoce";
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
