package fr.pandonia.hub.guis.profile;

import fr.pandonia.hub.events.PlayerUpdateSettingsEvent;
import fr.pandonia.hub.guis.ChildGui;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.settings.Setting;
import fr.pandonia.hub.api.settings.Settings;
import fr.pandonia.hub.api.settings.type.SettingType;
import fr.pandonia.hub.api.utils.BukkitUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.List;

public class SettingsGui extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    private final Settings settings;

    public SettingsGui(PandoniaPlayer player, Settings settings) {
        super(5, "Paramètres", player, GuiType.PROFILE);

        this.settings = settings;

        setBackground(DyeColor.PURPLE.ordinal(), BACKGROUND_SLOTS);

        setReturn(40);

        setSetting(20, "§cRéceptions des MPs", Material.BOOK_AND_QUILL, settings.getPrivateMessages());
        setSetting(21, "§bMentions", Material.TRIPWIRE_HOOK, settings.getMentions());
        setSetting(22, "§eDemandes d'amis", Material.YELLOW_FLOWER, settings.getFriendRequests());
        setSetting(23, "§aVisibilité des joueurs", Material.BARRIER, settings.getPlayerVisibility());
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        BukkitUtils.callEvent(new PlayerUpdateSettingsEvent((Player) event.getPlayer()));
    }

    private <T extends Enum<T> & SettingType<?>> void setSetting(int slot, String name, Material material, Setting<T> setting) {
        T value = setting.get();
        List<String> lore = new ArrayList<>();

        for (SettingType<?> type : setting.getValues()) {
            lore.add(value == type ? "§8▪ " + type.getDisplayName() : "§7▪ " + type.getName());
        }

        setItem(slot, getItem(name, material, lore), e -> {
            int index = (setting.get().ordinal() + 1) % setting.getValues().length;
            setting.set(setting.getValues()[index]);

            new SettingsGui(player, settings).open((Player) e.getWhoClicked());
        });
    }
}
