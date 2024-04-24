package fr.pandonia.hub.listeners.player;

import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.HubPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    private static final String CHAT_FORMAT = "%s%s%%s §8▪ %s%%s";

    private final PlayerService playerService;

    public PlayerChatListener(PlayerService playerService) {
        this.playerService = playerService;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        HubPlayer hubPlayer = playerService.getPlayer(event.getPlayer().getUniqueId());
        Group group = hubPlayer.getGroup();

        event.setFormat(String.format(CHAT_FORMAT, group.getPrefix().map(prefix -> prefix + " ").orElse(""), group.getColor(), group.getMessageFormat()));
    }
}
