package fr.pandonia.hub.api.gui;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public abstract class Gui extends FastInv {

    private Gui parent;

    public Gui(int rows, String title) {
        super(9 * rows, title);
    }

    public void open(PandoniaPlayer player) {
        configure(player);
        super.open(player.asBukkit());
    }

    protected abstract void configure(PandoniaPlayer player);

    protected void setBackground(int color, int[] slots) {
        for (int slot : slots) {
            setItem(slot, new ItemBuilder(Material.STAINED_GLASS_PANE).data(color).name(" ").build());
        }
    }

    protected void setReturn(int slot) {
        setItem(slot, getButton("§cRetour en arrière", Material.ARROW), e -> parent.open((Player) e.getWhoClicked()));
    }

    protected void setGui(int slot, ItemStack item, Gui gui, PandoniaPlayer player, Group requiredGroup) {
        if (!requiredGroup.is(Group.STAFF) || player.is(requiredGroup)) {
            gui.parent = this;

            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();

            if (requiredGroup != Group.PLAYER) {
                lore.add(0, "§8Accès : " + requiredGroup.getName());
                lore.add(1, "");
            }

            if (!lore.isEmpty()) {
                lore.add("");
            }

            lore.add("§3§l» §bCliquez pour y accéder");

            meta.setLore(lore);
            item.setItemMeta(meta);

            setItem(slot, item, e -> {
                if (player.is(requiredGroup)) {
                    gui.open(player);
                }
            });
        }
    }

    protected void setGui(int slot, ItemStack item, Gui gui, PandoniaPlayer player) {
        setGui(slot, item, gui, player, Group.PLAYER);
    }

    protected ItemStack getButton(String name, ItemStack item, List<String> lore) {
        return new ItemBuilder(item)
                .name(name)
                .lore(lore)
                .flags()
                .build();
    }

    protected ItemStack getButton(String name, Material material, List<String> lore) {
        return getButton(name, new ItemStack(material), lore);
    }

    protected ItemStack getButton(String name, Material material) {
        return getButton(name, material, Collections.emptyList());
    }

    protected ItemStack getButton(String name, ItemStack item) {
        return getButton(name, item, Collections.emptyList());
    }
}
