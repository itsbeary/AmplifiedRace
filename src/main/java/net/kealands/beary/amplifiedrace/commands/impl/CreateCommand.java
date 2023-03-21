package net.kealands.beary.amplifiedrace.commands.impl;

import net.kealands.beary.amplifiedrace.AmplifiedRace;
import net.kealands.beary.amplifiedrace.commands.SubCommand;
import net.kealands.beary.amplifiedrace.game.Game;
import net.kealands.beary.amplifiedrace.utils.ChatUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class CreateCommand extends SubCommand {
    public CreateCommand() {
        super(false, "create");
    }

    @Override
    public String onCommandExecute(Player player, String[] args) {
        if(AmplifiedRace.getInstance().getGameManager().isGameRunning())
            return "A game already exists";
        AmplifiedRace.getInstance().getGameManager().setGame(new Game((int) System.currentTimeMillis()));
        return "/amplifiedrace start - to start the game";
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
