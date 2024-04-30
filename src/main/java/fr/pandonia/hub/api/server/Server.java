package fr.pandonia.hub.api.server;

import org.bukkit.ChatColor;

import java.util.UUID;

public class Server {

    private final int id;
    private final UUID ownerId;
    private final String name;
    private final ChatColor nameColor;
    private final ServerState state;
    private final int gameConfigurationId;
    private final int playerCount;
    private final int maxPlayers;

    public Server(int id, UUID ownerId, String name, ChatColor nameColor, ServerState state, int gameConfigurationId, int playerCount, int maxPlayers) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.nameColor = nameColor;
        this.state = state;
        this.gameConfigurationId = gameConfigurationId;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
    }

    public int getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public String getDisplayName() {
        return nameColor + name;
    }

    public ServerState getState() {
        return state;
    }

    public int getGameConfigurationId() {
        return gameConfigurationId;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
