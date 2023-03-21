package net.kealands.beary.amplifiedrace.game;

import lombok.Getter;
import lombok.Setter;
import net.kealands.beary.amplifiedrace.AmplifiedRace;
import net.kealands.beary.amplifiedrace.utils.ChatUtils;
import net.kealands.beary.amplifiedrace.utils.FireworkUtil;
import net.kealands.beary.amplifiedrace.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.HashSet;

@Getter @Setter
public class Game {

    private final int id;

    private final World world;
    private int secondsPast;
    private final int pvpTimer = 1200;

    private boolean started;
    private GameTask gameTask;

    private HashSet<Player> alive = new HashSet<>();

    private HashSet<Player> eliminated = new HashSet<>();

    public Game(int id) {
        this.id = id;
        this.world = AmplifiedRace.getInstance().getWorldManager().generateGameWorld(this);
        this.gameTask = new GameTask(this);
        gameTask.runTaskTimer(AmplifiedRace.getInstance(), 0, 20);
        alive.addAll(Bukkit.getOnlinePlayers());
    }

    public void startGame() {
        started = true;
        alive.forEach(player -> {
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            player.setExp(0);
            player.setSaturation(20);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleportAsync(world.getSpawnLocation());
            player.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
            player.getInventory().addItem(new ItemStack(Material.FIREWORK_ROCKET, 16));
        });
        Bukkit.broadcast(Component.text(ChatUtils.format("Game has been started!")));
        Bukkit.broadcast(Component.text(ChatUtils.format("This game was created by twitter.com/bearyoce")));
        world.setPVP(false);
    }
    public void endGame(boolean force) {
        Bukkit.broadcast(Component.text(force ? ChatUtils.format("&cGame was force ended") : ChatUtils.format("&cGame is now ended")));
        if(!force) {
            Bukkit.broadcast(Component.text(ChatUtils.format(getAlive().stream().map(Player::getName).toList().toString().replace("[", "").replace("]", "")) + " has won the game!"));
            for(Player winners : getAlive()) {
                FireworkUtil.spawnFireworks(winners.getLocation());
                winners.teleportAsync(world.getSpawnLocation());
            }
            for(Player eliminated : getEliminated()) {
                eliminated.setGameMode(GameMode.SURVIVAL);
                eliminated.teleportAsync(world.getSpawnLocation());
            }
        }
        AmplifiedRace.getInstance().getGameManager().setGame(null);
    }

    public void onPlayerDeath(Player player, @Nullable Player killer, @Nullable EntityDamageEvent.DamageCause cause) {
        if(!getAlive().contains(player))
            return;
        getAlive().remove(player);

        if(getAlive().size() <= 1)
            endGame(false);

        getEliminated().add(player);
        Bukkit.broadcast(Component.text(ChatUtils.format(player.getName() + " has been eliminated!")));
        player.spigot().respawn();
        player.teleportAsync(world.getSpawnLocation());
        player.setGameMode(GameMode.SPECTATOR);

    }
    public void onPlayerDamage(Player player, @Nullable Player damager, EntityDamageEvent.DamageCause cause, double finalDamage, EntityDamageEvent event) {
        if (getEliminated().contains(damager))
            event.setCancelled(true);
    }

    public void onTick() {
        if(getSecondsPast() >= pvpTimer && !world.getPVP()) {
            getAlive().forEach(player ->
                    player.showTitle(Title.title(Component.text("§6§lAmplifiedRace"), Component.text("§cPvP is now enabled!"))));
            world.setPVP(true);
            world.getWorldBorder().setSize(50, 600);
        }

        getAlive().forEach(player ->
            player.sendActionBar(!world.getPVP() ? Component.text("§c" + (pvpTimer - getSecondsPast()) + " seconds till PVP!") : Component.text("§c" + PlayerUtils.returnClosestPlayer(player))));

    }
}