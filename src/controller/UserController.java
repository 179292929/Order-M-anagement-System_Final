package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.DBConnection;

public class UserController {

    DBConnection db = new DBConnection();

    public boolean registerUser(String username, String password, String role) {

        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

        try {
            Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}