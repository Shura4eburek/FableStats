package org.mamoru.fableStats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements Listener {

    private static final String MODRINTH_API = "https://api.modrinth.com/v2/project/SWZSZVoD/version";
    private static final String MODRINTH_URL = "https://modrinth.com/plugin/SWZSZVoD";

    private final FableStats plugin;
    private String latestVersion = null;
    private boolean updateAvailable = false;

    public UpdateChecker(FableStats plugin) {
        this.plugin = plugin;
    }

    public void check() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(MODRINTH_API).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "FableStats/" + plugin.getDescription().getVersion());
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String json = response.toString();
                int idx = json.indexOf("\"version_number\":");
                if (idx != -1) {
                    int start = json.indexOf("\"", idx + 17) + 1;
                    int end = json.indexOf("\"", start);
                    latestVersion = json.substring(start, end);

                    if (!latestVersion.equals(plugin.getDescription().getVersion())) {
                        updateAvailable = true;
                        plugin.getLogger().warning("=================================");
                        plugin.getLogger().warning("A new version of FableStats is available: " + latestVersion);
                        plugin.getLogger().warning("Download: " + MODRINTH_URL);
                        plugin.getLogger().warning("=================================");
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().info("Could not check for updates: " + e.getMessage());
            }
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!updateAvailable) return;
        Player player = event.getPlayer();
        if (!player.hasPermission("fablestats.reload")) return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            player.sendMessage(ChatColor.GOLD + "[FableStats] " + ChatColor.WHITE + "New version available: " + ChatColor.GREEN + latestVersion);
            player.sendMessage(ChatColor.GOLD + "[FableStats] " + ChatColor.AQUA + MODRINTH_URL);
        }, 40L);
    }
}
