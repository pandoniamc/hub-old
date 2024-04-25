package fr.pandonia.hub.api.gui;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.HubPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class Gui extends FastInv {

    private Gui parent;

    public Gui(int rows, String title) {
        super(9 * rows, title);
    }

    public void open(HubPlayer player) {
        configure(player);
        super.open(player.asBukkit());
    }

    protected abstract void configure(HubPlayer player);

    protected void setReturn(int slot) {
        setItem(slot, getButton("§cRetour en arrière", Material.ARROW), e -> parent.open((Player) e.getWhoClicked()));
    }

    protected void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> handler, HubPlayer player, Group group) {
        if (player.is(group)) {
            ItemMeta meta = item.getItemMeta();

            List<String> lore = meta.getLore();
            lore.add(0, "§8Accès : " + group.getName());
            lore.add(1, "");
            meta.setLore(lore);

            item.setItemMeta(meta);

            setItem(slot, item, handler);
        }
    }

    protected void setSwitch(int slot, ItemStack item, HubPlayer player, Group group) {
        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();
        lore.add("");
        lore.add("§3§l» §bCliquez pour y accéder");
        meta.setLore(lore);

        item.setItemMeta(meta);

        setItem(slot, item, e -> {
        }, player, group);
    }

    protected void setGui(int slot, ItemStack item, Gui gui, HubPlayer player, Group group) {
        setItem(slot, prepareGui(item, gui), e -> gui.open(player), player, group);
    }

    protected void setGui(int slot, ItemStack item, Gui gui, HubPlayer player) {
        setItem(slot, prepareGui(item, gui), e -> gui.open(player));
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

    protected ItemStack getButton(String name, ItemStack item) {
        return getButton(name, item, Collections.emptyList());
    }

    protected ItemStack getButton(String name, Material material) {
        return getButton(name, new ItemStack(material));
    }

    private ItemStack prepareGui(ItemStack item, Gui gui) {
        gui.parent = this;

        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.getLore();
        if (!lore.isEmpty()) {
            lore.add("");
        }
        lore.add("§3§l» §bCliquez pour y accéder");
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}
