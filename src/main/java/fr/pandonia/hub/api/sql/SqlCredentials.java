package fr.pandonia.hub.api.sql;

public class SqlCredentials {

    private static final String JDBC_URL_FORMAT = "jdbc:mysql://%s:%d/%s";

    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    public SqlCredentials(String host, int port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toJdbcUrl() {
        return String.format(JDBC_URL_FORMAT, host, port, database);
    }
}
