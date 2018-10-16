package application;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerNeuesTicket {
	
	//View
	@FXML private JFXTextField fieldErsteller, fieldTicketart;
	@FXML private JFXDatePicker fieldZieldatum, fieldErstelldatum;
	@FXML private JFXTextArea fieldText;
	@FXML private JFXButton btnAbschicken;	
	@FXML private Label fieldAntwort;
	
	private String gruppeErsteller;
	
	
	public void initialize() {
		fieldErsteller.setText(ControllerLogin.user);
		fieldErstelldatum.setValue(LocalDate.now());
		fieldAntwort.setText(null);
		try {
			gruppeErsteller = Benutzer.getBearGruppeByUser(ControllerLogin.user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleAbschicken() {
		//get Methoden
		String ticketart = fieldTicketart.getText();
		String ersteller = fieldErsteller.getText();
		LocalDate erstelldatum = fieldErstelldatum.getValue();
		LocalDate zieldatum = fieldZieldatum.getValue();
		String beschreibung = fieldText.getText();
		
		//transfer Methoden
		try {
			Antrag.insertAntrag(ticketart, ersteller, erstelldatum, zieldatum, beschreibung, gruppeErsteller);
			fieldAntwort.setText("Das Ticket '"+ticketart+"' wurde erstellt");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fieldTicketart.setText(null);
		fieldZieldatum.setValue(null);
		fieldText.setText(null);
		
	}
}
