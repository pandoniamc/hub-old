package fr.pandonia.hub.api.settings;

import fr.pandonia.hub.api.settings.type.BooleanFriendsOnlyType;
import fr.pandonia.hub.api.settings.type.BooleanType;
import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SettingsService {

    private final SqlConnectionProvider connectionProvider;

    public SettingsService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Settings getSettings(UUID playerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT s.private_messages , s.mentions, s.friend_requests, s.player_visibility FROM settings s JOIN players p ON s.player_id = p.id WHERE minecraft_id = ?")) {
                statement.setString(1, playerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException("No settings found for player " + playerId);
                    }

                    return new Settings(
                            BooleanFriendsOnlyType.valueOf(result.getString("s.private_messages")),
                            BooleanType.valueOf(result.getBoolean("mentions")),
                            BooleanType.valueOf(result.getBoolean("friend_requests")),
                            BooleanFriendsOnlyType.valueOf(result.getString("player_visibility"))
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSettings(UUID playerId, Settings setting) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE settings s JOIN players p ON s.player_id = p.id SET s.private_messages = ?, s.mentions = ?, s.friend_requests = ?, s.player_visibility = ? WHERE minecraft_id = ?")) {
                statement.setString(1, setting.getPrivateMessages().get().name());
                statement.setBoolean(2, setting.getMentions().get().ordinal() == 0);
                statement.setBoolean(3, setting.getFriendRequests().get().ordinal() == 0);
                statement.setString(4, setting.getPlayerVisibility().get().name());
                statement.setString(5, playerId.toString());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
