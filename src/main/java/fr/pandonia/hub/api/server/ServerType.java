package fr.pandonia.hub.api.server;

import org.bukkit.ChatColor;

public enum ServerType {

    HUB("Hub", ChatColor.AQUA),
    UHC("UHC", ChatColor.YELLOW),
    CAPTURE_THE_SHEEP("CTS", ChatColor.GREEN, "CaptureTheSheep"),
    ENMU_PARTY("EnmuParty", ChatColor.DARK_AQUA),
    ARENA("Arena", ChatColor.RED);

    private final String prefix;
    private final ChatColor color;
    private final String name;

    ServerType(String prefix, ChatColor color, String name) {
        this.prefix = prefix;
        this.color = color;
        this.name = name;
    }

    ServerType(String prefix, ChatColor color) {
        this(prefix, color, prefix);
    }

    public String getName() {
        return name;
    }

    public String getDisplayPrefix() {
        return color + prefix;
    }

    public String getDisplayName() {
        return "" + color + ChatColor.BOLD + name;
    }
}
