package fr.pandonia.hub.api.host;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.player.PlayerService;
import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.api.sql.SqlConnectionProvider;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HostService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;
    private final PlayerService playerService;
    private final ServerService serverService;

    public HostService(Logger logger, SqlConnectionProvider connectionProvider, PlayerService playerService, ServerService serverService) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
        this.playerService = playerService;
        this.serverService = serverService;
    }

    public List<Host> getHostsFromOwner(PandoniaPlayer owner) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT h.id, h.name, h.color, h.server_id, ht.name, ts.name, h.border_size, h.pvp_time, h.border_reduction_time, h.is_nether_enabled " +
                            "FROM hosts h " +
                            "JOIN host_types ht on h.type_id = ht.id " +
                            "JOIN team_sizes ts on h.team_size_id = ts.id " +
                            "JOIN players p on h.owner_id = p.id " +
                            "WHERE p.minecraft_id = ?"
            )) {
                statement.setString(1, owner.getId().toString());

                try (ResultSet result = statement.executeQuery()) {
                    List<Host> hosts = new ArrayList<>();

                    while (result.next()) {
                        hosts.add(
                                new Host(
                                        result.getString("h.name"),
                                        ChatColor.valueOf(result.getString("h.color")),
                                        serverService.getServer(result.getInt("h.server_id")),
                                        HostType.valueOf(result.getString("ht.name")),
                                        owner,
                                        TeamSize.valueOf(result.getString("ts.name")),
                                        result.getInt("h.border_size"),
                                        result.getInt("h.pvp_time"),
                                        result.getInt("h.border_reduction_time"),
                                        result.getBoolean("h.is_nether_enabled"),
                                        getScenarios(result.getInt("h.id"))
                                )
                        );
                    }

                    return hosts;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch hosts", e);

            return Collections.emptyList();
        }
    }

    private List<Scenario> getScenarios(int hostId) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT s.name " +
                            "FROM host_scenarios hs " +
                            "JOIN scenarios s on hs.scenario_id = s.id " +
                            "WHERE hs.host_id = ?"
            )) {
                statement.setInt(1, hostId);

                try (ResultSet result = statement.executeQuery()) {
                    List<Scenario> scenarios = new ArrayList<>();

                    while (result.next()) {
                        scenarios.add(Scenario.valueOf(result.getString("s.name")));
                    }

                    return scenarios;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch game scenarios", e);

            return Collections.emptyList();
        }
    }

    public List<Host> getHosts() {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT h.id, h.name, h.color, h.server_id, ht.name, ts.name, h.border_size, h.pvp_time, h.border_reduction_time, h.is_nether_enabled, p.minecraft_id " +
                            "FROM hosts h " +
                            "JOIN host_types ht on h.type_id = ht.id " +
                            "JOIN team_sizes ts on h.team_size_id = ts.id " +
                            "JOIN players p on h.owner_id = p.id"
            )) {
                try (ResultSet result = statement.executeQuery()) {
                    List<Host> hosts = new ArrayList<>();

                    while (result.next()) {
                        hosts.add(
                                new Host(
                                        result.getString("h.name"),
                                        ChatColor.valueOf(result.getString("h.color")),
                                        serverService.getServer(result.getInt("h.server_id")),
                                        HostType.valueOf(result.getString("ht.name")),
                                        playerService.getPlayer(UUID.fromString(result.getString("p.minecraft_id"))),
                                        TeamSize.valueOf(result.getString("ts.name")),
                                        result.getInt("h.border_size"),
                                        result.getInt("h.pvp_time"),
                                        result.getInt("h.border_reduction_time"),
                                        result.getBoolean("h.is_nether_enabled"),
                                        getScenarios(result.getInt("h.id"))
                                )
                        );
                    }

                    return hosts;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch hosts", e);

            return Collections.emptyList();
        }
    }
}
