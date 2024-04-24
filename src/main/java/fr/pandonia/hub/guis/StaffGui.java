package fr.pandonia.hub.guis;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.gui.GuiClick;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.utils.ItemUtils;
import fr.pandonia.hub.guis.staff.StaffListGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StaffGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    public StaffGui(HubPlayer player) {
        super(player, 5, "§f(§c!§f) §dStaff");

        for (int slot : BACKGROUND_SLOTS) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS).data(DyeColor.MAGENTA.ordinal()).name(ItemUtils.EMPTY_NAME).build());
        }

        setItem(20,
                getMenuButton("§9§lMode Modération", Material.ANVIL, Arrays.asList(
                        "§aActive§7/§cDésactive §7le §9Mode Modération",
                        "",
                        "§7État : §aActivé ou §cDésactivé"
                ), GuiClick.SWITCH),
                Group.HELPER
        );

        setItem(21,
                getMenuButton("§6§lListe du Staff", Material.SKULL_ITEM, Arrays.asList(
                        "§7Accède à la liste",
                        "§7de l’équipe du projet."
                ), GuiClick.OPEN_GUI),
                e -> new StaffListGui(player).open((Player) e.getWhoClicked()),
                Group.STAFF
        );

        setItem(22,
                getMenuButton("§a§lStaff Connecté", Material.SKULL_ITEM, Arrays.asList(
                        "§7Accède à la liste",
                        "§7de l’équipe qui",
                        "§7est connecté."
                ), GuiClick.OPEN_GUI),
                Group.STAFF
        );

        setItem(24,
                getMenuButton("§b§lServeur Staff", Material.SKULL_ITEM, Arrays.asList(
                        "§7Accède à des serveurs",
                        "§7Staff pour te détendre",
                        "§7ou voir les futures",
                        "§7mises à jour."
                ), GuiClick.OPEN_GUI),
                Group.STAFF
        );

        setItem(40, getButton("§cRevenir en arrière", Material.ARROW), e -> new MainGui(player).open((Player) e.getWhoClicked()));
    }
}
