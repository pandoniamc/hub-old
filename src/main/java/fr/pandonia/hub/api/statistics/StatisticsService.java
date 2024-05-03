package fr.pandonia.hub.api.statistics;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;
import fr.pandonia.hub.api.statistics.game.ArenaStatistics;
import fr.pandonia.hub.api.statistics.game.CaptureTheSheepStatistics;
import fr.pandonia.hub.api.statistics.game.EnmuPartyStatistics;
import fr.pandonia.hub.api.statistics.game.UHCStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StatisticsService {

    private final SqlConnectionProvider connectionProvider;

    public StatisticsService(SqlConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public Statistics getStatistics(UUID playerId) {
        try (Connection connection = connectionProvider.getConnection()) {
            String query = "SELECT " +
                    "a.kills, a.deaths, a.streak, " +
                    "c.kills, c.deaths, c.games_played, c.wins, c.captures, " +
                    "e.kills, e.deaths, e.games_played, e.wins, " +
                    "u.kills, u.deaths, u.games_played, u.wins " +
                    "FROM players p " +
                    "LEFT JOIN arena_statistics a ON p.id = a.player_id " +
                    "LEFT JOIN capture_the_sheep_statistics c ON p.id = c.player_id " +
                    "LEFT JOIN enmu_party_statistics e ON p.id = e.player_id " +
                    "LEFT JOIN uhc_statistics u ON p.id = u.player_id " +
                    "WHERE p.minecraft_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, playerId.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new SQLException("No statistics found for player " + playerId);
                    }

                    ArenaStatistics arenaStatistics = new ArenaStatistics(result.getInt("a.kills"), result.getInt("a.deaths"), result.getInt("a.streak"));
                    CaptureTheSheepStatistics captureTheSheepStatistics = new CaptureTheSheepStatistics(result.getInt("c.kills"), result.getInt("c.deaths"), result.getInt("c.games_played"), result.getInt("c.wins"), result.getInt("c.captures"));
                    EnmuPartyStatistics enmuPartyStatistics = new EnmuPartyStatistics(result.getInt("e.kills"), result.getInt("e.deaths"), result.getInt("e.games_played"), result.getInt("e.wins"));
                    UHCStatistics uhcStatistics = new UHCStatistics(result.getInt("u.kills"), result.getInt("u.deaths"), result.getInt("u.games_played"), result.getInt("u.wins"));

                    return new Statistics(arenaStatistics, captureTheSheepStatistics, enmuPartyStatistics, uhcStatistics);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
