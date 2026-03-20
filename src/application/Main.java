package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginView;
//import view.OrderView;
//import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		
		LoginView login = new LoginView();
        login.start(stage);
//		 OrderView view = new OrderView();
//	        view.start(stage);
	}
			
	public static void main(String[] args) {
		launch(args);
	}
}
