package application;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerLogin {
	
	//View
	
	@FXML public TextField fieldBenutzer;
	@FXML public TextField fieldKennwort;
	@FXML public Label labelFehler;
	
	//Hiermit kann in in anderen Fenstern die Visibility gesetzt werden
	public static String user;
	public static int Berechtigung;
	
	public boolean berechtigt = false;
	
	
	public Main main;

	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void handleLogin() {
		String benutzerName = fieldBenutzer.getText();
		String benutzerKennwort = fieldKennwort.getText();
		
		berechtigt = Benutzer.anmelden(benutzerName, benutzerKennwort);
		System.out.println(berechtigt);
		/*try {
			int admin = Benutzer.berechtigungPrüfen(benutzerName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		if (berechtigt == true) {
			user = benutzerName;
			main.primaryStage.close();
			main.primaryStage.setHeight(0);
			main.primaryStage.setWidth(0);
			main.mainWindow();
		} else {
			labelFehler.setText("Benutzer/Passwort falsch");
		}
		
//		if (admin = 1){
		
		
	}

}
