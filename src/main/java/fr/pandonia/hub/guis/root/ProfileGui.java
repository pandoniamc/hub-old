package fr.pandonia.hub.guis.root;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.guis.Gui;
import fr.pandonia.hub.guis.GuiType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class ProfileGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};

    public ProfileGui(PandoniaPlayer player) {
        super(6, "Profil", player);

        setBackground(DyeColor.BLUE.ordinal(), BACKGROUND_SLOTS);

        setItem(3, getItem("§d§lAmis", SkullUtils.getSkull(SkullTypes.FRIENDS)));

        setProfile(4);

        setItem(5, getItem("§b§lGuilde", SkullUtils.getSkull(SkullTypes.GUILD), Collections.singletonList("§c§lEn Développement...")));

        setGui(21, getItem("§5§lParamètres", Material.REDSTONE_COMPARATOR, Arrays.asList(
                "§7Modifier vos §5paramètres",
                "§7et préférences sur",
                "§7le serveur."
        )), GuiType.SETTINGS);

        setGui(22, getItem("§f§lStatistiques", Material.BOOK, Arrays.asList(
                "§7Voir vos statistiques",
                "§7sur le serveur."
        )), GuiType.STATISTICS);

        setGui(23, getItem("§3§lPandonia-Pass", SkullUtils.getSkull(SkullTypes.PANDONIA_PASS), Arrays.asList(
                "§7Voir votre progression sur",
                "§7le §3Pass de combat §7et",
                "§7les §erécompenses §7que vous",
                "§7pouvez obtenir sur",
                "§7chaque §bpalier§7."
        )), GuiType.PANDONIA_PASS);

        setItem(31, getItem("§c§lEn Développement", new ItemStack(Material.INK_SACK, 1, (short) DyeColor.ORANGE.ordinal())));
    }
}
