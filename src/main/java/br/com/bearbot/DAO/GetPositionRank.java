package br.com.bearbot.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetPositionRank {
    private ConnectionDAO conn;

    public GetPositionRank() {
        this.conn = new ConnectionDAO();

        try {
            this.conn.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getList(String serverId) {

        List<String> ret = new ArrayList<String>();
        try {
            String query = "select users.* from users where serverid = " + serverId + " order by users.msgcounter DESC";

            PreparedStatement sets = this.conn.db().prepareStatement(query);
            ResultSet results = sets.executeQuery();

            while (results.next()) {
                ret.add(results.getString("userid"));
            }

            this.conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


}
