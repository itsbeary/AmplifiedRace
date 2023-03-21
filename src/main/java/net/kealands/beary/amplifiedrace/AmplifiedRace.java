package net.kealands.beary.amplifiedrace;

import lombok.Getter;
import net.kealands.beary.amplifiedrace.commands.AmplifiedRaceCommand;
import net.kealands.beary.amplifiedrace.listeners.GameListener;
import net.kealands.beary.amplifiedrace.managers.GameManager;
import net.kealands.beary.amplifiedrace.managers.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AmplifiedRace extends JavaPlugin {

    @Getter
    private static AmplifiedRace instance;

    private final WorldManager worldManager = new WorldManager();
    private final GameManager gameManager = new GameManager();

    @Override
    public void onEnable() {
        instance = this;
        getCommand("amplifiedrace").setExecutor(new AmplifiedRaceCommand());
        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
    }
}
