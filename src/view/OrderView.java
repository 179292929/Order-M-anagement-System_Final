package view;

import controller.OrderController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.MenuItem;
import model.OrderItem;

public class OrderView {

    private OrderController controller = new OrderController();

    private TableView<MenuItem> menuTable = new TableView<>();
    private TableView<OrderItem> orderTable = new TableView<>();

    private ObservableList<MenuItem> menuItems;
    private ObservableList<OrderItem> orderItems = FXCollections.observableArrayList();

    private Label totalLabel = new Label("Total: 0");

    private Stage stage;

    public OrderView(Stage stage) {
        this.stage = stage;
    }
    // ================= GET VIEW =================
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

        Button menu = new Button("🍽 New Order");
        menu.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
        menu.setMaxWidth(Double.MAX_VALUE);

        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logout.setMaxWidth(Double.MAX_VALUE);

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

        sidebar.getChildren().addAll(
                brandBox,
                menu,
                spacer,
                staff,
                logout,
                backBtn
        );

        // ===== MENU TABLE =====
        menuItems = controller.getMenuItems();

        TableColumn<MenuItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<MenuItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        menuTable.getColumns().addAll(idCol, nameCol, priceCol);
        menuTable.setItems(menuItems);

        VBox menuBox = new VBox(10, new Label("Menu"), menuTable);

        // ===== ORDER TABLE =====
        TableColumn<OrderItem, String> orderName = new TableColumn<>("Item");
        orderName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<OrderItem, Integer> orderQty = new TableColumn<>("Qty");
        orderQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<OrderItem, Double> orderPrice = new TableColumn<>("Price");
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        orderTable.getColumns().addAll(orderName, orderQty, orderPrice);
        orderTable.setItems(orderItems);

        VBox orderBox = new VBox(10, new Label("Order"), orderTable, totalLabel);

        // ===== CONTROL =====
        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");

        Button addBtn = new Button("Add to Order");

        addBtn.setOnAction(_ -> {
            MenuItem selected = menuTable.getSelectionModel().getSelectedItem();

            if (selected == null) {
                showAlert("Please select a menu item!");
                return;
            }

            if (qtyField.getText().isEmpty()) {
                showAlert("Please enter quantity!");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText());
                if (qty <= 0) {
                    showAlert("Quantity must be greater than 0!");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid quantity!");
                return;
            }

            OrderItem item = controller.createOrderItem(selected, qty);
            orderItems.add(item);
            calculateTotal();
            qtyField.clear();
        });

        HBox controlBox = new HBox(10, qtyField, addBtn);
        controlBox.setPadding(new Insets(10));

        VBox mainContent = new VBox(20, menuBox, controlBox, orderBox);
        mainContent.setPadding(new Insets(20));

        // ===== SET ROOT =====
        root.setLeft(sidebar);
        root.setCenter(mainContent);

        return root;
    }
    // ===== ALERT =====
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===== CALCULATE TOTAL =====
    private void calculateTotal() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.getTotal();
        }
        totalLabel.setText(String.format("Total: %.2f", total));
    }
}