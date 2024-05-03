package fr.pandonia.hub.guis.enums;

import org.bukkit.ChatColor;

public enum GameStatus {

    OPEN("Ouvert", ChatColor.GREEN),
    CLOSED("Fermé", ChatColor.RED);

    private final String name;
    private final ChatColor color;

    GameStatus(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getDisplayName() {
        return color + name;
    }
}
