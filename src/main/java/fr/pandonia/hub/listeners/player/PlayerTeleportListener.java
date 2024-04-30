package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.events.PlayerTeleportEvent;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTeleportListener implements Listener {

    private final FileConfiguration configuration;

    public PlayerTeleportListener(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportLocation location = event.getLocation();

        player.teleport((Location) configuration.get(String.format("locations.%s", location.name().toLowerCase())));
    }
}
