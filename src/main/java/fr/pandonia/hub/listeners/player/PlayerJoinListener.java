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
            "§8»§r (§c!§r) Bienvenue sur §3§lPandonia",
            "",
            "§8┃§r Informations :",
            " §8•§r De nombreux ajouts, modifications et résolutions de bugs ont été effectués.",
            " §8•§r Rendez-vous dans le salon §7§o#nouvautés§r pour en savoir davantage.",
            "",
            "§8»§r En vous souhaitant une bonne expérience de jeu !"
    };

    private static final String HOTBAR_ITEM_NAME_FORMAT = "%s §8▪ §r§7Clic-Droit";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);

        player.sendMessage(JOIN_MESSAGE);

        // Hotbar
        Map<Integer, Pair<ItemStack, String>> inventory = new HashMap<>();
        inventory.put(0, new Pair<>(new ItemStack(Material.COMPASS), "§aMenu principal"));
        inventory.put(1, new Pair<>(ItemUtils.getPlayerSkull(player), "§3Profil"));
        inventory.put(4, new Pair<>(new ItemStack(Material.CHEST), "§cCosmétiques"));
        inventory.put(7, new Pair<>(new ItemStack(Material.FEATHER), "§aJump"));
        inventory.put(8, new Pair<>(new ItemStack(Material.BEACON), "§bHub"));

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
