package fr.pandonia.hub.guis.main;

import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.BukkitUtils;
import fr.pandonia.hub.events.PlayerUpdateStaffModeEvent;
import fr.pandonia.hub.guis.ChildGui;
import fr.pandonia.hub.guis.GuiType;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class StaffGui extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    public StaffGui(PandoniaPlayer player, boolean staffMode) {
        super(5, "Staff", player, GuiType.MAIN);

        setBackground(DyeColor.MAGENTA, BACKGROUND_SLOTS);

        setReturn(40);

        setProtectedItem(20,
                getItem("§9§lMode Modération", Material.ANVIL, Arrays.asList(
                        "§aActive§7/§cDésactive §7le §9Mode Modération",
                        "",
                        "§7État : " + (staffMode ? "§aActivé" : "§cDésactivé")
                )),
                Group.HELPER,
                e -> {
                    boolean newStaffMode = !staffMode;
                    new StaffGui(player, newStaffMode).open((Player) e.getWhoClicked());
                    BukkitUtils.callEvent(new PlayerUpdateStaffModeEvent((Player) e.getWhoClicked(), newStaffMode));
                }
        );

        setGui(21, getItem("§6§lListe du Staff", Material.SKULL_ITEM, Arrays.asList(
                "§7Accède à la liste",
                "§7de l’équipe du projet."
        )), GuiType.STAFF_ALL_LIST);

        setGui(22, getItem("§a§lStaff Connecté", Material.SKULL_ITEM, Arrays.asList(
                "§7Accède à la liste",
                "§7de l’équipe qui",
                "§7est connecté."
        )), GuiType.STAFF_CONNECTED_LIST);

        setGui(24, getItem("§b§lServeur Staff", Material.SKULL_ITEM, Arrays.asList(
                "§7Accède à des serveurs",
                "§7Staff pour te détendre",
                "§7ou voir les futures",
                "§7mises à jour."
        )), GuiType.STAFF_SERVERS);
    }
}
