package net.kealands.beary.amplifiedrace.managers;

import lombok.Getter;
import lombok.Setter;
import net.kealands.beary.amplifiedrace.game.Game;


@Getter
public class GameManager {

    @Setter
    private Game game = null;

    public boolean isGameRunning() {
        return game != null;
    }


}
