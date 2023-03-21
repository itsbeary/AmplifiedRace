package net.kealands.beary.amplifiedrace.game;

import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {

    private Game game;

    public GameTask(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if(game.isStarted()) {
            game.setSecondsPast(game.getSecondsPast() + 1);
            game.onTick();
        }
    }
}
