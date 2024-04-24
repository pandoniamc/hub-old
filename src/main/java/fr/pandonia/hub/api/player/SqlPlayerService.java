package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlPlayerService implements PlayerService {

    private final SqlConnectionProvider connectionProvider;

    private final Map<UUID, HubPlayer> cache = new HashMap<>();

    public SqlPlayerService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public HubPlayer loadData(UUID id) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT g.id FROM players p JOIN `groups` g ON p.group_id = g.id WHERE p.uuid = ?")) {
                statement.setString(1, id.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException("Player not found");
                    }

                    HubPlayer player = new HubPlayer(id, Group.valueOf(result.getInt("g.id")));
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

    @Override
    public List<HubPlayer> getStaffList() {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT p.uuid, g.id FROM players p JOIN `groups` g ON p.group_id = g.id WHERE g.id <= ?")) {
                statement.setInt(1, Group.STAFF.ordinal() + 1);

                try (ResultSet result = statement.executeQuery()) {
                    List<HubPlayer> staff = new ArrayList<>();

                    while (result.next()) {
                        UUID id = UUID.fromString(result.getString("p.uuid"));
                        staff.add(new HubPlayer(id, Group.valueOf(result.getInt("g.id"))));
                    }

                    return staff;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load staff list", e);
        }
    }
}
