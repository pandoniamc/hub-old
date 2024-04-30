package fr.pandonia.hub.api.server;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public ServerService(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    public List<Server> getServersFromOwner(UUID ownerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT s.id, s.name, s.color, s.state, s.game_configuration_id, s.player_count, s.max_players " +
                            "FROM servers s " +
                            "JOIN players p ON s.id = p.id " +
                            "WHERE p.minecraft_id = ?"
            )) {
                statement.setString(1, ownerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    List<Server> servers = new ArrayList<>();

                    while (result.next()) {
                        Server server = new Server(
                                result.getInt("s.id"),
                                ownerId,
                                result.getString("s.name"),
                                ChatColor.valueOf(result.getString("s.color")),
                                ServerState.valueOf(result.getString("s.state")),
                                result.getInt("s.game_configuration_id"),
                                result.getInt("s.player_count"),
                                result.getInt("s.max_players")
                        );

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
