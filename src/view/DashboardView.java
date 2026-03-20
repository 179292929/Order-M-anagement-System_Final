package view;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DashboardView extends Application{

	 @Override
	    public void start(Stage stage) {

	        // ===== ROOT =====
	        BorderPane root = new BorderPane();
	        root.setStyle("-fx-background-color: #f5f6f7;");

	        // ===== LEFT SIDEBAR =====
	        VBox sidebar = new VBox(20);
	        sidebar.setPadding(new Insets(20));
	        sidebar.setPrefWidth(220);
	        sidebar.setStyle("-fx-background-color: #ffffff;");

	        Label head = new Label("Order Management\n            System");
	        head.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 18px; -fx-font-weight: bold;");

	        Label sub = new Label("Control Every Order Effortlessly");
	        sub.setTextFill(Color.GRAY);

	        VBox headBox = new VBox(head, sub);
	        
	     // SIDEBAR BUTTONS 
	        Button orderQueue = createMenuButton("📋 Order Queue", true);
	        Button newOrder = createMenuButton("🍽 New Order", true);
	        Button kitchen = createMenuButton("⏱ Kitchen Display", true);
	        Button tables = createMenuButton("🔲 Tables", true);
	        Button menu = createMenuButton("👨‍🍳 Menu Management", true);
	        
	     //  BUTTON ACTIONS 
	        menu.setOnAction(_ -> {
	            MenuManagement menuPage = new MenuManagement(stage);
	            Scene scene = new Scene(menuPage.getView(), 1000, 600);
	            stage.setScene(scene);
	        });
	        newOrder.setOnAction(_ -> {
	            OrderView orderView = new OrderView(stage);
	            Scene scene = new Scene(orderView.getView(), 1000, 600);
	            stage.setScene(scene);
	        });
	        orderQueue.setOnAction(_ -> {
	        	OrderQueueView queueView = new OrderQueueView(stage); // can reuse OrderView
	            Scene scene = new Scene(queueView.getView(), 1000, 600);
	            stage.setScene(scene);
	        });
//	        
	        kitchen.setOnAction(_ -> {
	            KitchenView view = new KitchenView(stage);
	            Scene scene = new Scene(view.getView(), 1200, 700);
	            stage.setScene(scene);
	        });

	        tables.setOnAction(_ -> {
	            TableManagementView page = new TableManagementView(stage);
	            Scene scene = new Scene(page.getView(), 1000, 600);
	            stage.setScene(scene);
	        });

	        Region spacer = new Region();
	        VBox.setVgrow(spacer, Priority.ALWAYS);

	        Label staff = new Label("Server: Prasana Sujakhu\nStarted: 7:00 AM");
	        
	        Button logout = new Button("Logout");
	        logout.setMaxWidth(Double.MAX_VALUE);
	        logout.setStyle("-fx-background-color: #ff3b3b; -fx-text-fill: white;");

	        logout.setOnAction(_ -> {
	            LoginView login = new LoginView();
	            login.start(stage);
	        });

	        sidebar.getChildren().addAll(
	                headBox,
	                orderQueue, newOrder, kitchen, tables, menu,
	                spacer,
	                staff,
	                logout
	        );

	      
	        // ===== RIGHT PANEL =====
	        VBox center = new VBox();
	        center.setAlignment(Pos.CENTER);
	        center.setPrefWidth(300);

	        Label emptyIcon = new Label("📋");
	        emptyIcon.setStyle("-fx-font-size: 60px; -fx-text-fill: #c0c0c0;");

	        Label emptyText = new Label("Select an order to view details");
	        emptyText.setTextFill(Color.GRAY);
	        
	        Label emptyText1 = new Label("Welcome to Order Management System");
	        emptyText1.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 30px; -fx-font-weight: bold;");
	        
	        center.getChildren().addAll(emptyIcon, emptyText, emptyText1);

	        // ===== SET AREAS =====
	        root.setLeft(sidebar);
	       
	        root.setCenter(center);

	        Scene scene = new Scene(root, 1000, 600);
	        stage.setScene(scene);
	        stage.setTitle("Dashboard");
	        stage.show();
	    }

	    // ===== MENU BUTTON =====
	    private Button createMenuButton(String text, boolean active) {
	        Button btn = new Button(text);
	        btn.setMaxWidth(Double.MAX_VALUE);

	        if (active) {
	            btn.setStyle("-fx-background-color: #5da9dd; -fx-text-fill: white;");
	        } else {
	            btn.setStyle("-fx-background-color: transparent;");
	        }

	        return btn;
	    }

	    public static void main(String[] args) {
	        launch();
	    }
}
	
	
	
