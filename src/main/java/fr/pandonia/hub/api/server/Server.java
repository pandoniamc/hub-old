package fr.pandonia.hub.api.server;

public class Server {

    private final int id;
    private final ServerType type;
    private final ServerState state;
    private final int playerCount;
    private final int maxPlayers;

    public Server(int id, ServerType type, ServerState state, int playerCount, int maxPlayers) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.playerCount = playerCount;
        this.maxPlayers = maxPlayers;
    }

    public int getId() {
        return id;
    }

    public ServerType getType() {
        return type;
    }

    public ServerState getState() {
        return state;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
