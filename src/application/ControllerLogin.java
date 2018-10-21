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
	
	/**
	 * Deklarierung der GUI-Elemente.
	 */
	@FXML private JFXTextField fieldBenutzer;
	@FXML private JFXPasswordField fieldKennwort;
	@FXML private Label labelFehler, labelBenutzer, labelPasswort;
	@FXML private JFXButton btnAnmelden;
	
	/**
	 * Deklarierung der Variablen.
	 */
	private static String user;
	private static int userid;
	private static int berechtigung;
	
	public static boolean berechtigt = false;
	
	public Main main;

	/**
	 * Diese Methode regelt die Anmeldung im Login-Fenster.
	 * Zuerst wird der Benutzername und das Kennwort abgefragt.
	 * Sind die Felder leer oder der Benutzername passt nicht zum Kennwort, so schlägt die Anmeldung fehl.
	 * Ist die Anmeldung erfolgreich, so wird der benutzer zum Hauptfenster weitergeleitet.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleLogin() throws SQLException
	{
		String benutzerName = fieldBenutzer.getText();
		String benutzerKennwort = fieldKennwort.getText();
		
		if(benutzerName.isEmpty() || benutzerKennwort.isEmpty()) 
		{
			labelFehler.setVisible(true);
			labelFehler.setTextFill(Color.RED);
		}
		else 
		{
			berechtigt = Benutzer.anmelden(benutzerName, benutzerKennwort);
			try
			{
				berechtigung = Benutzer.berechtigungPrüfen(benutzerName);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			if (berechtigt == true)
			{
				userid = Benutzer.getIdByName(benutzerName);
				user = benutzerName;
				benutzerName = null;
				benutzerKennwort = null;
				main.primaryStage.close();
				main.primaryStage.setHeight(0);
				main.primaryStage.setWidth(0);
				main.mainWindow();
			}
			else
			{
				labelFehler.setVisible(true);
				labelFehler.setTextFill(Color.RED);
			}		
		}
	}

	/** ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Getter und Setter*********************************************************
	 ******************************************************************************************************************************************************/
	
	public static int getUserid() {
		return userid;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		ControllerLogin.user = user;
	}
	
	public static int getBerechtigung() {
		return berechtigung;
	}

	public static void setBerechtigung(int berechtigung) {
		ControllerLogin.berechtigung = berechtigung;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}