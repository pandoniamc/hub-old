package fr.pandonia.hub.api.gui;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class Gui extends FastInv {

    private final HubPlayer player;

    public Gui(HubPlayer player, int rows, String title) {
        super(9 * rows, title);

        this.player = player;
    }

    protected void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> handler, Group group) {
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

    protected void setItem(int slot, ItemStack item, Group group) {
        setItem(slot, item, e -> {}, group);
    }

    protected ItemStack getButton(String name, Material material, List<String> lore) {
        ItemStack item = material == Material.SKULL_ITEM
                ? ItemUtils.getPlayerSkull()
                : new ItemStack(material);

        return new ItemBuilder(item)
                .name(name)
                .lore(lore)
                .flags()
                .build();
    }

    protected ItemStack getButton(String name, Material material) {
        return getButton(name, material, Collections.emptyList());
    }

    protected ItemStack getMenuButton(String name, Material material, List<String> lore, GuiClick click) {
        List<String> formattedLore = new ArrayList<>(lore);
        formattedLore.add("");
        formattedLore.add(String.format("§3§l» §b%s", click.getMessage()));

        return getButton(name, material, formattedLore);
    }
}
