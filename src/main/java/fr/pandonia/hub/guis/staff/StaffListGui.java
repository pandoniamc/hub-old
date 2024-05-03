package fr.pandonia.hub.guis.staff;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.guis.ChildGui;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.BukkitUtils;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.DyeColor;

import java.util.List;

public class StaffListGui extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    public StaffListGui(PandoniaPlayer player, boolean connectedOnly, List<PandoniaPlayer> staffList) {
        super(5, connectedOnly ? "Staff(s) Connecté(s)" : "Équipe du Staff", player, GuiType.STAFF);

        setBackground(DyeColor.LIME, BACKGROUND_SLOTS);

        for (int i = 0; i < staffList.size(); i++) {
            PandoniaPlayer staff = staffList.get(i);

            int row = 11 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, new ItemBuilder(SkullUtils.getPlayerSkull(player)).name(String.format("%s %s", staff.getGroup().getDisplayName(), BukkitUtils.getPlayer(player).getName())).build());
        }

        setReturn(40);
    }
}
