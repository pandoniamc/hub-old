package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerQuitListener implements Listener {

    private final Plugin plugin;
    private final PlayerService playerService;
    private final ScoreboardManager scoreboardManager;

    public PlayerQuitListener(Plugin plugin, PlayerService playerService, ScoreboardManager scoreboardManager) {
        this.plugin = plugin;
        this.playerService = playerService;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        scoreboardManager.removePlayer(player);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> playerService.unloadData(player.getUniqueId()));
    }
}
