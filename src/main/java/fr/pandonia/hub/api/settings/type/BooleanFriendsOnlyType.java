package fr.pandonia.hub.api.settings.type;

import org.bukkit.ChatColor;

public enum BooleanFriendsOnlyType implements SettingType<String> {

    ENABLED("Activé", ChatColor.GREEN),
    FRIENDS_ONLY("Amis uniquement", ChatColor.LIGHT_PURPLE),
    DISABLED("Désactivé", ChatColor.RED);

    private final String name;
    private final ChatColor color;

    BooleanFriendsOnlyType(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return color + name;
    }

    @Override
    public String getPersistedValue() {
        return name();
    }
}
