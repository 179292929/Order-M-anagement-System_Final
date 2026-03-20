package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TableManagementView {

    private Stage stage; // Stage reference to navigate back

    public TableManagementView(Stage stage) {
        this.stage = stage;
    }

    public Parent getView() {
    	//root
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f6f7;");

        // ================= LEFT SIDEBAR =================
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #ffffff;");

        Label title = new Label("Order Management\n            System");
        title.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label sub = new Label("Control Every Order Effortlessly");
        sub.setTextFill(Color.GRAY);
        
        VBox titleBox = new VBox(title, sub);
        
     // SIDEBAR BUTTONS 
       
        Button tables = new Button("🔲 Tables");
        tables.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        tables.setMaxWidth(Double.MAX_VALUE);
        
        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logout.setMaxWidth(Double.MAX_VALUE);
        
        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #5da9dd;");
        backBtn.setMaxWidth(Double.MAX_VALUE);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        Label staff = new Label("Server: Prasana Sujakhu\nStarted: 7:00 AM");

        sidebar.getChildren().addAll(title, titleBox, tables, spacer, staff, logout, backBtn);

        // ================= CENTER =================
        VBox center = new VBox(20);
        center.setPadding(new Insets(20));

        Label heading = new Label("Tables");
        heading.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 22px; -fx-font-weight: bold;");

        // Status summary
        HBox summary = new HBox(20);
        summary.getChildren().addAll(
                createSummaryCard("6", "Available", Color.GREEN),
                createSummaryCard("3", "Occupied", Color.RED),
                createSummaryCard("1", "Reserved", Color.ORANGE)
        );

        // Table grid
        GridPane tableGrid = new GridPane();
        tableGrid.setHgap(20);
        tableGrid.setVgap(20);

        int tableNum = 1;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {

                VBox tableCard;
                if (tableNum == 2 || tableNum == 4 || tableNum == 8) {
                    tableCard = createTableCard(tableNum, "occupied", Color.RED);
                } else if (tableNum == 5) {
                    tableCard = createTableCard(tableNum, "reserved", Color.ORANGE);
                } else {
                    tableCard = createTableCard(tableNum, "available", Color.GREEN);
                }

                tableGrid.add(tableCard, col, row);
                tableNum++;
            }
        }

        center.getChildren().addAll(heading, summary, tableGrid);

        // ================= RIGHT PANEL =================
        VBox rightPanel = new VBox(20);
        rightPanel.setPadding(new Insets(20));
        rightPanel.setPrefWidth(250);
        rightPanel.setStyle("-fx-background-color: #f9f9f9;");

        Label tableTitle = new Label("Table 4");
        tableTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label capacity = new Label("Capacity: 4 seats");
        Label status = new Label("Status: occupied");
        status.setStyle("-fx-text-fill: red;");

        Label order = new Label("Current Order: ORD-002");

        Button availableBtn = new Button("Mark as Available");
        availableBtn.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        availableBtn.setMaxWidth(Double.MAX_VALUE);

        Button reservedBtn = new Button("Mark as Reserved");
        reservedBtn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        reservedBtn.setMaxWidth(Double.MAX_VALUE);
//
//        // ===== BACK BUTTON =====
//        Button backBtn = new Button("Back to Dashboard");
//        backBtn.setStyle("-fx-background-color: #5da9dd;");
//        backBtn.setMaxWidth(Double.MAX_VALUE);

        // Back button action
        backBtn.setOnAction(_ -> {
            DashboardView dashboard = new DashboardView();
            dashboard.start(stage);
        });
        logout.setOnAction(_ -> {
            LoginView login = new LoginView();
            login.start(stage);
        });

        rightPanel.getChildren().addAll(
                tableTitle, capacity, status, order,
                new Separator(),
                availableBtn, reservedBtn
        );

        // ================= SET LAYOUT =================
        root.setLeft(sidebar);
        root.setCenter(center);
        root.setRight(rightPanel);

        return root;
    }

    // ================= TABLE CARD =================
    private VBox createTableCard(int number, String status, Color color) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setPrefSize(120, 120);

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: " + toRgbString(color) + ";" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        Label num = new Label(String.valueOf(number));
        num.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label seats = new Label("4 seats");

        Label statusLabel = new Label(status);
        statusLabel.setStyle(
                "-fx-background-color: " + toRgbString(color) + ";" +
                "-fx-text-fill: white; -fx-padding: 3 8 3 8;" +
                "-fx-background-radius: 10;"
        );

        card.getChildren().addAll(num, seats, statusLabel);
        return card;
    }

    // ================= SUMMARY CARD =================
    private VBox createSummaryCard(String count, String label, Color color) {
        VBox box = new VBox(5);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(120);

        box.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-radius: 10;" +
                "-fx-background-radius: 10;"
        );

        Label num = new Label(count);
        num.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label text = new Label(label);

        box.getChildren().addAll(num, text);
        return box;
    }

    // ================= COLOR HELPER =================
    private String toRgbString(Color color) {
        return "rgb(" +
                (int)(color.getRed() * 255) + "," +
                (int)(color.getGreen() * 255) + "," +
                (int)(color.getBlue() * 255) + ")";
    }
}