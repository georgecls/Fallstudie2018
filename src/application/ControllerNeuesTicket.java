package application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerNeuesTicket {
	
	//View
	@FXML public TextField fieldTicketart;
	@FXML public TextField fieldErsteller;
	@FXML public DatePicker fieldErstelldatum;
	@FXML public DatePicker fieldZieldatum;
	@FXML public TextArea fieldText;
	
	
	public void initialize() {
		fieldErsteller.setText(ControllerLogin.user);
		fieldErstelldatum.setValue(LocalDate.now());
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
		System.out.println(ticketart);
		System.out.println(ersteller);
		System.out.println(erstelldatum);
		System.out.println(zieldatum);
		System.out.println(beschreibung);
	}
	
	
	
	

}
