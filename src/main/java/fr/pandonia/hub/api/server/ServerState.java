package fr.pandonia.hub.api.server;

import fr.pandonia.hub.api.utils.skull.SkullTypes;
import org.bukkit.ChatColor;

public enum ServerState {

    CLOSED("Préparation", ChatColor.DARK_BLUE, "a"),
    WAITING_FOR_PLAYERS("Attente de joueurs", ChatColor.AQUA, SkullTypes.OPEN_SERVER),
    STARTING("Démarrage", ChatColor.GREEN, "a"),
    IN_GAME("En jeu", ChatColor.GOLD, SkullTypes.IN_GAME_SERVER),
    ENDED("Partie finie", ChatColor.RED, SkullTypes.CLOSED_SERVER),
    STOPPING("Arrêt", ChatColor.RED, SkullTypes.CLOSED_SERVER);

    private final String name;
    private final ChatColor color;
    private final String skullType;

    ServerState(String name, ChatColor color, String skullType) {
        this.name = name;
        this.color = color;
        this.skullType = skullType;
    }

    public String getColoredName() {
        return color + name;
    }

    public String getSkullType() {
        return skullType;
    }
}
