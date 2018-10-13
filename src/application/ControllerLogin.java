package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ControllerLogin {
	
	//View
	
//	@FXML public TextField fieldBenutzer;
//	@FXML public TextField fieldKennwort;
//	@FXML public Label labelFehler;
	
	//neueView
	
	@FXML private JFXTextField fieldBenutzer;
	@FXML private JFXPasswordField fieldKennwort;
	@FXML private Label labelFehler, labelBenutzer, labelPasswort;
	@FXML private JFXButton btnAnmelden;
	
	
	//Hiermit kann in in anderen Fenstern die Visibility gesetzt werden
	public static String user;
	public static int berechtigung;
	
	public boolean berechtigt = false;
	
	
	public Main main;

	public void setMain(Main main) {
		this.main = main;
	}
	
//	@Override
//    public void initialize(URL url, ResourceBundle rb) {
//		
//	}
	
	@FXML
	public void handleLogin() throws SQLException {
		String benutzerName = fieldBenutzer.getText();
		String benutzerKennwort = fieldKennwort.getText();
		
		berechtigt = Benutzer.anmelden(benutzerName, benutzerKennwort);
		
		try {
			berechtigung = Benutzer.berechtigungPrüfen(benutzerName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (berechtigt == true) {
			user = benutzerName;
			main.primaryStage.close();
			main.primaryStage.setHeight(0);
			main.primaryStage.setWidth(0);
			main.mainWindow();
		} else {
			labelFehler.setVisible(true);
			labelFehler.setTextFill(Color.RED);
//			labelFehler.setText("Benutzer/Passwort falsch");
		}
		
	}

}
