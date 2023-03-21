package net.kealands.beary.amplifiedrace.managers;

import net.kealands.beary.amplifiedrace.game.Game;
import net.kealands.beary.amplifiedrace.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class WorldManager {

    public World generateGameWorld(Game game) {
        Bukkit.broadcast(Component.text(ChatUtils.format("Generating world... (Server will freeze)")));
        WorldCreator worldCreator = new WorldCreator("amplifiedrace_" + game.getId());
        worldCreator.type(WorldType.AMPLIFIED);
        World world = worldCreator.createWorld();
        if(world == null)
            return null;
        world.getWorldBorder().setSize(1000);
        Bukkit.getLogger().info("Created world " + world.getName() + "!");
        return world;
    }

}
