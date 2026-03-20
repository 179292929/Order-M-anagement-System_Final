package controller;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.DBConnection;
import model.MenuItem;
import model.OrderItem;

public class OrderController {

    DBConnection db = new DBConnection();

    //  GET MENU ITEMS
    public ObservableList<MenuItem> getMenuItems() {

        ObservableList<MenuItem> items = FXCollections.observableArrayList();

        String sql = "SELECT * FROM menu_items";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new MenuItem(
                        rs.getInt("menu_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category") 
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    //  CREATE ORDER ITEM (LOCAL OBJECT)
    public OrderItem createOrderItem(MenuItem menuItem, int quantity) {
        return new OrderItem(menuItem.getName(), quantity, menuItem.getPrice());
    }

    // ✅ GET ALL ORDERS (for OrderQueueView)
    public ObservableList<String> getOrders() {

        ObservableList<String> orders = FXCollections.observableArrayList();

        String sql = "SELECT * FROM orders";

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String order = "Order #" + rs.getInt("order_id") +
                               " | Table " + rs.getInt("table_no") +
                               " | " + rs.getString("status");

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // ✅ GET ORDER ITEMS BY ORDER ID
    public ObservableList<OrderItem> getOrderItems(int orderId) {

        ObservableList<OrderItem> items = FXCollections.observableArrayList();

        String sql = """
            SELECT m.name, oi.quantity, m.price
            FROM order_items oi
            JOIN menu_items m ON oi.menu_id = m.menu_id
            WHERE oi.order_id = ?
        """;

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // ✅ UPDATE ORDER STATUS (important for button)
    public void updateOrderStatus(int orderId, String status) {

        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}