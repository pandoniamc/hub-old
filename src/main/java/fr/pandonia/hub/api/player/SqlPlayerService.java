package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SqlPlayerService implements PlayerService {

    private final SqlConnectionProvider connectionProvider;

    private final Map<UUID, HubPlayer> cache = new HashMap<>();

    public SqlPlayerService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public HubPlayer loadData(UUID id) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT g.name AS name FROM players p JOIN `groups` g ON p.group_id = g.id WHERE p.uuid = ?")) {
                statement.setString(1, id.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException("Player not found");
                    }

                    HubPlayer player = new HubPlayer(Group.valueOf(result.getString("g.name")));
                    cache.put(id, player);

                    return player;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load data", e);
        }
    }

    @Override
    public void unloadData(UUID id) {
        cache.remove(id);
    }

    @Override
    public HubPlayer getPlayer(UUID id) {
        return Optional.ofNullable(cache.get(id)).orElseGet(() -> loadData(id));
    }
}
