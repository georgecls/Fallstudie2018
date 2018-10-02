package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerLogin {
	
	//View
	
	@FXML public TextField fieldBenutzer;
	@FXML public TextField fieldKennwort;
	@FXML public Label labelFehler;
	
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
			main.primaryStage.close();
			main.primaryStage.setHeight(0);
			main.primaryStage.setWidth(0);
			main.mainWindow();
		} else {
			labelFehler.setText("Benutzer oder Passwort falsch");
		}
		
//		if (admin = 1){
		
	/*
		if ((benutzerName.equals("Wolfgang")) && (benutzerKennwort.equals("1234"))
				|| (benutzerName.equals("Nicolassi")) && (benutzerKennwort.equals("4711"))) {
			main.primaryStage.close();
			main.primaryStage.setHeight(0);
			main.primaryStage.setWidth(0);
			main.mainWindow();
		} else {
			labelFehler.setText("Falsch");
		}*/
		
	}

}
