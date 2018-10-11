package application;
	
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public Stage primaryStage;
	static DBConnector db = null;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		loginWindow();
	}

	
public static MysqlCon getSQL()
{
	return MysqlCon.getDbCon();
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
	
	public static DBConnector get_DBConnection()
	{
		if(null == db) db = new DBConnector("sql7258916", "sql7258916", "SfSFVG296y");
		if(db.is_connected() == false)
		{
			try
			{
				db.ConnectToDatabase();
			}
			catch (InstantiationException e)
			{
				System.out.println(String.format("Fehler bei Herstellung der Datenbank-Verbindung: %s", e.getMessage()));
			}
			catch (IllegalAccessException e)
			{
				System.out.println(String.format("Fehler bei Herstellung der Datenbank-Verbindung: %s", e.getMessage()));
			}
			String error = db.get_last_error();
			if(error != null)
			{
				System.out.println(String.format("Fehler bei Herstellung der Datenbank-Verbindung: %s", error));
				return null;
			}
			else
			{
				System.out.println("Datenbank-Verbindung wurde erfolgreich hergestellt");
			}
		}

		return db;
	}
}
