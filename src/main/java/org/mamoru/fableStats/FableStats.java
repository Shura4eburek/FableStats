package org.mamoru.fableStats;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mamoru.fableStats.commands.StatsCommandExecutor;
import org.mamoru.fableStats.placeholders.FablePlaceholderExpansion;

public class FableStats extends JavaPlugin {

    private boolean placeholderApiEnabled;
    private LangManager langManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        langManager = new LangManager(this);
        langManager.reload(getConfig().getString("lang", "en"));

        String commandName = getConfig().getString("command", "stats");
        if (commandName != null && !commandName.isEmpty() && getCommand(commandName) != null) {
            getCommand(commandName).setExecutor(new StatsCommandExecutor(this));
        } else {
            getLogger().severe("Command '" + commandName + "' not found in plugin.yml!");
        }

        if (getConfig().getBoolean("check-updates", true)) {
            UpdateChecker updateChecker = new UpdateChecker(this);
            updateChecker.check();
            Bukkit.getPluginManager().registerEvents(updateChecker, this);
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderApiEnabled = true;
            FablePlaceholderExpansion expansion = new FablePlaceholderExpansion(this);
            expansion.register();
            Bukkit.getPluginManager().registerEvents(expansion, this);
        } else {
            placeholderApiEnabled = false;
            getLogger().warning("PlaceholderAPI not found. Placeholders won't work.");
        }
    }

    @Override
    public void onDisable() {
    }

    public boolean isPlaceholderApiEnabled() {
        return placeholderApiEnabled;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public void reloadPluginConfig() {
        reloadConfig();
        langManager.reload(getConfig().getString("lang", "en"));
    }
}
