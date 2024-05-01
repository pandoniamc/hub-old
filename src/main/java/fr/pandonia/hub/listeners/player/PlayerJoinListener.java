package fr.pandonia.hub.listeners.player;

import fr.mrmicky.fastinv.ItemBuilder;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.scoreboard.ScoreboardManager;
import fr.pandonia.hub.api.settings.SettingsService;
import fr.pandonia.hub.api.utils.skull.SkullUtils;
import fr.pandonia.hub.api.utils.Pair;
import fr.pandonia.hub.api.visibility.VisibilityManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

    private static final String HOTBAR_ITEM_NAME_FORMAT = "%s §8▪ §7Clic-Droit";

    private final PlayerService playerService;
    private final SettingsService settingsService;
    private final ScoreboardManager scoreboardManager;
    private final VisibilityManager visibilityManager;

    public PlayerJoinListener(PlayerService playerService, SettingsService settingsService, ScoreboardManager scoreboardManager, VisibilityManager visibilityManager) {
        this.playerService = playerService;
        this.settingsService = settingsService;
        this.visibilityManager = visibilityManager;
        this.scoreboardManager = scoreboardManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);

        player.sendMessage(JOIN_MESSAGE);

        // Hotbar
        Map<Integer, Pair<ItemStack, Pair<String, ChatColor>>> inventory = new HashMap<>();
        inventory.put(0, new Pair<>(new ItemStack(Material.COMPASS), new Pair<>("Menu principal", ChatColor.GREEN)));
        inventory.put(1, new Pair<>(SkullUtils.getPlayerSkull(player), new Pair<>("Profil", ChatColor.DARK_AQUA)));
        inventory.put(4, new Pair<>(new ItemStack(Material.CHEST), new Pair<>("Cosmétiques", ChatColor.RED)));
        inventory.put(7, new Pair<>(new ItemStack(Material.FEATHER), new Pair<>("Jump", ChatColor.GREEN)));
        inventory.put(8, new Pair<>(new ItemStack(Material.BEACON), new Pair<>("Hub", ChatColor.AQUA)));

        inventory.forEach((slot, item) -> {
            String name = "" + item.second().second() + ChatColor.BOLD + item.second().first() + ChatColor.RESET;
            player.getInventory().setItem(slot,
                    new ItemBuilder(item.first())
                            .name(String.format(HOTBAR_ITEM_NAME_FORMAT, name))
                            .build()
            );
        });

        playerService.addPlayerInCache(playerId);
        settingsService.addSettingsInCache(playerId);

        scoreboardManager.addPlayer(player);

        Bukkit.getOnlinePlayers().forEach(target -> {
            visibilityManager.hidePlayerIfNeeded(player, target);
            visibilityManager.hidePlayerIfNeeded(target, player);
        });

        event.setJoinMessage(null);
    }
}
