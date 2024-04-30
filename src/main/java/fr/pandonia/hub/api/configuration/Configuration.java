package fr.pandonia.hub.api.configuration;

import fr.pandonia.hub.api.sql.SqlCredentials;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    private final FileConfiguration configuration;

    public Configuration(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    public SqlCredentials getSqlCredentials() {
        String host = configuration.getString("database.host");
        int port = configuration.getInt("database.port");
        String username = configuration.getString("database.username");
        String password = configuration.getString("database.password");
        String database = configuration.getString("database.database");

        return new SqlCredentials(host, port, username, password, database);
    }

    public Location getLocation(TeleportLocation location) {
        return (Location) configuration.get(String.format("locations.%s", location.name().toLowerCase()));
    }
}
