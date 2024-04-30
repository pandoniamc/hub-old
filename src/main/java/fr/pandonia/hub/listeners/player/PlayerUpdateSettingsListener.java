package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.events.PlayerUpdateSettingsEvent;
import fr.pandonia.hub.api.settings.Settings;
import fr.pandonia.hub.api.settings.SettingsService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerUpdateSettingsListener implements Listener {

    private final SettingsService settingsService;

    public PlayerUpdateSettingsListener(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @EventHandler
    public void onPlayerUpdateSettings(PlayerUpdateSettingsEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        Settings settings = event.getSettings();

        settingsService.saveSettings(playerId, settings);
    }
}
