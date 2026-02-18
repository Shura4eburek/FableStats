package org.mamoru.fableStats;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FableStatsTest {

    private ServerMock server;
    private FableStats plugin;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(FableStats.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void pluginEnablesSuccessfully() {
        assertTrue(plugin.isEnabled());
    }

    @Test
    void defaultConfigIsCreated() {
        assertNotNull(plugin.getConfig());
        assertEquals("stats", plugin.getConfig().getString("command"));
        assertFalse(plugin.getConfig().getStringList("message").isEmpty());
    }

    @Test
    void placeholderApiDisabledWithoutPlugin() {
        assertFalse(plugin.isPlaceholderApiEnabled());
    }

    @Test
    void reloadConfigDoesNotThrow() {
        assertDoesNotThrow(() -> plugin.reloadPluginConfig());
    }
}
