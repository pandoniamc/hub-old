package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.guis.MainGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class PlayerInteractListener implements Listener {

    private final PlayerService playerService;

    public PlayerInteractListener(PlayerService playerService) {
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

        HubPlayer player = playerService.getPlayer(event.getPlayer().getUniqueId());

        switch (item.getType()) {
            case COMPASS:
                new MainGui().open(player);
                break;
        }
    }
}
