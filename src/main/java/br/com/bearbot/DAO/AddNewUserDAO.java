package br.com.bearbot.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddNewUserDAO {
    public AddNewUserDAO(long userID, long guildId, int msg_counter) throws Exception {
        ConnectionDAO connection = new ConnectionDAO();

        try {
            connection.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();// a
        }

        String insertQuery = "insert into users (userid , serverid , msgcounter) values (? , ? , ?)";

        PreparedStatement insertStm;

        insertStm = connection.db().prepareStatement(insertQuery);
        insertStm.setLong(1, userID);
        insertStm.setLong(2, guildId);
        insertStm.setInt(3, msg_counter);
        insertStm.execute();
        insertStm.close();

        connection.disconnect();
    }
}
