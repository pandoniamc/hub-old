package fr.pandonia.hub.api.events;

import fr.pandonia.hub.api.gui.GuiType;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerOpenGuiEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private final PandoniaPlayer pandoniaPlayer;
    private final GuiType type;

    public PlayerOpenGuiEvent(Player viewer, PandoniaPlayer pandoniaPlayer, GuiType type) {
        super(viewer);

        this.pandoniaPlayer = pandoniaPlayer;
        this.type = type;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public PandoniaPlayer getPandoniaPlayer() {
        return pandoniaPlayer;
    }

    public GuiType getType() {
        return type;
    }
}
