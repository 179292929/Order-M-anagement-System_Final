package view;

import controller.LoginControl;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DBConnection;
  

public class LoginView {
	
	 public void start(Stage stage){

		 DBConnection connect = new DBConnection();
		 

			    VBox root = new VBox(20);
			    root.setAlignment(Pos.CENTER);
			    root.setStyle("-fx-background-color: #eeeeee;");

			    // ===== LOGO (optional) =====
//			    ImageView logo = new ImageView(new Image("file:cart.png")); // add your image path
//			    logo.setFitHeight(100);
//			    logo.setPreserveRatio(true);

			    // ===== TITLE =====
			    Label title = new Label("Order Management System");
			    title.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 24px; -fx-font-weight: bold;");

			    // ===== INPUT FIELDS =====
			    TextField usernameField = new TextField();
			    usernameField.setPromptText("Username");
			    usernameField.setMaxWidth(300);

			    PasswordField passwordField = new PasswordField();
			    passwordField.setPromptText("Password");
			    passwordField.setMaxWidth(300);

			    // ===== BUTTONS =====
			    Button loginBtn = new Button("Login");
			    loginBtn.setPrefWidth(200);
			    loginBtn.setStyle(
			        "-fx-background-color: #5da9dd;" +
			        "-fx-text-fill: white;" +
			        "-fx-background-radius: 30;" +
			        "-fx-font-size: 14px;"
			    );
			    loginBtn.setOnAction(_ -> {
			    	 connect.getConnection();
			    	
			    	});

			    Button exitBtn = new Button("Exit");
			    exitBtn.setPrefWidth(200);
			    exitBtn.setStyle(
			        "-fx-background-color: #ff3b3b;" +
			        "-fx-text-fill: white;" +
			        "-fx-background-radius: 30;" +
			        "-fx-font-size: 14px;"
			    );

			    Label message = new Label();
			    
			    // ===== ACTIONS =====
			    LoginControl lc1 = new LoginControl();
			    

			    loginBtn.setOnAction(_ -> {

			        String username = usernameField.getText();
			        String password = passwordField.getText();

			        // ADD VALIDATION HERE
			        if (username.isEmpty() || password.isEmpty()) {
			            message.setText("Please enter all fields");
			            message.setStyle("-fx-text-fill: orange;");
			            return; // stops further execution
			        }

			        //  DATABASE LOGIN
			        boolean status = lc1.login(username, password);

			        if (status) {
			            message.setText("Login Successful");
			            message.setStyle("-fx-text-fill: green;");

			            DashboardView dashboard = new DashboardView();
			            dashboard.start(stage);

			        } else {
			            message.setText("Invalid username or password");
			            message.setStyle("-fx-text-fill: red;");
			        }
			    });
			    

			    exitBtn.setOnAction(_ -> stage.close());

			    Hyperlink registerLink = new Hyperlink("Create Account");
			    registerLink.setOnAction(_ -> {
			        RegisterView reg = new RegisterView();
			        reg.start(stage);
			    });

			    // ===== ADD COMPONENTS =====
			    root.getChildren().addAll(
//			            logo,
			            title,
			            usernameField,
			            passwordField,
			            loginBtn,
			            exitBtn,
			            message,
			            registerLink
			    );

			    Scene scene = new Scene(root, 600, 500);
			    stage.setScene(scene);
			    stage.setTitle("Login");
			    stage.show();
			}
}
		 
		 
		 
