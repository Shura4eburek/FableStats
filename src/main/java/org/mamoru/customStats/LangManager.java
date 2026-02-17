package org.mamoru.customStats;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LangManager {

    private final JavaPlugin plugin;
    private YamlConfiguration langConfig;

    public LangManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void reload(String langCode) {
        String resourcePath = "lang/" + langCode + ".yml";
        File langFile = new File(plugin.getDataFolder(), resourcePath);

        if (!langFile.exists()) {
            langFile.getParentFile().mkdirs();
            if (plugin.getResource(resourcePath) != null) {
                plugin.saveResource(resourcePath, false);
            } else {
                plugin.getLogger().warning("Language file not found: " + resourcePath + ", falling back to en.");
                resourcePath = "lang/en.yml";
                langFile = new File(plugin.getDataFolder(), resourcePath);
                if (!langFile.exists()) {
                    plugin.saveResource(resourcePath, false);
                }
            }
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);

        InputStream defaultStream = plugin.getResource(resourcePath);
        if (defaultStream != null) {
            YamlConfiguration defaults = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(defaultStream, StandardCharsets.UTF_8));
            langConfig.setDefaults(defaults);
        }
    }

    public String getMessage(String key) {
        String message = langConfig.getString("messages." + key, key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getPlaceholder(String key) {
        return langConfig.getString("placeholders." + key, key);
    }
}
