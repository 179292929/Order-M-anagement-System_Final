package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class KitchenView {
	
	private Stage stage;

    public KitchenView(Stage stage) {
        this.stage = stage;
    }

    public Parent getView() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0f172a;");

        // ===== LEFT SIDEBAR =====
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Order Management\n            System");
        title.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 18px; -fx-font-weight: bold;");
        Label sub = new Label("Control Every Order Effortlessly");
        sub.setTextFill(Color.GRAY);
        
        VBox titleBox = new VBox(5, title, sub);
    
     // SIDEBAR BUTTONS 
        Button kitchen = new Button("⏱ Kitchen Display");
        kitchen.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        kitchen.setMaxWidth(Double.MAX_VALUE);
        
        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logout.setMaxWidth(Double.MAX_VALUE);
        
        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #5da9dd;");
        backBtn.setMaxWidth(Double.MAX_VALUE);

       
		// ACTIONS
        logout.setOnAction(_ -> {
            LoginView login = new LoginView();
            login.start(stage);
        });

        backBtn.setOnAction(_ -> {
            DashboardView dashboard = new DashboardView();
            dashboard.start(stage);
        });
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Label staff = new Label("Server: Prasana Sujakhu\nStarted: 7:00 AM");

        sidebar.getChildren().addAll(titleBox, kitchen, spacer, staff, logout, backBtn );

        // ===== TOP BAR =====
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #1e293b;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        
        Label time = new Label("6:54 PM");
        time.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        topBar.getChildren().addAll(spacer1, time);
        // ===== LIVE TIME CODE =====
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, _ -> {
                    LocalTime currentTime = LocalTime.now();
                    time.setText(currentTime.format(formatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );

        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();

//        topBar.getChildren().addAll(spacer1, time);

        // ===== MAIN CONTENT =====
        HBox content = new HBox(20);
        content.setPadding(new Insets(20));

        VBox pendingCol = createColumn("PENDING", "#f59e0b");
        VBox preparingCol = createColumn("PREPARING", "#3b82f6");
        VBox readyCol = createColumn("READY", "#22c55e");

        content.getChildren().addAll(pendingCol, preparingCol, readyCol);

        // ===== ADD SAMPLE CARDS =====
        pendingCol.getChildren().add(createOrderCard("#2", "Table 4", "5 min",
                "2x Ribeye Steak\n2x French Fries\n1x Soup of the Day"));

        preparingCol.getChildren().add(createOrderCard("#1", "Table 2", "15 min",
                "2x Caesar Salad\n1x Chicken Alfredo\n2x Cappuccino"));

        readyCol.getChildren().add(createOrderCard("#3", "Table 8", "25 min",
                "3x Margherita Pizza\n2x Garlic Bread\n4x Soft Drink"));

        // ===== SET LAYOUT =====
        root.setLeft(sidebar);
//        root.setTop(topBar);
        root.setCenter(content);

        return root;
    }

    // ===== COLUMN =====
    private VBox createColumn(String title, String color) {

        VBox column = new VBox(15);
        column.setPrefWidth(300);

        Label header = new Label(title);
        header.setStyle(
                "-fx-background-color:" + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-padding: 10;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;"
        );

        column.getChildren().add(header);

        return column;
    }

    // ===== ORDER CARD =====
    private VBox createOrderCard(String orderNo, String table, String time, String items) {

        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle(
                "-fx-background-color: #1e293b;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;"
        );

        Label orderLabel = new Label(orderNo + "  (" + table + ")");
        orderLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label timeLabel = new Label("⏱ " + time);
        timeLabel.setTextFill(Color.LIGHTGRAY);

        Label itemsLabel = new Label(items);
        itemsLabel.setStyle("-fx-text-fill:white;");

        card.getChildren().addAll(orderLabel, timeLabel, itemsLabel);

        return card;
    }
}
