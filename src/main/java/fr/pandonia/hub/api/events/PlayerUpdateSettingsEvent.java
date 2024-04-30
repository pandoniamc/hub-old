package fr.pandonia.hub.api.events;

import fr.pandonia.hub.api.settings.Settings;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerUpdateSettingsEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Settings settings;

    public PlayerUpdateSettingsEvent(Player player, Settings settings) {
        super(player);

        this.settings = settings;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public Settings getSettings() {
        return settings;
    }
}
