package fr.pandonia.hub.api.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionProvider implements SqlConnectionProvider {

    private final HikariDataSource dataSource;

    public HikariConnectionProvider(FileConfiguration configuration) {
        String host = configuration.getString("database.host");
        int port = configuration.getInt("database.port");
        String username = configuration.getString("database.username");
        String password = configuration.getString("database.password");
        String database = configuration.getString("database.database");

        HikariConfig hikariConfiguration = new HikariConfig();
        hikariConfiguration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfiguration.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", host, port, database));
        hikariConfiguration.setUsername(username);
        hikariConfiguration.setPassword(password);

        dataSource = new HikariDataSource(hikariConfiguration);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        dataSource.close();
    }
}
