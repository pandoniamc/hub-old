package fr.pandonia.hub.api.friend;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriendService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public FriendService(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    public boolean isFriend(UUID playerId, UUID targetId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM friends WHERE player_id = ? AND target_id = ?")) {
                statement.setString(1, playerId.toString());
                statement.setString(2, targetId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    return result.next();
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to check player friendship", e);

            return false;
        }
    }
}
