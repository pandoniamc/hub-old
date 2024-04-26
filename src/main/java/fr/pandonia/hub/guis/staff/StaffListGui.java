package fr.pandonia.hub.guis.staff;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.HubPlugin;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.staff.StaffService;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.DyeColor;

import java.util.List;

public class StaffListGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    private final boolean connectedOnly;

    public StaffListGui(boolean connectedOnly) {
        super(5, connectedOnly ? "Staff(s) Connecté(s)" : "Équipe du Staff");

        this.connectedOnly = connectedOnly;
    }

    @Override
    protected void configure(PandoniaPlayer player) {
        setBackground(DyeColor.LIME.ordinal(), BACKGROUND_SLOTS);

        StaffService staffService  = HubPlugin.getInstance().getStaffService();
        List<PandoniaPlayer> staffList = staffService.getStaffList(connectedOnly);

        for (int i = 0; i < staffList.size(); i++) {
            PandoniaPlayer staff = staffList.get(i);

            int row = 11 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, new ItemBuilder(SkullUtils.getPlayerSkull(staff.asBukkit())).name(String.format("%s %s", staff.getGroup().getColoredName(), staff.getName())).build());
        }

        setReturn(40);
    }
}
