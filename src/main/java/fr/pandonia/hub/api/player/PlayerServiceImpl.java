package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerServiceImpl implements PlayerService {

    private final SqlConnectionProvider connectionProvider;

    private final Map<UUID, PandoniaPlayer> cache = new HashMap<>();

    public PlayerServiceImpl(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public PandoniaPlayer cache(UUID playerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT g.id, p.start_group_date FROM players p JOIN `groups` g ON p.group_id = g.id WHERE p.minecraft_id = ?")) {
                statement.setString(1, playerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException("Player not found");
                    }

                    Group group = Group.valueOf(result.getInt("g.id"));
                    Date startGroupDate = result.getDate("p.start_group_date");

                    PandoniaPlayer player = new PandoniaPlayer(playerId, group, startGroupDate);
                    cache.put(playerId, player);

                    return player;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load data", e);
        }
    }

    @Override
    public void remove(UUID playerId) {
        cache.remove(playerId);
    }

    @Override
    public PandoniaPlayer get(UUID playerId) {
        return Optional.ofNullable(cache.get(playerId)).orElseGet(() -> cache(playerId));
    }
}
