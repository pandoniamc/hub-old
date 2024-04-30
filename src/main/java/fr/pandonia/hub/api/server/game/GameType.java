package fr.pandonia.hub.api.server.game;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;

public enum GameType {

    UHC("UHC Classique", ChatColor.BLUE, Arrays.asList(
            "§7Préparez votre équipement,",
            "§7dans une map vanilla et",
            "§7affrontez des joueurs."
    )),
    WEREWOLF("Werewolf", ChatColor.RED, Arrays.asList(
            "§7Mode de jeu inspiré du jeu",
            "§6Loups-Garous de Thiercelieux §7dans",
            "§7lequel s’affrontent §aVillageois §7et",
            "§cLoups-Garous§7. Incarnez vos rôles",
            "§7favoris dans ce mode de jeu !"
    ));

    private final String name;
    private final ChatColor color;
    private final List<String> description;

    GameType(String name, ChatColor color, List<String> description) {
        this.name = name;
        this.color = color;
        this.description = description;
    }

    public String getDisplayName() {
        return color + name;
    }

    public List<String> getDescription() {
        return description;
    }
}
