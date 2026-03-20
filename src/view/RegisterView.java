package view;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class RegisterView {
	
	public void start(Stage stage){



		 VBox root = new VBox(20);
		    root.setAlignment(Pos.CENTER);
		    root.setStyle("-fx-background-color: #eeeeee;");

		    // ===== TITLE =====
		    Label title = new Label("Create Account");
		    title.setStyle("-fx-text-fill: #5da9dd; -fx-font-size: 22px; -fx-font-weight: bold;");

		    // ===== INPUTS =====
		    TextField userField = new TextField();
		    userField.setPromptText("Username");
		    userField.setMaxWidth(300);

		    PasswordField passField = new PasswordField();
		    passField.setPromptText("Password");
		    passField.setMaxWidth(300);

		    ComboBox<String> roleBox = new ComboBox<>();
		    roleBox.getItems().addAll("Admin", "Staff");
		    roleBox.setPromptText("Select Role");
		    roleBox.setMaxWidth(300);

		    // ===== BUTTONS =====
		    Button registerBtn = new Button("Register");
		    registerBtn.setPrefWidth(200);
		    registerBtn.setStyle(
		        "-fx-background-color: #5cb85c;" +
		        "-fx-text-fill: white;" +
		        "-fx-background-radius: 30;"
		    );

		    Button backBtn = new Button("Back to Login");
		    backBtn.setPrefWidth(200);
		    backBtn.setStyle(
		        "-fx-background-color: #5da9dd;" +
		        "-fx-text-fill: white;" +
		        "-fx-background-radius: 30;"
		    );

		    // ===== ACTIONS =====
		    backBtn.setOnAction(_ -> {
		        LoginView login = new LoginView();
		        login.start(stage);
		    });

		    registerBtn.setOnAction(_ -> {

		        String username = userField.getText().trim();
		        String password = passField.getText().trim();
		        String role = roleBox.getValue();

		        if (username.isEmpty() || password.isEmpty() || role == null) {
		            System.out.println("Please fill all fields");
		            return;
		        }

		        UserController controller = new UserController();
		        boolean success = controller.registerUser(username, password, role);

		        if (success) {
		            System.out.println("User Registered Successfully");

		            LoginView login = new LoginView();
		            login.start(stage);

		        } else {
		            System.out.println("Registration Failed");
		        }
		    });

		    // ===== ADD =====
		    root.getChildren().addAll(
		            title,
		            userField,
		            passField,
		            roleBox,
		            registerBtn,
		            backBtn
		    );

		    Scene scene = new Scene(root, 600, 500);
		    stage.setScene(scene);
		    stage.setTitle("Register");
		    stage.show();
		}
		
	
}
