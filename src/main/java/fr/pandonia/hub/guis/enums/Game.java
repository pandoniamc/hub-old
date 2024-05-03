package fr.pandonia.hub.guis.enums;

import fr.pandonia.hub.api.server.ServerType;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Game {

    ARENA(ServerType.ARENA, Material.DIAMOND_SWORD, Collections.singletonList(GameType.PVP), Arrays.asList(
            "§7Rejoins les combats entre les",
            "§7joueurs, choisissez votre kit",
            "§7et combattez pour gagner !",
            "§7Venez exploser vos §c§lENNEMIS §7!"
    ), DyeColor.WHITE),
    CAPTURE_THE_SHEEP(ServerType.CAPTURE_THE_SHEEP, Material.WOOL, Arrays.asList(GameType.PVP, GameType.SURVIVAL), Arrays.asList(
            "§7Sur une carte customisé",
            "§c§lvol §7le mouton adverse",
            "§7et ramène le dans ton camps",
            "§7pour gagner la partie !"
    ), DyeColor.LIME),
    ENMU_PARTY(ServerType.ENMU_PARTY, SkullUtils.getSkull(SkullTypes.ENMU_PARTY), Collections.singletonList(GameType.PVP), Arrays.asList(
            "§7Installez-vous dans le §c§lTrain",
            "§c§ld’Enmu §7et incarne les",
            "§7personnages phare pour",
            "§7pouvoir te battre !"
    ), DyeColor.BLUE),
    UHC(ServerType.UHC, Material.GOLDEN_APPLE, Collections.singletonList(GameType.UHC), Arrays.asList(
            "§7Prépare ton équipement sur",
            "§7une carte de jeu naturelle et",
            "§7affronte des joueurs."
    ), DyeColor.YELLOW);

    private final ServerType serverType;
    private final ItemStack item;
    private final List<GameType> types;
    private final List<String> lore;
    private final DyeColor dyeColor;
    private final GameStatus status = GameStatus.OPEN;

    Game(ServerType serverType, ItemStack item, List<GameType> types, List<String> lore, DyeColor dyeColor) {
        this.serverType = serverType;
        this.item = item;
        this.types = types;
        this.lore = lore;
        this.dyeColor = dyeColor;
    }

    Game(ServerType serverType, Material material, List<GameType> types, List<String> lore, DyeColor dyeColor) {
        this(serverType, new ItemStack(material), types, lore, dyeColor);
    }

    public ServerType getServerType() {
        return serverType;
    }

    public String getName() {
        return serverType.getName();
    }

    public String getDisplayName() {
        return serverType.getDisplayName();
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

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public GameStatus getStatus() {
        return status;
    }
}
