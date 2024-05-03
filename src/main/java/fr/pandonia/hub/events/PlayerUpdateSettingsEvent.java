package fr.pandonia.hub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUpdateSettingsEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerUpdateSettingsEvent(Player player) {
        super(player);
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
