package fr.pandonia.hub.api.game;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Game {

    ARENA("§c§lArena", Material.DIAMOND_SWORD, Collections.singletonList(GameType.PVP), Arrays.asList(
            "§7Rejoins les combats entre les",
            "§7joueurs, choisissez votre kit",
            "§7et combattez pour gagner !",
            "§7Venez exploser vos §c§lENNEMIS §7!"
    )),
    CAPTURE_THE_SHEEP("§a§lCaptureTheSheep", Material.WOOL, Arrays.asList(GameType.PVP, GameType.SURVIVAL), Arrays.asList(
            "§7Sur une carte customisé",
            "§c§lvol §7le mouton adverse",
            "§7et ramène le dans ton camps",
            "§7pour gagner la partie !"
    )),
    ENMU_PARTY("§3§lEnmuParty", Material.SKULL_ITEM, Collections.singletonList(GameType.PVP), Arrays.asList(
            "§7Installez-vous dans le §c§lTrain",
            "§c§ld’Enmu §7et incarne les",
            "§7personnages phare pour",
            "§7pouvoir te battre !"
    )),
    UHC("§e§lUHC", Material.GOLDEN_APPLE, Collections.singletonList(GameType.UHC), Arrays.asList(
            "§7Prépare ton équipement sur",
            "§7une carte de jeu naturelle et",
            "§7affronte des joueurs."
    ));

    private final String name;
    private final Material material;
    private final List<GameType> types;
    private final List<String> lore;

    Game(String name, Material material, List<GameType> types, List<String> lore) {
        this.name = name;
        this.material = material;
        this.types = types;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public List<GameType> getTypes() {
        return types;
    }

    public List<String> getLore() {
        return lore;
    }
}
