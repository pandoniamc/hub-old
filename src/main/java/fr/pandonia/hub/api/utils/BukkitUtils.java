package fr.pandonia.hub.api.utils;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class BukkitUtils {

    public static Player getPlayer(PandoniaPlayer player) {
        return Bukkit.getPlayer(player.getId());
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
