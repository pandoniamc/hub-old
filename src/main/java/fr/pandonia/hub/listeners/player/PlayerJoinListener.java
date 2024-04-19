package fr.pandonia.hub.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(20);

        player.sendMessage(JOIN_MESSAGE);

        event.setJoinMessage(null);
    }
}
