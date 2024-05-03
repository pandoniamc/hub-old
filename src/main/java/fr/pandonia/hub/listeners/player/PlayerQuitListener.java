package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.scoreboard.ScoreboardManager;
import fr.pandonia.hub.api.settings.SettingsService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final PlayerService playerService;
    private final SettingsService settingsService;
    private final ScoreboardManager scoreboardManager;

    public PlayerQuitListener(PlayerService playerService, SettingsService settingsService, ScoreboardManager scoreboardManager) {
        this.playerService = playerService;
        this.settingsService = settingsService;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        scoreboardManager.removePlayer(playerId);

        playerService.removePlayerFromCache(playerId);
        settingsService.removeSettingsFromCache(playerId);
    }
}
