package fr.pandonia.hub.api.gui;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public abstract class Gui extends FastInv {

    public Gui(int rows, String title) {
        super(9 * rows, title);
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
}
