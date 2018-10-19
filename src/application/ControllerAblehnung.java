package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Die Klasse ControllerAblehnung ist für das Handling des Abehnungsgrundes zuständig.
 * Es öffnet sich ein Fenster beim klicken auf den Button 'ablehnen' im Tab 'zu genehmigen'.
 * In dieses Fenster muss der Ablehnungsgrund eingetragen werden. Dieser Grund wird in der Datenbank gespeichert.
 */

public class ControllerAblehnung implements Initializable{

	/**
	 * Deklarierung der GUI Elemente.
	 */
	@FXML private JFXButton btnAbschicken;
	@FXML private JFXTextArea taAblehnung;
	@FXML private JFXTextField tfAntragid;
	@FXML private Label label;

	ControllerTickets ct;

	/**
	 * 
	 * @param ct
	 * @return none
	 */
	public void setControllerTickets(ControllerTickets ct){
		this.ct = ct;
	}

	/**
	 * Diese Methode ruft die Antrags-ID von ControllerTickets ab und setzt sie in das entsprechende TextFeld.
	 * 
	 * @param arg0, arg1
	 * @return none
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		label.setVisible(false);
		tfAntragid.setText(ControllerTickets.getAID());
	}

	/**
	 * Diese Methode regelt was passiert, wenn der Button 'abschicken' gedrückt wird.
	 * 
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	public void handleAbschicken() throws SQLException
	{
		if(taAblehnung.getText().equals(""))
		{
			label.setVisible(true);
		}
		else
		{
			Antrag.antragAblehnen(tfAntragid.getText(), taAblehnung.getText());
			Stage stage = (Stage) btnAbschicken.getScene().getWindow();
			ct.refresh();
			stage.close();
		}
		
	}
}
