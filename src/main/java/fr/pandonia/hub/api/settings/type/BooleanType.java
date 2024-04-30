package fr.pandonia.hub.api.settings.type;

import org.bukkit.ChatColor;

public enum BooleanType implements SettingType {

    ENABLED("Activé", ChatColor.GREEN),
    DISABLED("Désactive", ChatColor.RED);

    private final String name;
    private final ChatColor color;

    BooleanType(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public static BooleanType valueOf(boolean value) {
        return value ? ENABLED : DISABLED;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return color + name;
    }
}
