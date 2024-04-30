package fr.pandonia.hub.api.utils.skull;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.utils.BukkitUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullUtils {

    public static ItemStack getPlayerSkull() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) org.bukkit.SkullType.PLAYER.ordinal());
    }

    public static ItemStack getSkull(String url) {
        ItemStack item = getPlayerSkull();

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(url);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getPlayerSkull(Player owner) {
        return getSkull(owner.getName());
    }

    public static ItemStack getPlayerSkull(PandoniaPlayer player) {
        return getPlayerSkull(BukkitUtils.getPlayer(player));
    }
}
