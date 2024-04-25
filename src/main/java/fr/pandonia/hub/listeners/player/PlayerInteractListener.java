package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.guis.HubGui;
import fr.pandonia.hub.guis.MainGui;
import fr.pandonia.hub.guis.ProfileGui;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlayerInteractListener implements Listener {

    private final FileConfiguration configuration;
    private final PlayerService playerService;

    public PlayerInteractListener(FileConfiguration configuration, PlayerService playerService) {
        this.configuration = configuration;
        this.playerService = playerService;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();

        if (!Arrays.asList(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK).contains(action)) {
            return;
        }

        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }

        Player player = event.getPlayer();
        HubPlayer hubPlayer = playerService.getPlayer(player.getUniqueId());

        switch (item.getType()) {
            case COMPASS:
                new MainGui().open(hubPlayer);

                break;

            case SKULL_ITEM:
                new ProfileGui().open(hubPlayer);

                break;

            case FEATHER:
                player.teleport((Location) configuration.get("jump"));

                break;

            case BEACON:
                new HubGui().open(hubPlayer);

                break;
        }
    }
}
