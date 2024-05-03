package fr.pandonia.hub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerSendToServerEvent extends PlayerEvent {

    public PlayerSendToServerEvent(Player player, String arena) {
        super(player);
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
