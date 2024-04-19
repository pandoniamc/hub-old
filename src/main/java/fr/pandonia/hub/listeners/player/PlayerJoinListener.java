package fr.pandonia.hub.listeners.player;

import com.samjakob.spigui.item.ItemBuilder;
import fr.pandonia.hub.api.utils.ItemUtils;
import fr.pandonia.hub.api.utils.Pair;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerJoinListener implements Listener {

    private static final String[] JOIN_MESSAGE = {
            "§8» §f(§c!§f) §fBienvenue sur §3§lPandonia",
            "",
            "§8┃ §fInformations :",
            " §8• §fDe nombreux ajouts, modifications et résolutions de bugs ont été effectués.",
            " §8• §fRendez-vous dans le salon §7§o#nouvautés §rpour en savoir davantage.",
            "",
            "§8» §fEn vous souhaitant une §abonne expérience de jeu §f!"
    };

    private static final String HOTBAR_ITEM_NAME_FORMAT = "%s§r §8▪ §7Clic-Droit";


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);

        player.sendMessage(JOIN_MESSAGE);

        // Hotbar
        Map<Integer, Pair<ItemStack, String>> inventory = new HashMap<>();
        inventory.put(0, new Pair<>(new ItemStack(Material.COMPASS), "§a§lMenu principal"));
        inventory.put(1, new Pair<>(ItemUtils.getPlayerSkull(player), "§3§lProfil"));
        inventory.put(4, new Pair<>(new ItemStack(Material.CHEST), "§c§lCosmétiques"));
        inventory.put(7, new Pair<>(new ItemStack(Material.FEATHER), "§a§lJump"));
        inventory.put(8, new Pair<>(new ItemStack(Material.BEACON), "§b§lHub"));

        inventory.forEach((slot, item) ->
                player.getInventory().setItem(slot,
                        new ItemBuilder(item.getFirst())
                                .name(String.format(HOTBAR_ITEM_NAME_FORMAT, item.getSecond()))
                                .build()
                )
        );

        event.setJoinMessage(null);
    }
}
