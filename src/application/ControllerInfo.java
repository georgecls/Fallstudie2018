package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControllerInfo implements Initializable{
	
	@FXML private JFXTextField id, titel, gruppe, erstelldatum, zieldatum, status;
	@FXML private JFXTextArea beschreibung, kommentar, ablehnungsgrund;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		id.setText(ControllerTickets.getAID());
		titel.setText(ControllerTickets.getNameS());
		erstelldatum.setText(ControllerTickets.getDatumS());
		zieldatum.setText(ControllerTickets.getEdatumS());
		status.setText(ControllerTickets.getStatusS());
		beschreibung.setText(ControllerTickets.getBeschrS());
		kommentar.setText(ControllerTickets.getKommS());
		ablehnungsgrund.setText(ControllerTickets.getAblehnungS());
	}
}