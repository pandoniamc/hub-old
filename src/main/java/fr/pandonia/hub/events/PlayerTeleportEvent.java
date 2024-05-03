package fr.pandonia.hub.events;

import fr.pandonia.hub.configuration.TeleportLocation;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTeleportEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final TeleportLocation location;

    public PlayerTeleportEvent(Player player, TeleportLocation location) {
        super(player);
        this.location = location;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public TeleportLocation getLocation() {
        return location;
    }

}
