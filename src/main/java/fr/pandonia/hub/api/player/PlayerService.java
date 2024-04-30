package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerService {

    private final SqlConnectionProvider connectionProvider;

    private final Map<UUID, PandoniaPlayer> cache = new HashMap<>();

    public PlayerService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public PandoniaPlayer addPlayerInCache(UUID playerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT g.name, p.start_group_date, p.kamas, p.hosts, p.preWhitelist, p.lootboxs " +
                            "FROM players p " +
                            "JOIN `groups` g ON p.group_id = g.id " +
                            "WHERE p.minecraft_id = ?"
            )) {
                statement.setString(1, playerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException(String.format("No player found with id %s", playerId));
                    }

                    PandoniaPlayer player = new PandoniaPlayer(
                            playerId,
                            Group.valueOf(result.getString("g.name")),
                            result.getDate("p.start_group_date"),
                            result.getInt("p.kamas"),
                            result.getInt("p.hosts"),
                            result.getInt("p.preWhitelist"),
                            result.getInt("p.lootboxs")
                    );

                    cache.put(playerId, player);

                    return player;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load player data", e);
        }
    }

    public void removePlayerFromCache(UUID playerId) {
        cache.remove(playerId);
    }

    public PandoniaPlayer getPlayer(UUID playerId) {
        return Optional.ofNullable(cache.get(playerId)).orElseGet(() -> addPlayerInCache(playerId));
    }
}
