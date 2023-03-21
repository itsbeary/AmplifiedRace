package net.kealands.beary.amplifiedrace.commands.impl;

import net.kealands.beary.amplifiedrace.AmplifiedRace;
import net.kealands.beary.amplifiedrace.commands.SubCommand;
import net.kealands.beary.amplifiedrace.game.Game;
import org.bukkit.entity.Player;

import java.util.List;

public class EndCommand extends SubCommand {
    public EndCommand() {
        super(false, "create");
    }

    @Override
    public String onCommandExecute(Player player, String[] args) {
        if (!AmplifiedRace.getInstance().getGameManager().isGameRunning())
            return "There is no game to end!";
        AmplifiedRace.getInstance().getGameManager().getGame().endGame(true);
        return "Ended game successfully";
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
