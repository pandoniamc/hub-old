package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChatListener implements Listener {

    private final PlayerService playerService;

    public PlayerChatListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        PandoniaPlayer player = playerService.getPlayer(playerId);

        event.setFormat(player.getGroup().getChatFormat());
    }
}
