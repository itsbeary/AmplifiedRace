package net.kealands.beary.amplifiedrace.commands;

import net.kealands.beary.amplifiedrace.commands.impl.CreateCommand;
import net.kealands.beary.amplifiedrace.commands.impl.EndCommand;
import net.kealands.beary.amplifiedrace.commands.impl.HelpCommand;
import net.kealands.beary.amplifiedrace.commands.impl.StartCommand;
import net.kealands.beary.amplifiedrace.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmplifiedRaceCommand implements CommandExecutor, TabCompleter {

    public final List<SubCommand> subCommands = new ArrayList<>(Arrays.asList(
            new HelpCommand(),
            new CreateCommand(),
            new EndCommand(),
            new StartCommand()
    ));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player))
            return true;


        if (args.length == 0)
            return sendMessage(player, ChatUtils.format("More information with /amplifiedrace help"));

        for (SubCommand subCommand : subCommands) {
            if (!subCommand.getAliases().contains(args[0].toLowerCase()))
                continue;

            if (subCommand.requirePermissions() && !player.hasPermission("amplifiedrace.commands." + subCommand.getLabel()))
                return sendMessage(player, "&cYou cannot execute this command");

            String response = subCommand.onCommandExecute(player, Arrays.stream(args).skip(1).toArray(String[]::new));

            if (response == null || response.length() == 0)
                return true;

            return sendMessage(player, ChatUtils.format(response));
        }

        return sendMessage(player, ChatUtils.format("More information with /amplifiedrace help"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1)
            return subCommands.stream().map(SubCommand::getLabel).filter(v -> args[0].length() == 0 || v.startsWith(args[0].toLowerCase())).toList();

        return null;
    }

    public boolean sendMessage(Player player, String message) {
        player.sendMessage(message);
        return true;
    }
}
