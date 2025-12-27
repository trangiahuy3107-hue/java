package dao;

import java.sql.*;
import database.DBConnection;

public class UserDAO {

    public static boolean login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // có bản ghi → đúng

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
