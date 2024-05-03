package fr.pandonia.hub.api.server;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public ServerService(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    public Server getServer(int id) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT s.id, st.name, s.state, s.player_count, s.max_players " +
                            "FROM servers s " +
                            "JOIN server_types st ON s.type_id = st.id " +
                            "WHERE s.id = ?"
            )) {
                statement.setInt(1, id);

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new IllegalArgumentException("Server not found");
                    }

                    return new Server(
                            result.getInt("s.id"),
                            ServerType.valueOf(result.getString("st.name").toUpperCase()),
                            ServerState.valueOf(result.getString("s.state").toUpperCase()),
                            result.getInt("s.player_count"),
                            result.getInt("s.max_players")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch server", e);
        }
    }

    public List<Server> getServersByType(ServerType type) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT s.id, st.name, s.state, s.player_count, s.max_players " +
                            "FROM servers s " +
                            "JOIN server_types st ON s.type_id = st.id " +
                            "WHERE st.name = ?"
            )) {
                statement.setString(1, type.name());

                try (ResultSet result = statement.executeQuery()) {
                    List<Server> servers = new ArrayList<>();

                    while (result.next()) {
                        Server server = new Server(
                                result.getInt("s.id"),
                                ServerType.valueOf(result.getString("st.name").toUpperCase()),
                                ServerState.valueOf(result.getString("s.state").toUpperCase()),
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

    public Map<ServerType, Integer> getPlayersCountByType() {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT st.name, SUM(s.player_count) AS player_count " +
                            "FROM servers s " +
                            "JOIN server_types st ON s.type_id = st.id " +
                            "GROUP BY st.name"
            )) {
                try (ResultSet result = statement.executeQuery()) {
                    Map<ServerType, Integer> players = new HashMap<>();

                    while (result.next()) {
                        players.put(
                                ServerType.valueOf(result.getString("st.name").toUpperCase()),
                                result.getInt("player_count")
                        );
                    }

                    return players;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch players count by type", e);
        }

        return Collections.emptyMap();
    }
}
