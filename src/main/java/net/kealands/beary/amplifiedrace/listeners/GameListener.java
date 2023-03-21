package net.kealands.beary.amplifiedrace.listeners;

import net.kealands.beary.amplifiedrace.AmplifiedRace;
import net.kealands.beary.amplifiedrace.game.Game;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        Game game = AmplifiedRace.getInstance().getGameManager().getGame();

        if(game == null)
            return;

        if(!game.getAlive().contains(player))
            return;

        if (game.getEliminated().contains(player)) {
            event.setCancelled(true);
            return;
        }

        Player damager = null;

        if (event instanceof EntityDamageByEntityEvent eventDE) {
            if (eventDE.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player shooter)
                damager = shooter;
            else if (eventDE.getDamager() instanceof Player)
                damager = (Player) eventDE.getDamager();
        }

        if (damager != null && AmplifiedRace.getInstance().getGameManager().getGame().getEliminated().contains(damager)) {
            event.setCancelled(true);
            return;
        }

        if (!game.getWorld().getPVP())
            return;

        // Player is going to die
        if (player.getHealth() - event.getFinalDamage() <= 0) {
            game.onPlayerDeath(player, damager, event.getCause());
            event.setCancelled(true);
        } else
            game.onPlayerDamage(player, damager, event.getCause(), event.getFinalDamage(), event);
    }
    @EventHandler
    public void onPortalEnter(PlayerTeleportEvent event) {
        if(event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) || event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL))
            event.setCancelled(true);
    }
}
