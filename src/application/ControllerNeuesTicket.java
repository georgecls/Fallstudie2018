package application;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class ControllerNeuesTicket {
	
	/**
	 * Deklarierung der GUI-Elemente.
	 */
	@FXML private JFXTextField fieldErsteller, fieldTicketart;
	@FXML private JFXDatePicker fieldZieldatum, fieldErstelldatum;
	@FXML private JFXTextArea fieldText;
	@FXML private JFXButton btnAbschicken;	
	@FXML private Label fieldAntwort;
	@FXML private JFXComboBox cbGruppeZuweisen;
	
	/**
	 * Deklarierung der Variablen.
	 */
	private ObservableList<String> cbData;
	private String gruppeErsteller;
	
	/**
	 * Die Methode initialisiert das Fenster 'Neues Ticket'. Die ComboBox wird mit Werten aus der DB gefüllt.
	 * Der Ersteller ist die Person, welche momentan am Programm angemeldet ist.
	 * Das Erstelldatum ist das aktuelle Datum.
	 * 
	 * @param none
	 * @return none
	 */
	public void initialize() {
		try
		{
			cbData = Gruppe.getGruppennamen();
		}
    	catch (SQLException e1)
    	{
			e1.printStackTrace();
		}
		
    	cbGruppeZuweisen.setItems(cbData);
		
    	fieldErsteller.setText(ControllerLogin.getUser());
		fieldErstelldatum.setValue(LocalDate.now());
		fieldAntwort.setText(null);
		try
		{
			gruppeErsteller = Benutzer.getBearGruppeByUser(ControllerLogin.getUser());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Die Methode regelt was passiert, wenn der Button 'abschicken' im Fenster 'Neues Ticket' gedrückt wird.
	 * Hierbei werden alle eingetragenen Werte aus den GUI-Feldern entnommen und in Variablen gespeichert.
	 * Anschließend wird das Ticket angelegt.
	 * Ist eines der Felder leer, so erscheint ein Label mit der Anweisung, bitte alle Felder auszufüllen.
	 * 
	 * @param none
	 * @return none
	 */
	@FXML
	public void handleAbschicken() {
		
		String ticketart = fieldTicketart.getText();
		String beschreibung = fieldText.getText();
		LocalDate erstelldatum = fieldErstelldatum.getValue();
		LocalDate zieldatum = fieldZieldatum.getValue();
		
		if(cbGruppeZuweisen.getValue()==null) {
			fieldAntwort.setText("Bitte alle Felder ausfüllen");
			fieldAntwort.setVisible(true);
			fieldAntwort.setTextFill(Color.RED);		
		}
		else if(fieldZieldatum.getValue()==null) {
			fieldAntwort.setText("Bitte alle Felder ausfüllen");
			fieldAntwort.setVisible(true);
			fieldAntwort.setTextFill(Color.RED);
		}
		else if(beschreibung.equals(""))
		{
			fieldAntwort.setText("Bitte alle Felder ausfüllen");
			fieldAntwort.setVisible(true);
			fieldAntwort.setTextFill(Color.RED);
		}
		else if(ticketart.equals("")) {
			fieldAntwort.setText("Bitte alle Felder ausfüllen");
			fieldAntwort.setVisible(true);
			fieldAntwort.setTextFill(Color.RED);
		}
		else if(zieldatum.compareTo(erstelldatum)<0) {
			fieldAntwort.setText("Zieldatum darf nicht in der Vergangenheit liegen");
			fieldAntwort.setVisible(true);
			fieldAntwort.setTextFill(Color.RED);
		}
		else
		{
			ticketart = fieldTicketart.getText();
			String ersteller = fieldErsteller.getText();
			beschreibung = fieldText.getText();
			String gru = cbGruppeZuweisen.getValue().toString();
			
			try
			{
				Antrag.insertAntrag(ticketart, ersteller, erstelldatum, zieldatum, beschreibung, gruppeErsteller, gru);
				fieldAntwort.setText("Das Ticket '"+ticketart+"' wurde erstellt");
			}
			catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fieldTicketart.setText(null);
			fieldZieldatum.setValue(null);
			fieldText.setText(null);
			fieldAntwort.setTextFill(Color.BLACK);
			cbGruppeZuweisen.setValue(null);
		}
	}
}