package fr.pandonia.hub.api.player;

import java.util.Optional;

public enum Group {

    ADMINISTRATOR("Administrateur", "§4", "ADMIN", "§6§l"),
    MANAGER("Responsable", "§c", "RESP"),
    MODERATOR("Modérateur", "§9", "MOD"),
    HELPER("Helper", "§3", "HELPER"),
    DEVELOPER("Développeur", "§5", "DEV"),
    BUILDER("Builder", "§6", "BUILD"),
    COMMUNITY_MANAGER("Community Manager", "§e", "CM"),
    GAME_DESIGNER("Game Designer", "§1", "G. DESIGN"),
    STAFF("Staff", "§2", "STAFF"),
    PARTNER("Partenaire", "§d", "PART"),
    FRIEND("Ami", "§8", "AMI"),
    FAMOUS("Famous", "§d", "FAMOUS"),
    BOOSTER("Booster", "§d", "BOOSTER"),
    GOD("Dieu", "§d", "DIEU"),
    LEGEND("Légende", "§b", "LEGENDE"),
    ELITE("Élite", "§e", "ELITE"),
    PLAYER("Joueur", "§7");

    private final String name;
    private final String prefix;
    private final String color;
    private final String messageFormat;

    Group(String name, String color, String prefix, String messageFormat) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.messageFormat = messageFormat;
    }

    Group(String name, String color, String prefix) {
        this(name, color, prefix, "§f");
    }

    Group(String name, String color) {
        this(name, color, null);
    }

    public static Group valueOf(int id) {
        return values()[id - 1];
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getColoredName() {
        return color + name;
    }

    public Optional<String> getPrefix() {
        if (prefix == null) {
            return Optional.empty();
        }

        return Optional.of(color + "§l" + prefix);
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public boolean is(Group group) {
        return ordinal() <= group.ordinal();
    }
}
