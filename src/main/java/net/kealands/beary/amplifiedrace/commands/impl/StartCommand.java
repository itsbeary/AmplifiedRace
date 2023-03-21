package net.kealands.beary.amplifiedrace.commands.impl;

import net.kealands.beary.amplifiedrace.AmplifiedRace;
import net.kealands.beary.amplifiedrace.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class StartCommand extends SubCommand {
    public StartCommand() {
        super(false, "start");
    }

    @Override
    public String onCommandExecute(Player player, String[] args) {
        if(!AmplifiedRace.getInstance().getGameManager().isGameRunning())
            return "No game exists";
        if(AmplifiedRace.getInstance().getGameManager().getGame().isStarted())
            return "Game has already started!";

        AmplifiedRace.getInstance().getGameManager().getGame().startGame();
        return "Started game!";
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
