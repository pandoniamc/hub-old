package fr.pandonia.hub.guis;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ChildGui extends Gui {

    private final GuiType parentType;

    public ChildGui(int rows, String title, PandoniaPlayer player, GuiType parentType) {
        super(rows, title, player);

        this.parentType = parentType;
    }

    protected void setReturn(int slot) {
        setItem(slot, getItem("§cRetour en arrière", Material.ARROW), e -> openGui((Player) e.getWhoClicked(), parentType));
    }
}
