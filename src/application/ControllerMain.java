package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Klasse für den Haupt-Workspace.
 * Hier werden die Funktionen hinter den Button implementiert.
 * Je nachdem welcher Button geklickt wird, wird eine neue Scene geöffnet.
 */

public class ControllerMain implements Initializable {
	
	/**
	 * Deklarierung der GUI-Elemente.
	 */
	@FXML private AnchorPane rootPane;
	@FXML private JFXButton btnBenutzerverwaltung, btnNTicket, btnTickets,
							btnAuswertung, btnGruppenverwaltung, btnAbmelden;
	@FXML private Label lblAngemeldet;
	
	/**
	 * Deklarierung der Variablen.
	 */
	public Main main;
	public AnchorPane pane;
	
	/**
	 * Je nach Berechtigung werden die Button 'Gruppenverwaltung' bzw. 'Benutzerverwaltung' ausgeblendet.
	 */
	@Override
	public void initialize (URL url, ResourceBundle rb){
		
		if (ControllerLogin.getBerechtigung() == 2) {
			btnBenutzerverwaltung.setVisible(true);
			btnGruppenverwaltung.setVisible(true);
		}else {
			btnBenutzerverwaltung.setVisible(false);
			btnGruppenverwaltung.setVisible(false);
		}
		lblAngemeldet.setVisible(true);
		lblAngemeldet.setText("Käpsele "+ControllerLogin.getUser()+ " ist angemeldet");
	}
		

	/**
	 * Pane wird entfernt. Neues Pane mit entspechender fxml wird geladen.
	 * Es wird dem MainWindow hinzugefügt.
	 */
	@FXML
	public void handleNeuesTicket() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("NeuesTicketNeu.fxml"));
			rootPane.getChildren().add(pane);			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Pane wird entfernt. Neues Pane mit entspechender fxml wird geladen.
	 * Es wird dem MainWindow hinzugefügt.
	 */
	@FXML
	public void handleTickets() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("TicketsNeu.fxml"));
			rootPane.getChildren().add(pane);
									
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pane wird entfernt. Neues Pane mit entspechender fxml wird geladen.
	 * Es wird dem MainWindow hinzugefügt.
	 */
	@FXML
	public void handleAuswertung() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("Auswertung.fxml"));
			rootPane.getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Pane wird entfernt. Neues Pane mit entspechender fxml wird geladen.
	 * Es wird dem MainWindow hinzugefügt.
	 */
	@FXML
	public void handleBenutzerverwaltung() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("NeuBenutzerverwaltung.fxml"));
			rootPane.getChildren().add(pane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Pane wird entfernt. Neues Pane mit entspechender fxml wird geladen.
	 * Es wird dem MainWindow hinzugefügt.
	 */
	@FXML
	public void handleGruppenverwaltung() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("Gruppenverwaltung.fxml"));
			rootPane.getChildren().add(pane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * DB Verbindung wird getrennt. User und Berechtigung wird zurückgesetzt und das Fenster
	 * wird geschlossen. AnmeldeFenster wird geöffnet.
	 */
	@FXML
	public void handleAbmelden() 
	{
		Main.get_DBConnection().close();
		ControllerLogin.setUser(null);
		ControllerLogin.setBerechtigung(0);
		main.primaryStage.close();
		main.primaryStage.setHeight(0);
		main.primaryStage.setWidth(0);
		main.loginWindow();
	}
	
	/** ***************************************************************************************************************************************************
	* ******************************************************Implementierung der Getter und Setter*********************************************************
	******************************************************************************************************************************************************/

	public void setMain(Main main) {
		this.main = main;
	}
}