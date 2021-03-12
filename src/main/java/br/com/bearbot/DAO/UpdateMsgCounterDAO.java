package br.com.bearbot.DAO;

import java.sql.PreparedStatement;

public class UpdateMsgCounterDAO {
    public UpdateMsgCounterDAO(long idUser, long idServer) {

        try {
            String query = "Update users set msgcounter = msgcounter + 1 where userid = '" + idUser
                    + "' AND serverid = '" + idServer + "'";
            ConnectionDAO connection = new ConnectionDAO();
            connection.connect();

            PreparedStatement preparedStmt = connection.db().prepareStatement(query);

            preparedStmt.executeUpdate();

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
