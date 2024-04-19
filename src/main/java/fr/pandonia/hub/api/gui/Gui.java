package fr.pandonia.hub.api.gui;

import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.entity.Player;

public abstract class Gui {

    private final String name;
    private final int rows;

    public Gui(String name, int rows) {
        this.name = name;
        this.rows = rows;
    }

    protected abstract void configure(Player player, SGMenu menu);

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }
}
