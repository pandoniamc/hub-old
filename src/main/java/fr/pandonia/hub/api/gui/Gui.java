package fr.pandonia.hub.api.gui;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.events.PlayerOpenGuiEvent;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.BukkitUtils;
import fr.pandonia.hub.api.utils.DateUtils;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Gui extends FastInv {

    protected final PandoniaPlayer player;

    public Gui(int rows, String title, PandoniaPlayer player) {
        super(9 * rows, title);

        this.player = player;
    }

    protected void setBackground(int color, int[] slots) {
        for (int slot : slots) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE).data(color).name(" ").build());
        }
    }

    protected void setProfile(int slot) {
        setItem(slot, getItem("§3§lProfil", SkullUtils.getPlayerSkull(player), Arrays.asList(
                "§8▪ §fGrade: " + player.getGroup().getColoredName(),
                "§8▪ §fTemps Restant: §3" + player.getEndGroupDate().map(DateUtils::format).orElse("Aucune Expiration"),
                "",
                String.format("§8▪ §fKamas: §e%d ⛁", player.getKamas()),
                String.format("§8▪ §fHosts: §6%d ✯", player.getHosts()),
                String.format("§8▪ §fPré-Wl: §c%d", player.getPreWhitelist())
        )));
    }

    protected void setGui(int slot, ItemStack item, GuiType type, Group requiredGroup) {
        if (!requiredGroup.is(Group.STAFF) || player.is(requiredGroup)) {
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();

            if (requiredGroup != Group.PLAYER) {
                lore.add(0, "§8Accès : " + requiredGroup.getName());
                lore.add(1, "");
            }

            if (!lore.isEmpty()) {
                lore.add("");
                lore.add("§3§l» §bCliquez pour y accéder");
            }

            meta.setLore(lore);
            item.setItemMeta(meta);

            setItem(slot, item, e -> {
                if (player.is(requiredGroup)) {
                    openGui((Player) e.getWhoClicked(), type);
                }
            });
        }
    }

    protected void setGui(int slot, ItemStack item, GuiType gui) {
        setGui(slot, item, gui, Group.PLAYER);
    }

    protected ItemStack getItem(String name, ItemStack item, List<String> lore) {
        return new ItemBuilder(item)
                .name(name)
                .lore(lore)
                .flags()
                .build();
    }

    protected ItemStack getItem(String name, Material material, List<String> lore) {
        return getItem(name, new ItemStack(material), lore);
    }

    protected ItemStack getItem(String name, Material material) {
        return getItem(name, material, Collections.emptyList());
    }

    protected ItemStack getItem(String name, ItemStack item) {
        return getItem(name, item, Collections.emptyList());
    }

    protected void openGui(Player viewer, GuiType type) {
        BukkitUtils.callEvent(new PlayerOpenGuiEvent(viewer, player, type));
    }
}
