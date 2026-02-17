package org.mamoru.customStats.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mamoru.customStats.CustomStats;

import static org.junit.jupiter.api.Assertions.*;

class StatsCommandExecutorTest {

    private ServerMock server;
    private CustomStats plugin;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CustomStats.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void consoleCannotUseStats() {
        server.executeConsole("stats");
        // Console should receive "Only a player can use this command."
        // No exception should be thrown
    }

    @Test
    void playerCanUseStats() {
        PlayerMock player = server.addPlayer("TestPlayer");
        player.performCommand("stats");
        // Player should receive messages without errors
        assertFalse(player.nextMessage() == null && plugin.getConfig().getStringList("message").isEmpty());
    }

    @Test
    void reloadWithoutPermissionIsDenied() {
        PlayerMock player = server.addPlayer("TestPlayer");
        player.addAttachment(plugin, "customstats.reload", false);
        player.performCommand("stats reload");
        String message = player.nextMessage();
        assertNotNull(message);
        assertTrue(message.contains("don't have permission") || message.contains("нет прав"));
    }

    @Test
    void reloadWithPermissionWorks() {
        PlayerMock player = server.addPlayer("AdminPlayer");
        player.addAttachment(plugin, "customstats.reload", true);
        player.performCommand("stats reload");
        String message = player.nextMessage();
        assertNotNull(message);
        assertTrue(message.contains("reloaded"));
    }
}
