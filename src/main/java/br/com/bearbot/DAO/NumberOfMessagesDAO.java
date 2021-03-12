package br.com.bearbot.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumberOfMessagesDAO {
    public long QntMessages(long userID, long guildId) {
        long msgs = 0;

        ConnectionDAO connection = new ConnectionDAO();

        try {
            connection.connect();
        } catch (ClassNotFoundException e) {
//			e.printStackTrace();
        } catch (SQLException e) {
//			e.printStackTrace();
        }

        try {
            String query = "select * from users where userid = '" + userID + "' AND serverid = '" + guildId + "'";

            PreparedStatement sets = connection.db().prepareStatement(query);
            ResultSet results = sets.executeQuery();

            while (results.next()) {
                msgs = results.getInt("msgcounter");
            }

        } catch (Exception e) {
        }

        try {
            connection.disconnect();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return msgs;
    }

}
