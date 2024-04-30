package fr.pandonia.hub.guis.profile;

import fr.pandonia.hub.HubPlugin;
import fr.pandonia.hub.api.gui.Gui;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.settings.Setting;
import fr.pandonia.hub.api.settings.Settings;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.settings.type.SettingType;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class SettingsGui extends Gui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 27, 35, 36, 37, 43, 44};

    private final SettingsService settingsService = HubPlugin.getInstance().getSettingsService();

    public SettingsGui() {
        super(5, "Paramètres");
    }

    @Override
    protected void configure(PandoniaPlayer player) {
        setBackground(DyeColor.PURPLE.ordinal(), BACKGROUND_SLOTS);

        setReturn(40);

        Settings settings = settingsService.getSettings(player.getId());

        setSetting(20, "§cRéceptions des MPs", Material.BOOK_AND_QUILL, player, settings, settings.getPrivateMessages());
        setSetting(21, "§bMentions", Material.TRIPWIRE_HOOK, player, settings, settings.getMentions());
        setSetting(22, "§eDemandes d'amis", Material.YELLOW_FLOWER, player, settings, settings.getFriendRequests());
        setSetting(23, "§aVisibilité des joueurs", Material.BARRIER, player, settings, settings.getPlayerVisibility());
    }

    private <T extends Enum<T> & SettingType> void setSetting(
            int slot,
            String name,
            Material material,
            PandoniaPlayer player,
            Settings settings,
            Setting<T> setting
    ) {
        T value = setting.get();
        List<String> lore = new ArrayList<>();

        for (SettingType type : setting.getValues()) {
            lore.add(value == type ? "§8▪ " + type.getDisplayName() : "§7▪ " + type.getName());
        }

        setItem(slot, getButton(name, material, lore), e -> {
            int index = (setting.get().ordinal() + 1) % setting.getValues().length;
            setting.set(setting.getValues()[index]);
            settingsService.saveSettings(player.getId(), settings);

            configure(player);
        });
    }
}
