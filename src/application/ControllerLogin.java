package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerLogin {
	
	//View
	
	@FXML public TextField fieldBenutzer;
	@FXML public TextField fieldKennwort;
	@FXML public Label labelFehler;
	
	//Hiermit kann in in anderen Fenstern die Visibility gesetzt werden
	public String Benutzer;
	public int Berechtigung;
	
	public boolean berechtigt;
	
	
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
		//Int admin = login.istadmin (benutzerName);
		
		if (berechtigt == true) {
			Benutzer = benutzerName;
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
