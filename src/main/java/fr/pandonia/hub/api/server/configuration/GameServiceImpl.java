package fr.pandonia.hub.api.server.configuration;

import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServiceImpl implements GameService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public GameServiceImpl(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    @Override
    public GameConfiguration getConfiguration(int configurationId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT border_size, pvp_time, border_reduction_time, is_nether_enabled, " +
                            "gm.name as game_mode_name, gt.name as game_type_name " +
                            "FROM game_configurations gc " +
                            "JOIN game_modes gm on gm.id = gc.mode_id " +
                            "JOIN game_types gt on gt.id = gc.type_id " +
                            "WHERE gc.id = ?"
            )) {
                statement.setInt(1, configurationId);

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new IllegalArgumentException(String.format("No game found with id %d", configurationId));
                    }

                    return new GameConfiguration(
                            GameType.valueOf(result.getString("game_type_name")),
                            GameMode.valueOf(result.getString("game_mode_name")),
                            result.getInt("border_size"),
                            result.getInt("pvp_time"),
                            result.getInt("border_reduction_time"),
                            result.getBoolean("is_nether_enabled")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameScenario> getScenarios(int configurationId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT name FROM game_configuration_game_scenarios gcgs " +
                            "JOIN game_scenarios gs on gcgs.scenario_id = gs.id " +
                            "WHERE game_configuration_id = ?"
            )) {
                statement.setInt(1, configurationId);

                try (ResultSet result = statement.executeQuery()) {
                    List<GameScenario> scenarios = new ArrayList<>();

                    while (result.next()) {
                        scenarios.add(GameScenario.valueOf(result.getString("name")));
                    }

                    return scenarios;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch game scenarios", e);

            return Collections.emptyList();
        }
    }
}
