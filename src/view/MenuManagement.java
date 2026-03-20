package view;

import java.util.List;

import controller.MenuController;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuManagement {

    private Stage stage;

    public MenuManagement(Stage stage) {
        this.stage = stage;
    }

    public Parent getView() {

        BorderPane root = new BorderPane();

        // ===== SIDEBAR =====
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #ffffff;");

        Label brand = new Label("Order Management\n            System");
        brand.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 18px; -fx-font-weight: bold;");

        Label subBrand = new Label("Control Every Order Effortlessly");
        subBrand.setTextFill(Color.GRAY);

        VBox brandBox = new VBox(5, brand, subBrand);

     // SIDEBAR BUTTONS 
      
        Button menu = new Button("👨‍🍳 Menu Management");
        menu.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        menu.setMaxWidth(Double.MAX_VALUE);
        
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

        sidebar.getChildren().addAll(
                brandBox,
                menu,
                spacer,
                staff,
                logout,
                backBtn
        );

     // ===== CENTER CONTENT =====
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: white;");

        Label title = new Label("Menu Management");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        MenuController controller = new MenuController();
        List<model.MenuItem> items = controller.getAllItems();

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        int col = 0, row = 0;

        for (model.MenuItem item : items) {
            grid.add(createMenuCard(
                    item.getId(),
                    item.getName(),
                    "$" + item.getPrice(),
                    item.getCategory() != null ? item.getCategory() : "N/A"
            ), col, row);

            col++;
            if (col == 2) {
                col = 0;
                row++;
            }
        }

        // Wrap grid inside scroll pane
        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);

        mainContent.getChildren().addAll(title, scroll);
        // ===== SET LAYOUT =====
        root.setLeft(sidebar);
        root.setCenter(mainContent);

        return root;
    }

    // ===== BUTTON STYLE METHOD =====
    private Button createBtn(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    // ===== MENU CARD =====
    private VBox createMenuCard(int id, String name, String price, String category) {

        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd;");

        Label lblName = new Label(name);
        lblName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblPrice = new Label(price);
        Label lblCategory = new Label("Category: " + category);

        // ✏️ EDIT BUTTON
        Button editBtn = new Button("Edit");

        editBtn.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");

        editBtn.setOnAction(e -> {

            TextInputDialog nameDialog = new TextInputDialog(name);
            nameDialog.setHeaderText("Edit Item Name");

            TextInputDialog priceDialog = new TextInputDialog(price.replace("$", ""));
            priceDialog.setHeaderText("Edit Price");

            String newName = nameDialog.showAndWait().orElse(name);
            String newPriceStr = priceDialog.showAndWait().orElse(price.replace("$", ""));

            double newPrice = Double.parseDouble(newPriceStr);

            MenuController controller = new MenuController();
            controller.updateItem(id, newName, newPrice, category);

            // refresh view (simple way)
            MenuManagement refreshed = new MenuManagement(stage);
            stage.getScene().setRoot(refreshed.getView());
        });

        card.getChildren().addAll(lblName, lblPrice, lblCategory, editBtn);

        return card;
    }
}