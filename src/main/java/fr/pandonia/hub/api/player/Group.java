package fr.pandonia.hub.api.player;

import org.bukkit.ChatColor;

import java.util.Optional;

public enum Group {

    ADMINISTRATOR("Administrateur", ChatColor.DARK_RED, "ADMIN", "" + ChatColor.GOLD + ChatColor.BOLD),
    MANAGER("Responsable", ChatColor.RED, "RESP"),
    MODERATOR("Modérateur", ChatColor.BLUE, "MOD"),
    HELPER("Helper", ChatColor.DARK_AQUA, "HELPER"),
    DEVELOPER("Développeur", ChatColor.DARK_PURPLE, "DEV"),
    BUILDER("Builder", ChatColor.GOLD, "BUILD"),
    COMMUNITY_MANAGER("Community Manager", ChatColor.YELLOW, "CM"),
    GAME_DESIGNER("Game Designer", ChatColor.DARK_BLUE, "G. DESIGN"),
    STAFF("Staff", ChatColor.DARK_GREEN, "STAFF"),
    PARTNER("Partenaire", ChatColor.LIGHT_PURPLE, "PART"),
    FRIEND("Ami", ChatColor.DARK_GRAY, "AMI"),
    FAMOUS("Famous", ChatColor.LIGHT_PURPLE, "FAMOUS"),
    BOOSTER("Booster", ChatColor.LIGHT_PURPLE, "BOOSTER", 2592000),
    GOD("Dieu", ChatColor.LIGHT_PURPLE, "DIEU", 2592000),
    LEGEND("Légende", ChatColor.AQUA, "LEGENDE", 2592000),
    ELITE("Élite", ChatColor.YELLOW, "ELITE", 2592000),
    PLAYER("Joueur", ChatColor.GRAY);

    private static final String CHAT_FORMAT = "%s%s%%s §8▪ %s%%s";

    private final String name;
    private final ChatColor color;
    private final String prefix;
    private String messageFormat = ChatColor.WHITE.toString();
    private int duration = -1;

    Group(String name, ChatColor color, String prefix, String messageFormat) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.messageFormat = messageFormat;
    }

    Group(String name, ChatColor color, String prefix, int duration) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.duration = duration;
    }

    Group(String name, ChatColor color, String prefix) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
    }

    Group(String name, ChatColor color) {
        this(name, color, null);
    }

    public static Group valueOf(int id) {
        return values()[id - 1];
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return color + name;
    }

    public Optional<Integer> getDuration() {
        return duration == -1 ? Optional.empty() : Optional.of(duration);
    }

    public String getChatFormat() {
        return String.format(CHAT_FORMAT, prefix == null ? "" : "" + color + ChatColor.BOLD + prefix + " ", color, messageFormat);
    }

    public boolean is(Group group) {
        return ordinal() <= group.ordinal();
    }
}
