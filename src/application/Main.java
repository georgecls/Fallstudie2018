package application;
	
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * In dieser Klasse wird die Anwendung gestartet und eine Verbindung zur Datenbank hergestellt.
 */

public class Main extends Application {
	
	/**
	 * Deklarierung der Variablen
	 */
	public Stage primaryStage;
	static DBConnector db = null;

	/**
	 * Start der Anwendung
	 * Verwendung der zuvor deklarierten Variable primaryStage (bisher kein Inhalt)
	 * Laden des Logos in primaryStage
	 * Aufruf der Methode loginWindow, welche die Stage Inhaltlich füllt
	 * @param primaryStage
	 * @return none	
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.getIcons().add(new Image("/IconmitRing.png"));
		loginWindow();
	}
	
	/**
	 * Erzeugen eines FXMLLoaders, welcher die Inhalte in pane lädt
	 * Fenstergröße wird auf Höhe 290.00 und Breite 370.00 gesetzt (größe nicht veränderbar)
	 * Es wird eine Szene mit den Inhalten von pane erzeugt
	 * Die neue Szene wird in die primaryStage geladen und angezeigt (Fenster wird geöffnet)
	 * @param none
	 * @return none
	 */
	public void loginWindow()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginNeu.fxml"));
			AnchorPane pane = loader.load();
			primaryStage.setMinHeight(290.00);
			primaryStage.setMinWidth(370.00);
			primaryStage.setMaxHeight(290.00);
			primaryStage.setMaxWidth(370.00);
			
			ControllerLogin loginWindowController = loader.getController();
			loginWindowController.setMain(this);
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();		
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * Erzeugen eines FXMLLoaders, welcher die Inhalte in pane lädt
	 * Fenstergröße wird auf mindestens Höhe  730 und Breite 1010 gesetzt
	 * Es wird eine Szene mit den Inhalten von pane erzeugt
	 * Die neue Szene wird in die primaryStage geladen und angezeigt (Fenster wird geöffnet)
	 * @param none
	 * @return none
	 */
	public void mainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainNeu.fxml"));
			BorderPane pane = loader.load();
			primaryStage.setMinHeight(730);
			primaryStage.setMinWidth(1010);
			
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
	
	/**
	 * 
	 * 
	 * @param args
	 * @return none
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Herstellen einer Datenbankverbindung
	 * Wenn db=null wird der Konstruktor III von DBConnector aufgerufen
	 * Wenn noch keine Datenbankverbindung besteht, wird ein Verbindungsversuch gestartet
	 * Mögliche Fehlermeldungen werden abgefangen und in der Konsole ausgegeben
	 * Rückgabe der db und Anzeige in der Konsole, dass Verbindung erfolgreich war
	 * @param none
	 * @return db
	 */
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