package fr.pandonia.hub.api.game;

import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Game {

    ARENA("Arena", ChatColor.RED, Material.DIAMOND_SWORD, Collections.singletonList(GameType.PVP), Arrays.asList(
            "§7Rejoins les combats entre les",
            "§7joueurs, choisissez votre kit",
            "§7et combattez pour gagner !",
            "§7Venez exploser vos §c§lENNEMIS §7!"
    )),
    CAPTURE_THE_SHEEP("CaptureTheSheep", ChatColor.GREEN, Material.WOOL, Arrays.asList(GameType.PVP, GameType.SURVIVAL), Arrays.asList(
            "§7Sur une carte customisé",
            "§c§lvol §7le mouton adverse",
            "§7et ramène le dans ton camps",
            "§7pour gagner la partie !"
    )),
    ENMU_PARTY("EnmuParty", ChatColor.DARK_AQUA, SkullUtils.getSkull(SkullTypes.ENMU_PARTY), Collections.singletonList(GameType.PVP), Arrays.asList(
            "§7Installez-vous dans le §c§lTrain",
            "§c§ld’Enmu §7et incarne les",
            "§7personnages phare pour",
            "§7pouvoir te battre !"
    )),
    UHC("UHC", ChatColor.YELLOW, Material.GOLDEN_APPLE, Collections.singletonList(GameType.UHC), Arrays.asList(
            "§7Prépare ton équipement sur",
            "§7une carte de jeu naturelle et",
            "§7affronte des joueurs."
    ));

    private final String name;
    private final ChatColor color;
    private final ItemStack item;
    private final List<GameType> types;
    private final List<String> lore;

    Game(String name, ChatColor color, ItemStack item, List<GameType> types, List<String> lore) {
        this.name = name;
        this.color = color;
        this.item = item;
        this.types = types;
        this.lore = lore;
    }

    Game(String name, ChatColor color, Material material, List<GameType> types, List<String> lore) {
        this(name, color, new ItemStack(material), types, lore);
    }

    public String getName() {
        return "" + color + ChatColor.BOLD + name;
    }

    public ItemStack getItem() {
        return item;
    }

    public List<GameType> getTypes() {
        return types;
    }

    public List<String> getLore() {
        return lore;
    }
}
