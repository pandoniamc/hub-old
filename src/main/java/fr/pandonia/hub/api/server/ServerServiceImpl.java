package fr.pandonia.hub.api.server;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerServiceImpl implements ServerService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public ServerServiceImpl(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<Server> getServersFromOwner(UUID ownerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT s.id as server_id, name, color, state, game_configuration_id, player_count, max_players FROM servers s JOIN players p ON s.id = p.id WHERE p.minecraft_id = ?")) {
                statement.setString(1, ownerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    List<Server> servers = new ArrayList<>();

                    while (result.next()) {
                        int serverId = result.getInt("server_id");
                        String name = result.getString("name");
                        ChatColor nameColor = ChatColor.valueOf(result.getString("color"));
                        ServerState state = ServerState.valueOf(result.getString("state"));
                        int gameConfigurationId = result.getInt("game_configuration_id");
                        int playerCount = result.getInt("player_count");
                        int maxPlayers = result.getInt("max_players");

                        Server server = new Server(serverId, ownerId, name, nameColor, state, gameConfigurationId, playerCount, maxPlayers);
                        servers.add(server);
                    }

                    return servers;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch servers", e);
        }

        return Collections.emptyList();
    }
}
