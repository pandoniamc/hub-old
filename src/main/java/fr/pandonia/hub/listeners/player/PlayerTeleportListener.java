package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.configuration.Configuration;
import fr.pandonia.hub.configuration.TeleportLocation;
import fr.pandonia.hub.events.PlayerTeleportEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerTeleportListener implements Listener {

    private final Configuration configuration;

    public PlayerTeleportListener(Configuration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        TeleportLocation location = event.getLocation();

        player.teleport(configuration.getLocation(location));
    }
}
