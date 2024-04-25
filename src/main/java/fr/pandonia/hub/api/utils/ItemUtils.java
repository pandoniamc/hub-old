package fr.pandonia.hub.api.utils;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

    public static String EMPTY_NAME = " ";

    public static ItemStack getPlayerSkull() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
    }

    public static ItemStack getPlayerSkull(String url) {
        ItemStack item = getPlayerSkull();

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(url);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getPlayerSkull(Player owner) {
        return getPlayerSkull(owner.getName());
    }
}
