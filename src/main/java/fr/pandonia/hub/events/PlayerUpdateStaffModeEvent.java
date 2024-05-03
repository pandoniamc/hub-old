package fr.pandonia.hub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUpdateStaffModeEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final boolean staffMode;

    public PlayerUpdateStaffModeEvent(Player player, boolean staffMode) {
        super(player);
        this.staffMode = staffMode;
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public boolean isStaffMode() {
        return staffMode;
    }
}
