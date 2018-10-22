package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControllerBenutzerverwaltung  implements Initializable {
	
	/**
	 * Deklarierung der GUI-Elemente.
	 */
	@FXML private TableView<Benutzer> tvBenutzerverwaltung;
	@FXML private TableColumn<Benutzer, String> benutzer_Col, gruppe_Col, berechtigung_Col;
	@FXML private JFXButton btnHinzufuegen, btnAendern, btnLoeschen, btnClear, btnWiederherstellen;
	@FXML private JFXTextField fieldBenutzer, fieldPasswort;
	@FXML private JFXComboBox boxGruppe, boxBerechtigung;
	@FXML private Label label;
	
	/**
	 * Deklarierung der Variablen.
	 */
	private ObservableList<Integer> boxBerechtigungListe = FXCollections.observableArrayList(0,1,2);
	private ObservableList<String> boxGruppeListe;
	private ObservableList<Benutzer> data;
	public static String b12, g12;
	public static int a12;
	
	/**
	 * Diese Methode initialisiert die Oberfl�che.
	 * Die Tabelle Benutzer und die ComboBox werden gef�llt.
	 * Die Text Felder werden 'null' gesetzt. 
	 * 
	 * @param url, rb
	 * @return none
	 */
	@Override
	public void initialize (URL url, ResourceBundle rb){	
		try
		{
			data = Benutzer.getBenutzerverwaltung();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		data.forEach((benutzer) -> {
			Benutzer b1 = (Benutzer) benutzer;
		});
		
//		�bergabe aller aktiven Gruppen
		try
		{
			boxGruppeListe = Gruppe.getGruppennamen();	
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}				
				
		benutzer_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("benutzername"));
		gruppe_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("gruppe"));
		berechtigung_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("berechtigung"));
		
		tvBenutzerverwaltung.setItems(data);
		
		boxGruppe.setItems(boxGruppeListe);
		boxBerechtigung.setItems(boxBerechtigungListe);
		
		fieldBenutzer.setText("");
		fieldPasswort.setText("");
		boxGruppe.setValue(null);
		boxBerechtigung.setValue(null);
		label.setVisible(false);
	}
	
	/**
	 * Die Methode regelt was passiert, wenn eine Zeile in der Tabelle ausgew�hlt wird.
	 * Die Daten aus der Tabelle werden in Variablen gespeichert und in die TextFelder gesetzt.
	 * 
	 * @param none
	 * @return none
	 */
	@FXML
	public void onMouseClicked () {
		try {
			Benutzer b1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
			b12 = b1.getBenutzername();
								
			Benutzer g1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
			g12 = g1.getGruppe();
						
			Benutzer a1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
			a12 = a1.getBerechtigung();
			
			fieldBenutzer.setText(b12);
			boxGruppe.setValue(g12);
			boxBerechtigung.setValue(a12);	
			label.setVisible(false);
		}
		catch(NullPointerException npe) {
			npe.printStackTrace();
		}
	}
	
	/**
	 * Diese Methode regelt was passiert, wenn der Button 'hinzuf�gen' gedr�ckt wird.
	 * Wenn nicht alle Felder ausgef�llt sind, so kommt eine Meldung.
	 * Sind alle Felder ausgef�llt, so wird gepr�ft, ob der Benutzer bereits besteht.
	 * Ist dies nicht der Fall, so wird der benutzer angelegt.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
    public void handleHinzufuegen() throws SQLException {
		
        String benutzer = fieldBenutzer.getText();
        String passwort = fieldPasswort.getText();
        
		if(boxGruppe.getValue()==null){
			label.setVisible(true);
			label.setText("Alle Felder m�ssen ausgef�llt sein!");
			label.setTextFill(Color.RED);
		}
		else if(boxBerechtigung.getValue() == null){
			label.setVisible(true);
			label.setText("Alle Felder m�ssen ausgef�llt sein!");
			label.setTextFill(Color.RED);
		}
        else if (benutzer.equals("")) {
			label.setVisible(true);
			label.setText("Alle Felder m�ssen ausgef�llt sein!");
			label.setTextFill(Color.RED);
		}
        else if (passwort.equals("")){
			label.setVisible(true);
			label.setText("Alle Felder m�ssen ausgef�llt sein!");
			label.setTextFill(Color.RED);
		}
		else
		{
			benutzer = fieldBenutzer.getText().toString();
			passwort = fieldPasswort.getText().toString();
		    String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
		    int berechtigung =  (int) boxBerechtigung.getSelectionModel().getSelectedItem();

            Boolean selberName = Benutzer.selberName(benutzer);   

	        if (selberName == true)
	        {
	          	label.setVisible(true);
	           	label.setText("Der Benutzer existiert bereits!");
	           	label.setTextFill(Color.RED);
	        }
	        else
            {
	           	Benutzer.insertBenutzer(benutzer, passwort, berechtigung, gruppe);                   
	           	initialize (null, null);
	            label.setVisible(true);
	            label.setText("Benutzer '"+benutzer+"' hinzugef�gt");
	            label.setTextFill(Color.BLACK);
	        }
		}
    }

	/**
	 * Diese Methode regelt was passiert, wenn der Button '�ndern' gedr�ckt wird.
	 * Wenn nicht alle Felder ausgef�llt sind, so kommt eine Meldung.
	 * Sind alle Felder ausgef�llt, so wird der Benutzer ge�ndert, je nachdem was eingetragen wurde.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleAendern() throws SQLException{
		String benutzer = fieldBenutzer.getText();
		String passwort = fieldPasswort.getText();

		if (benutzer.equals("")) {
			label.setVisible(true);
			label.setText("Es ist kein Benutzer zum �ndern ausgew�hlt!");
			label.setTextFill(Color.RED);
		}
		else if(boxGruppe.getValue()==null){
			label.setVisible(true);
			label.setText("Berechtigung und Gruppe m�ssen ausgew�hlt sein!");
			label.setTextFill(Color.RED);
		}
		else if(boxBerechtigung.getValue() == null){
			label.setVisible(true);
			label.setText("Berechtigung und Gruppe m�ssen ausgew�hlt sein!");
			label.setTextFill(Color.RED);
		}
		else if(!benutzer.equals(b12)){
			label.setVisible(true);
			label.setText("Der Name darf nicht ge�ndert werden!");
			label.setTextFill(Color.RED);
		}
		else
		{
			benutzer = fieldBenutzer.getText().toString();
			passwort = fieldPasswort.getText().toString();
			String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
			int berechtigung =  (int) boxBerechtigung.getSelectionModel().getSelectedItem();
		
			if (passwort.equals("")) {
				Benutzer.updateBenutzer(benutzer, gruppe, berechtigung);
				initialize (null, null);
				label.setVisible(true);
				label.setText("Benutzer '"+benutzer+"' ge�ndert");
				label.setTextFill(Color.BLACK);
			}
			else
			{
				Benutzer.updateBenutzerPw(benutzer,passwort, gruppe, berechtigung);
				initialize (null, null);
				label.setVisible(true);
				label.setText("Benutzer '"+benutzer+"' und Passwort ge�ndert");
				label.setTextFill(Color.BLACK);
			}	
		}
	}
	
	/**
	 * Diese Methode regelt was passiert, wenn der Button 'l�schen' gedr�ckt wird.
	 * Wenn die TextFelder leer sind, so kann kein benutzer gel�sch werden.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleLoeschen() throws SQLException {
		
		String benutzer = fieldBenutzer.getText().toString();

		if(benutzer.equals(""))
		{
			label.setVisible(true);
			label.setText("Kein Benutzer ausgew�hlt");
			label.setTextFill(Color.RED);
		}
		else {
			Benutzer.deleteBenutzer(b12);
			label.setVisible(true);
			label.setText("Benutzer '"+benutzer+"' gel�scht");
			label.setTextFill(Color.BLACK);
			initialize (null, null);
		}
	}
	
	/**
	 * Diese Methode regelt was passiert, wenn der Button 'zur�cksetzen' gedr�ckt wird.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleClear() throws SQLException {

		initialize (null, null);
	}
	
	/**
	 * Diese Methode regelt was passiert, wenn der Button 'wiederherstellen' gedr�ckt wird.
	 * Wenn das Feld Benutzer, die ComboBox, die ComboBox Berechtigung leer sind, so werden benutzerdefinierte Fehler ausgeworfen.
	 * Ist dies nicht der Fall, so wird gepr�ft, ob das PasswortFeld leer ist.
	 * Ist dies leer, so wird der Benutzer reaktiviert.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleWiederherstellen() throws SQLException {
		String benutzer = fieldBenutzer.getText().toString();
		String passwort = fieldPasswort.getText();
		String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
		
		if (benutzer.equals("")) {
			label.setVisible(true);
			label.setText("Es ist kein Benutzer zum Wiederherstellen ausgew�hlt!");
			label.setTextFill(Color.RED);
		}
		else if(boxGruppe.getValue()==null){
			label.setVisible(true);
			label.setText("Name und Gruppe m�ssen ausgew�hlt sein!");
			label.setTextFill(Color.RED);
		}
		else if(boxBerechtigung.getValue() != null)
		{
			boxBerechtigung.setValue(null);
			label.setVisible(true);
			label.setText("Berechtigung kann beim Wiederherstellen nicht ge�ndert werden.");
			label.setTextFill(Color.RED);
		}
		else 
		{
//			if (fieldPasswort.getText()==null) 
			if(passwort.equals(""))
			{
				if(Benutzer.inaktiverBenutzer(benutzer)) 
				{
					Benutzer.updateInaktiverBenutzer(benutzer, gruppe);
					initialize (null, null);
					label.setVisible(true);
					label.setText("Benutzer '" + benutzer + "' wiederhergestellt");
					label.setTextFill(Color.BLACK);
					
				}
				else 
				{
					initialize (null, null);
					label.setVisible(true);
					label.setText("Benutzer '" + benutzer + "' kann nicht wiederhergestellt werden.");
					label.setTextFill(Color.RED);
					
				}	
			}
			else 
			{
				fieldPasswort.setText("");
				label.setVisible(true);
				label.setText("Passwort kann beim Wiederherstellen nicht ge�ndert werden.");
				label.setTextFill(Color.RED);
			}
		}
	}
}