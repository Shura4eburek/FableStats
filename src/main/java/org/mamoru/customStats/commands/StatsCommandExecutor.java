package org.mamoru.customStats.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mamoru.customStats.CustomStats;

import java.util.List;

public class StatsCommandExecutor implements CommandExecutor {

    private final CustomStats plugin;

    public StatsCommandExecutor(CustomStats plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("customstats.reload")) {
                sender.sendMessage(plugin.getLangManager().getMessage("no-permission"));
                return true;
            }
            plugin.reloadPluginConfig();
            sender.sendMessage(plugin.getLangManager().getMessage("config-reloaded"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getLangManager().getMessage("only-player"));
            return true;
        }

        Player player = (Player) sender;

        List<String> messages = plugin.getConfig().getStringList("message");
        if (messages.isEmpty()) {
            player.sendMessage(plugin.getLangManager().getMessage("no-messages"));
            return true;
        }

        for (String message : messages) {
            String processed = ChatColor.translateAlternateColorCodes('&', message);
            if (plugin.isPlaceholderApiEnabled()) {
                processed = PlaceholderAPI.setPlaceholders(player, processed);
            }
            player.sendMessage(processed);
        }
        return true;
    }
}
