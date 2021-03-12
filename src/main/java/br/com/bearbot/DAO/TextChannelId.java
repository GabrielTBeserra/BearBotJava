package br.com.bearbot.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TextChannelId {
    private ConnectionDAO conn;

    public TextChannelId() {
        this.conn = new ConnectionDAO();

        try {
            this.conn.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public String getIds(String serverId) {
        String retorno = null;
        try {
            String query = "SELECT * from guilds_text_ids where serverid = " + serverId;

            PreparedStatement sets = this.conn.db().prepareStatement(query);
            ResultSet results = sets.executeQuery();

            while (results.next()) {
                retorno = results.getString("channelId");
            }

            this.conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public void setId(String guild, String id) throws SQLException {
        String insertQuery = "insert into guilds_text_ids (serverid , channelId) values (? , ?)";
        System.out.println(insertQuery);
        PreparedStatement insertStm;

        insertStm = this.conn.db().prepareStatement(insertQuery);
        insertStm.setString(1, guild);
        insertStm.setString(2, id);
        insertStm.execute();
        insertStm.close();

        this.conn.disconnect();
    }

    public void changeId(String guild, String id) throws SQLException {
        String query = "Update guilds_text_ids set channelId  = '" + id + "' where serverid = '" + guild + "'";
        System.out.println(query);
        PreparedStatement preparedStmt = this.conn.db().prepareStatement(query);

        preparedStmt.executeUpdate();

        this.conn.disconnect();
    }

    public void deleteId(String guildId) throws SQLException {
        String query = "Delete from guilds_musics_ids where serverid = " + guildId;

        PreparedStatement preparedStmt = this.conn.db().prepareStatement(query);

        preparedStmt.executeUpdate();

        this.conn.disconnect();
    }

}
