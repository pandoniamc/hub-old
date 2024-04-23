package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlPlayerService implements PlayerService {

    private final SqlConnectionProvider connectionProvider;

    public SqlPlayerService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Group getGroup(Player player) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT g.name AS name FROM players p JOIN `groups` g ON p.group_id = g.id WHERE p.uuid = ?")) {
                statement.setString(1, player.getUniqueId().toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException("Player not found");
                    }

                    return Group.valueOf(result.getString("name"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get group", e);
        }
    }
}
