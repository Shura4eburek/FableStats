package org.mamoru.fableStats.placeholders;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mamoru.fableStats.FableStats;

import static org.junit.jupiter.api.Assertions.*;

class FablePlaceholderExpansionTest {

    private ServerMock server;
    private FableStats plugin;
    private FablePlaceholderExpansion expansion;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(FableStats.class);
        expansion = new FablePlaceholderExpansion(plugin);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void identifierIsFablestats() {
        assertEquals("fablestats", expansion.getIdentifier());
    }

    @Test
    void persistReturnsTrue() {
        assertTrue(expansion.persist());
    }

    @Test
    void nullPlayerReturnsEmptyString() {
        assertEquals("", expansion.onPlaceholderRequest(null, "deaths"));
    }

    @Test
    void unknownPlaceholderReturnsNull() {
        PlayerMock player = server.addPlayer("TestPlayer");
        assertNull(expansion.onPlaceholderRequest(player, "nonexistent"));
    }

    @Test
    void deathsPlaceholderReturnsValue() {
        PlayerMock player = server.addPlayer("TestPlayer");
        String result = expansion.onPlaceholderRequest(player, "deaths");
        assertNotNull(result);
    }

    @Test
    void killsPlaceholderReturnsValue() {
        PlayerMock player = server.addPlayer("TestPlayer");
        String result = expansion.onPlaceholderRequest(player, "kills");
        assertNotNull(result);
    }

    @Test
    void playerNamePlaceholderReturnsName() {
        PlayerMock player = server.addPlayer("TestPlayer");
        String result = expansion.onPlaceholderRequest(player, "player_name");
        assertEquals("TestPlayer", result);
    }

    @Test
    void sessionTimeWithoutJoinReturnsNA() {
        PlayerMock player = server.addPlayer("TestPlayer");
        String result = expansion.onPlaceholderRequest(player, "time_since_last_played");
        assertTrue(result.equals("N/A") || result.equals("Н/Д"));
    }

    @Test
    void sessionTimeAfterJoinReturnsFormattedTime() {
        PlayerMock player = server.addPlayer("TestPlayer");
        expansion.onPlayerJoin(new PlayerJoinEvent(player, "joined"));
        String result = expansion.onPlaceholderRequest(player, "time_since_last_played");
        assertNotNull(result);
        assertTrue((result.contains("h") && result.contains("min"))
                || (result.contains("ч") && result.contains("мин")));
    }

    @Test
    void damageDealtPlaceholderReturnsValue() {
        PlayerMock player = server.addPlayer("TestPlayer");
        String result = expansion.onPlaceholderRequest(player, "damage_dealt");
        assertNotNull(result);
    }

    @Test
    void damageTakenPlaceholderReturnsValue() {
        PlayerMock player = server.addPlayer("TestPlayer");
        String result = expansion.onPlaceholderRequest(player, "damage_taken");
        assertNotNull(result);
    }

    @Test
    void quitCleansUpSessionData() {
        PlayerMock player = server.addPlayer("TestPlayer");
        expansion.onPlayerJoin(new PlayerJoinEvent(player, "joined"));
        expansion.onPlayerQuit(new PlayerQuitEvent(player, "left"));
        String result = expansion.onPlaceholderRequest(player, "time_since_last_played");
        assertTrue(result.equals("N/A") || result.equals("Н/Д"));
    }
}
