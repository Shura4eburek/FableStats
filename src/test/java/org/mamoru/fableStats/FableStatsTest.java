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

    @Test
    void ukrainianLangLoadsWithoutThrowing() {
        assertDoesNotThrow(() -> plugin.getLangManager().reload("uk"));
    }

    @Test
    void ukrainianNoPermissionMessage() {
        plugin.getLangManager().reload("uk");
        String message = plugin.getLangManager().getMessage("no-permission");
        assertTrue(message.contains("немає прав"));
    }

    @Test
    void ukrainianConfigReloadedMessage() {
        plugin.getLangManager().reload("uk");
        String message = plugin.getLangManager().getMessage("config-reloaded");
        assertTrue(message.contains("перезавантажено"));
    }

    @Test
    void ukrainianTimeUnits() {
        plugin.getLangManager().reload("uk");
        assertEquals("год", plugin.getLangManager().getPlaceholder("time-hours"));
        assertEquals("хв", plugin.getLangManager().getPlaceholder("time-minutes"));
    }

    @Test
    void ukrainianNotAvailable() {
        plugin.getLangManager().reload("uk");
        assertEquals("Н/Д", plugin.getLangManager().getPlaceholder("not-available"));
    }
}
