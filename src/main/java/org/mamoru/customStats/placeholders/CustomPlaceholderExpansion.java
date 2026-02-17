package org.mamoru.customStats.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.mamoru.customStats.CustomStats;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomPlaceholderExpansion extends PlaceholderExpansion implements Listener {

    private final CustomStats plugin;
    private final Map<UUID, Long> sessionStartTimes = new HashMap<>();

    public CustomPlaceholderExpansion(CustomStats plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "customstats";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sessionStartTimes.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sessionStartTimes.remove(event.getPlayer().getUniqueId());
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        return switch (identifier) {
            case "time_played_total" -> {
                long totalPlayTime = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
                yield formatTime(totalPlayTime);
            }
            case "time_since_last_played" -> {
                Long sessionStart = sessionStartTimes.get(player.getUniqueId());
                if (sessionStart != null) {
                    long sessionDuration = (System.currentTimeMillis() - sessionStart) / 1000;
                    yield formatTime(sessionDuration);
                }
                yield plugin.getLangManager().getPlaceholder("not-available");
            }
            case "first_join_date" -> new SimpleDateFormat("dd.MM.yyyy").format(new Date(player.getFirstPlayed()));
            case "deaths" -> String.valueOf(player.getStatistic(Statistic.DEATHS));
            case "kills" -> String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS));
            case "mob_kills" -> String.valueOf(player.getStatistic(Statistic.MOB_KILLS));
            case "damage_dealt" -> String.valueOf(player.getStatistic(Statistic.DAMAGE_DEALT));
            case "damage_taken" -> String.valueOf(player.getStatistic(Statistic.DAMAGE_TAKEN));
            case "distance_walked" -> {
                long meters = player.getStatistic(Statistic.WALK_ONE_CM) / 100;
                yield meters + " " + plugin.getLangManager().getPlaceholder("distance-suffix");
            }
            default -> null;
        };
    }

    private String formatTime(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        String h = plugin.getLangManager().getPlaceholder("time-hours");
        String min = plugin.getLangManager().getPlaceholder("time-minutes");
        return hours + " " + h + " " + minutes + " " + min;
    }
}
