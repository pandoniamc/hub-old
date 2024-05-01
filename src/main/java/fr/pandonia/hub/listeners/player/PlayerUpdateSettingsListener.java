package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.events.PlayerUpdateSettingsEvent;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.visibility.VisibilityManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerUpdateSettingsListener implements Listener {

    private final SettingsService settingsService;
    private final VisibilityManager visibilityManager;

    public PlayerUpdateSettingsListener(SettingsService settingsService, VisibilityManager visibilityManager) {
        this.settingsService = settingsService;
        this.visibilityManager = visibilityManager;
    }

    @EventHandler
    public void onPlayerUpdateSettings(PlayerUpdateSettingsEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        Bukkit.getOnlinePlayers().forEach(target -> visibilityManager.hidePlayerIfNeeded(target, player));

        settingsService.saveSettings(playerId);
    }
}
