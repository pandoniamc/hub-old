package fr.pandonia.hub.api.staff;

import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.sql.SqlConnectionProvider;

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

public class StaffService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public StaffService(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    public List<UUID> getStaffList(boolean connectedOnly) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT p.minecraft_id FROM players p JOIN `groups` g ON p.group_id = g.id WHERE g.id <= ?" + (connectedOnly ? " AND p.is_connected = true" : ""))) {
                statement.setInt(1, Group.STAFF.ordinal() + 1);

                try (ResultSet result = statement.executeQuery()) {
                    List<UUID> staff = new ArrayList<>();

                    while (result.next()) {
                        UUID staffId = UUID.fromString(result.getString("minecraft_id"));
                        staff.add(staffId);
                    }

                    return staff;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to load staff list", e);
        }

        return Collections.emptyList();
    }
}
