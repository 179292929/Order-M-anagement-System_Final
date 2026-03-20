package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.DBConnection;
import model.MenuItem;

public class MenuController {

    DBConnection db = new DBConnection();

    // ===== GET ALL MENU ITEMS =====
    public List<MenuItem> getAllItems() {

        List<MenuItem> list = new ArrayList<>();

        try {
            Connection conn = db.getConnection();

            String sql = "SELECT * FROM menu_items";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MenuItem(
                        rs.getInt("menu_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== ADD ITEM =====
    public void addItem(String name, double price, String category) {
        try {
            Connection conn = db.getConnection();

            String query = "INSERT INTO menu_items(name, price, category) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, category);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== DELETE ITEM =====
    public void deleteItem(int id) {
        try {
            Connection conn = db.getConnection();

            String query = "DELETE FROM menu_items WHERE menu_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== UPDATE ITEM =====
    public void updateItem(int id, String name, double price, String category) {
        try {
            Connection conn = db.getConnection();

            String query = "UPDATE menu_items SET name=?, price=?, category=? WHERE menu_id=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, category);
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}