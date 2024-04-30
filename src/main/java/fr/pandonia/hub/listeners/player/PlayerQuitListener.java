package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.scoreboard.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    private final PlayerService playerService;
    private final ScoreboardManager scoreboardManager;

    public PlayerQuitListener(PlayerService playerService, ScoreboardManager scoreboardManager) {
        this.playerService = playerService;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();

        scoreboardManager.removePlayer(playerId);
        playerService.removePlayerFromCache(playerId);
    }
}
