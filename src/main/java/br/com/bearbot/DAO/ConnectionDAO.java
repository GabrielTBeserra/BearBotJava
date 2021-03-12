package br.com.bearbot.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAO {
    private final String URL = "jdbc:mysql://urso-testes.mysql.uhserver.com:3306/urso_testes";
    private final String driver = "com.mysql.jdbc.Driver";
    private final String user = "ursoteste";
    private final String password = "@1Pluteiro";
    private Connection connection;

    public void connect() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        connection = DriverManager.getConnection(URL, user, password);

    }

    public Connection db() {
        return connection;
    }

    public void disconnect() throws SQLException {
        connection.close();

    }

}
