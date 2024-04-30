package fr.pandonia.hub.guis;

import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.guis.profile.PandoniaPassGui;
import fr.pandonia.hub.guis.profile.SettingsGui;
import fr.pandonia.hub.guis.profile.StatisticsGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;

public class ProfileGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};

    public ProfileGui() {
        super(6, "Profil");
    }

    @Override
    protected void configure(PandoniaPlayer player) {
        setBackground(DyeColor.BLUE.ordinal(), BACKGROUND_SLOTS);

        setItem(3, getButton("§d§lAmis", SkullUtils.getSkull(SkullTypes.FRIENDS)));

        setProfile(4, player);

        setItem(5, getButton("§b§lGuilde", SkullUtils.getSkull(SkullTypes.GUILD), Collections.singletonList("§c§lEn Développement...")));

        setGui(21, getButton("§5§lParamètres", Material.REDSTONE_COMPARATOR, Arrays.asList(
                "§7Modifier vos §5paramètres",
                "§7et préférences sur",
                "§7le serveur."
        )), new SettingsGui(), player);

        setGui(22, getButton("§f§lStatistiques", Material.BOOK, Arrays.asList(
                "§7Voir vos statistiques",
                "§7sur le serveur."
        )), new StatisticsGui(), player);

        setGui(23, getButton("§3§lPandonia-Pass", SkullUtils.getSkull(SkullTypes.PANDONIA_PASS), Arrays.asList(
                "§7Voir votre progression sur",
                "§7le §3Pass de combat §7et",
                "§7les §erécompenses §7que vous",
                "§7pouvez obtenir sur",
                "§7chaque §bpalier§7."
        )), new PandoniaPassGui(), player);

        setItem(31, getButton("§c§lEn Développement", new ItemStack(Material.INK_SACK, 1, (short) DyeColor.ORANGE.ordinal())));
    }
}
