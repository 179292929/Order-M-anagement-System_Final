package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.DBConnection;

public class RegisterController {

    DBConnection db = new DBConnection();

    public String login(String username, String password) {

        String sql = "SELECT role FROM users WHERE username=? AND password=?";

        try {
            Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("role"); // Admin or Staff
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login failed
    }
}