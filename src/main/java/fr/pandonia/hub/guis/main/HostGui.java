package fr.pandonia.hub.guis.main;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.host.Host;
import fr.pandonia.hub.api.utils.skull.SkullTypes;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.guis.ChildGui;
import fr.pandonia.hub.guis.GuiType;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class HostGui extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    public HostGui(PandoniaPlayer player, List<Host> hosts) {
        super(5, "Choix du serveur", player, GuiType.MAIN);

        setBackground(DyeColor.BROWN, BACKGROUND_SLOTS);

        setReturn(40);

        setProfile(2);

        setItem(4, getItem("§e§lExplications", Material.PAPER, Arrays.asList(
                "§9▪ §7Serveur customisé:",
                "§7Il s’agit d’un serveur",
                "§7accessible par §atous§7."
        )));

        setGui(6, getItem("§9§lCréer un serveur", SkullUtils.getSkull(SkullTypes.SERVER_CREATION), Arrays.asList(
                "§7Démarre ton serveur",
                "§7customisé §9public §7et amuse-toi !",
                "",
                "§c§l[!] Abus sanctionnables."
        )), GuiType.SERVER_CREATION);

        for (int i = 0; i < hosts.size(); i++) {
            int row = 20 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, getHostItem(hosts.get(i)));
        }
    }
}
