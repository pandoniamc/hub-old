package fr.pandonia.hub.api.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionProvider implements SqlConnectionProvider {

    private final HikariDataSource dataSource;

    public HikariConnectionProvider(SqlCredentials credentials) {
        HikariConfig hikariConfiguration = new HikariConfig();
        hikariConfiguration.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfiguration.setJdbcUrl(credentials.toJdbcUrl());
        hikariConfiguration.setUsername(credentials.getUsername());
        hikariConfiguration.setPassword(credentials.getPassword());

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
