package br.com.bearbot.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerifyExistenceUserDAO {
    public boolean CheckExistenceUserDAO(long guildId, long userID) {
        ConnectionDAO connection = new ConnectionDAO();
        boolean isChecked = false;
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
                isChecked = true;
            }
            connection.disconnect();

        } catch (Exception e) {
        }

        return isChecked;
    }
}
