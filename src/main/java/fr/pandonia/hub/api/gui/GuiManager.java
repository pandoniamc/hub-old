package fr.pandonia.hub.api.gui;

import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.entity.Player;

public class GuiManager {

    private final SpiGUI spigui;

    public GuiManager(SpiGUI spigui) {
        this.spigui = spigui;
    }

    public void open(Player player, Gui gui) {
        SGMenu menu = spigui.create(gui.getName(), gui.getRows());
        gui.configure(player, menu);

        player.openInventory(menu.getInventory());
    }
}
