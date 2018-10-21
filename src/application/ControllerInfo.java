package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Klasse für die Anzeige von Ticket Details bei klick auf Button 'details' in Tab 'Tickets'.
 */

public class ControllerInfo implements Initializable{
	
	/**
	 * Deklarierung der GUI Elemente
	 */
	@FXML private JFXTextField id, titel, gruppe, erstelldatum, zieldatum, status;
	@FXML private JFXTextArea beschreibung, kommentar, ablehnungsgrund;

	/**
	 * Methode um TextFelder mit Daten aus ControllerTickets zu füllen.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		id.setText(ControllerTickets.getAID());
		titel.setText(ControllerTickets.getNameS());
		erstelldatum.setText(ControllerTickets.getEdatumS());
		zieldatum.setText(ControllerTickets.getDatumS());
		status.setText(ControllerTickets.getStatusS());
		beschreibung.setText(ControllerTickets.getBeschrS());
		kommentar.setText(ControllerTickets.getKommS());
		ablehnungsgrund.setText(ControllerTickets.getAblehnungS());
	}
}