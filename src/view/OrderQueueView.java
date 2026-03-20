package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OrderQueueView {

    private Stage stage;

    private Label orderTitle = new Label("Order #2");
    private Label orderStatus = new Label("pending");

    public OrderQueueView(Stage stage) {
        this.stage = stage;
    }

    // THIS is what your Scene will use
    public Parent getView() {

        BorderPane root = new BorderPane();

        // ================= LEFT SIDEBAR =================
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: white;");

        Label title = new Label("Order Management\n            System");
        title.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label subBrand = new Label("Control Every Order Effortlessly");
        subBrand.setTextFill(Color.GRAY);

        VBox brandBox = new VBox(5, title, subBrand);
        
        Button orderQueue = new Button("📋 Order Queue");
        orderQueue.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        orderQueue.setMaxWidth(Double.MAX_VALUE);

        Button logout = new Button("Logout");
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setStyle("-fx-background-color: red; -fx-text-fill: white;");

        Button backBtn = new Button("Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        backBtn.setMaxWidth(Double.MAX_VALUE);

        // ACTIONS
        logout.setOnAction(_ -> {
            LoginView login = new LoginView();
            login.start(stage);
        });

        backBtn.setOnAction(_ -> {
            DashboardView dashboard = new DashboardView();
            try {
                dashboard.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Label staff = new Label("Server: Prasana Sujakhu\nStarted: 7:00 AM");

        sidebar.getChildren().addAll(brandBox, orderQueue, spacer, staff, logout, backBtn);

        // ================= CENTER =================
        VBox center = new VBox(15);
        center.setPadding(new Insets(20));

        Label header = new Label("Active Orders");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label sub = new Label("4 orders in queue");
        sub.setTextFill(Color.GRAY);

        VBox order1 = createOrderCard("#1", "preparing", "Table 2", "15 mins ago", "$51.96");
        VBox order2 = createOrderCard("#2", "pending", "Table 4", "5 mins ago", "$79.95");
        VBox order3 = createOrderCard("#3", "ready", "Table 8", "25 mins ago", "$120.50");

        center.getChildren().addAll(header, sub, order1, order2, order3);

        // ================= RIGHT PANEL =================
        VBox right = new VBox(15);
        right.setPadding(new Insets(20));

        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER_LEFT);

        Label tableInfo = new Label("Table 4 • 5 mins ago");

        Button statusBtn = new Button("Start Preparing");
        statusBtn.setStyle("-fx-background-color: #1e66ff; -fx-text-fill: white;");

        top.getChildren().addAll(orderTitle, tableInfo, statusBtn);

        orderStatus.setStyle(
                "-fx-background-color: #ffeaa7;" +
                "-fx-padding: 5 10 5 10;" +
                "-fx-background-radius: 10;"
        );

        VBox itemsBox = new VBox(10);
        itemsBox.setPadding(new Insets(10));

        itemsBox.getChildren().addAll(
                createItem("Ribeye Steak", "x2", "$65.98"),
                createItem("French Fries", "x2", "$9.98"),
                createItem("Soup of the Day", "x1", "$8.99")
        );

        VBox totalBox = new VBox(5);
        totalBox.setPadding(new Insets(10));

        totalBox.getChildren().addAll(
                new Label("Subtotal: $79.95"),
                new Label("Tax (10%): $8.00"),
                new Label("TOTAL: $87.95")
        );

        right.getChildren().addAll(top, orderStatus, new Label("Order Items"), itemsBox, totalBox);

        // ================= ROOT =================
        root.setLeft(sidebar);
        root.setCenter(center);
        root.setRight(right);

        return root;
    }

    // ================= ORDER CARD =================
    private VBox createOrderCard(String id, String status, String table, String time, String price) {

        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10;");

        Label idLabel = new Label(id + "  " + status);
        idLabel.setStyle("-fx-font-weight: bold;");

        Label timeLabel = new Label(time);
        timeLabel.setTextFill(Color.GRAY);

        Label tableLabel = new Label(table);
        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-weight: bold;");

        card.getChildren().addAll(idLabel, timeLabel, tableLabel, priceLabel);

        card.setOnMouseClicked(_ -> {
            orderTitle.setText("Order " + id);
            orderStatus.setText(status);
        });

        return card;
    }

    // ================= ITEM ROW =================
    private HBox createItem(String name, String qty, String price) {

        HBox box = new HBox(10);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 8;");

        Label n = new Label(name);
        Label q = new Label(qty);
        Label p = new Label(price);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        box.getChildren().addAll(n, spacer, q, p);

        return box;
    }
}