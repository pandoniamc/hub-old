package fr.pandonia.hub.api.staff;

import fr.pandonia.hub.api.player.Group;
import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.api.sql.SqlConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffServiceImpl implements StaffService {

    private final Logger logger;
    private final SqlConnectionProvider connectionProvider;

    public StaffServiceImpl(Logger logger, SqlConnectionProvider connectionProvider) {
        this.logger = logger;
        this.connectionProvider = connectionProvider;
    }

    @Override
    public List<PandoniaPlayer> getStaffList(boolean connectedOnly) {
        List<PandoniaPlayer> staff = new ArrayList<>();

        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT p.minecraft_id, g.id, p.start_group_date FROM players p JOIN `groups` g ON p.group_id = g.id WHERE g.id <= ?" + (connectedOnly ? " AND p.is_connected = true" : ""))) {
                statement.setInt(1, Group.STAFF.ordinal() + 1);

                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        UUID id = UUID.fromString(result.getString("p.minecraft_id"));
                        Group group = Group.valueOf(result.getInt("g.id"));
                        Date startGroupDate = result.getDate("p.start_group_date");

                        staff.add(new PandoniaPlayer(id, group, startGroupDate));
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to load staff list", e);
        }

        return staff;
    }
}
