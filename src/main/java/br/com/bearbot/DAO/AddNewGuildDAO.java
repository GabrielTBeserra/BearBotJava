package br.com.bearbot.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNewGuildDAO {
    public AddNewGuildDAO(long guildID) throws Exception {
        ConnectionDAO connection = new ConnectionDAO();
// TESTE AQUI
        try {
            connection.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String insertQuery = "insert into guildserver (serverid) values (?)";

        PreparedStatement insertStm;

        insertStm = connection.db().prepareStatement(insertQuery);
        insertStm.setLong(1, guildID);
        insertStm.execute();
        insertStm.close();

        connection.disconnect();

    }
}
