package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.configuration.TeleportLocation;
import fr.pandonia.hub.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.events.PlayerTeleportEvent;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.utils.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

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

        Player bukkitPlayer = event.getPlayer();
        UUID playerId = bukkitPlayer.getUniqueId();
        PandoniaPlayer player = playerService.getPlayer(playerId);

        switch (item.getType()) {
            case COMPASS:
                BukkitUtils.callEvent(new PlayerOpenGuiEvent(bukkitPlayer, player, GuiType.MAIN));

                break;

            case SKULL_ITEM:
                BukkitUtils.callEvent(new PlayerOpenGuiEvent(bukkitPlayer, player, GuiType.PROFILE));

                break;

            case FEATHER:
                BukkitUtils.callEvent(new PlayerTeleportEvent(bukkitPlayer, TeleportLocation.JUMP));

                break;

            case BEACON:
                BukkitUtils.callEvent(new PlayerOpenGuiEvent(bukkitPlayer, player, GuiType.HUB));

                break;
        }
    }
}
