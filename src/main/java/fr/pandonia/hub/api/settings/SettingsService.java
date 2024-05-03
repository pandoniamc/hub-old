package fr.pandonia.hub.api.settings;

import fr.pandonia.hub.api.settings.type.BooleanFriendsOnlyType;
import fr.pandonia.hub.api.settings.type.BooleanType;
import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SettingsService {

    private final SqlConnectionProvider connectionProvider;

    private final Map<UUID, Settings> cache = new HashMap<>();

    public SettingsService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Settings addSettingsInCache(UUID playerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT s.private_messages , s.mentions, s.friend_requests, s.player_visibility FROM settings s JOIN players p ON s.player_id = p.id WHERE minecraft_id = ?")) {
                statement.setString(1, playerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException(String.format("No settings found for player %s", playerId));
                    }

                    Settings settings = new Settings(
                            BooleanFriendsOnlyType.valueOf(result.getString("s.private_messages")),
                            BooleanType.valueOf(result.getBoolean("mentions")),
                            BooleanType.valueOf(result.getBoolean("friend_requests")),
                            BooleanFriendsOnlyType.valueOf(result.getString("player_visibility"))
                    );

                    cache.put(playerId, settings);

                    return settings;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeSettingsFromCache(UUID playerId) {
        cache.remove(playerId);
    }

    public Settings getSettings(UUID playerId) {
        return cache.computeIfAbsent(playerId, this::addSettingsInCache);
    }

    public void saveSettings(UUID playerId, Settings settings) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE settings s JOIN players p ON s.player_id = p.id SET s.private_messages = ?, s.mentions = ?, s.friend_requests = ?, s.player_visibility = ? WHERE minecraft_id = ?")) {
                statement.setString(1, settings.getPrivateMessages().get().getPersistedValue());
                statement.setBoolean(2, settings.getMentions().get().getPersistedValue());
                statement.setBoolean(3, settings.getFriendRequests().get().getPersistedValue());
                statement.setString(4, settings.getPlayerVisibility().get().getPersistedValue());
                statement.setString(5, playerId.toString());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
