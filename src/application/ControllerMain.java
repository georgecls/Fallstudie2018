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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerMain implements Initializable {
	
	//View
	@FXML private AnchorPane rootPane;
	@FXML private JFXButton btnBenutzerverwaltung, btnNTicket, btnTickets,
							btnAuswertung, btnGruppenverwaltung, btnAbmelden;
	
	
	public Main main;
	public AnchorPane pane;
	
	@Override
	public void initialize (URL url, ResourceBundle rb){
		
		if (ControllerLogin.berechtigung == 2) {
			btnBenutzerverwaltung.setVisible(true);
		}else {
			btnBenutzerverwaltung.setVisible(false);
		}
	
		}
		
	public void setMain(Main main) {
		this.main = main;
	}
		
	@FXML
	public void handleNeuesTicket() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("NeuesTicket.fxml"));
			rootPane.getChildren().add(pane);
			//btnNTicket.setStyle("#2eb7bf");
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleTickets() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("TicketsNeu.fxml"));
			rootPane.getChildren().add(pane);
			
			
//			Parent root;
//			Stage window = new Stage();
//			Scene scene;
//			root = FXMLLoader.load(getClass().getResource("ticket1234.fxml"));
//			scene = new Scene(root);
//			window.setScene(scene);
//			window.show();
									
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleAuswertung() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("Auswertung.fxml"));
			rootPane.getChildren().add(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void handleBenutzerverwaltung() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("Benutzerverwaltung.fxml"));
			rootPane.getChildren().add(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML public void handleGruppenverwaltung() {
		try {
			rootPane.getChildren().remove(pane);
			pane = FXMLLoader.load(getClass().getResource("Gruppenverwaltung.fxml"));
			rootPane.getChildren().add(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleAbmelden() {
		DBConnector db = new DBConnector();
		Main.get_DBConnection().close();
//		System.out.println("Datenbankverbindung geschlossen");
		ControllerLogin.user = null;
		main.primaryStage.close();
		main.primaryStage.setHeight(0);
		main.primaryStage.setWidth(0);
		main.loginWindow();
	}

}
