package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loginWindow();
	}
	
	public void loginWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Login.fxml"));
			AnchorPane pane = loader.load();
			primaryStage.setMinHeight(200.00);
			primaryStage.setMinWidth(300.00);
			
			ControllerLogin loginWindowController = loader.getController();
			loginWindowController.setMain(this);
			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Main.fxml"));
			BorderPane pane = loader.load();
			primaryStage.setMinHeight(630);
			primaryStage.setMinWidth(900);
			
			ControllerMain mainWindowController = loader.getController();
			mainWindowController.setMain(this);
			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
