package fr.pandonia.hub.guis.staff;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.HubPlugin;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.utils.ItemUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.List;

public class StaffListGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    public StaffListGui(boolean connected) {
        super(5, connected ? "§f(§c!§f) §2Staff(s) Connecté(s)" : "§f(§c!§f) §2Équipe du Staff");
    }

    @Override
    protected void configure(HubPlayer player) {
        for (int slot : BACKGROUND_SLOTS) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE).data(DyeColor.LIME.ordinal()).name(ItemUtils.EMPTY_NAME).build());
        }

        PlayerService playerService = HubPlugin.getPlayerService();
        List<HubPlayer> staffList = playerService.getStaffList();

        for (int i = 0; i < staffList.size(); i++) {
            HubPlayer staff = staffList.get(i);

            int row = 11 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, new ItemBuilder(ItemUtils.getPlayerSkull(staff.asBukkit())).name(String.format("%s %s", staff.getGroup().getColoredName(), staff.getName())).build());
        }

        setReturn(40);
    }
}
