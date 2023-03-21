package net.kealands.beary.amplifiedrace.commands;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class SubCommand {
    final private String label;
    final private List<String> aliases;
    @Accessors(fluent = true)
    final private boolean requirePermissions;

    public SubCommand(boolean requirePermissions, String... aliases) {
        this.label = aliases[0];
        this.aliases = Arrays.stream(aliases).toList();
        this.requirePermissions = requirePermissions;
    }

    public abstract String onCommandExecute(Player player, String[] args);

    public abstract List<String> onTabComplete(Player player, String[] args);
}
