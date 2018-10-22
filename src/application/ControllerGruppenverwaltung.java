package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControllerGruppenverwaltung  implements Initializable {
	
	/**
	 * Deklarierung der GUI-Elemente.
	 */
	@FXML private TableView<Gruppe> tvGruppenverwaltung;
	@FXML private TableView<Benutzer> tvBenutzer;
	
	@FXML private TableColumn<Benutzer, String> benutzer_Col;
	@FXML private TableColumn<Gruppe, String> gruppe_Col, beschreibung_Col;
	@FXML private TableColumn<Gruppe, String> id_Col;

	@FXML private JFXButton btnHinzufuegen, btnAendern, btnLoeschen;
	@FXML private JFXTextField fieldGruppe;
	@FXML private JFXTextArea fieldBeschreibung;
	@FXML private Label label;
	
	/**
	 * Deklarierung der Variablen.
	 */
	private static String b12, g12, a12, id12;
	private ObservableList<Gruppe> gruppe;
	private ObservableList<Benutzer> benutzer;
	
	/**
	 * Diese Methode ist für die Initialisierung des Fensters 'Gruppenverwaltung' zuständig.
	 * Zuerst werden die angelegten Gruppen in einer Liste gespeichert.
	 * Diese Elemente der Liste werden in der Tabelle angezeigt.
	 * Die Text Felder Gruppe und Beschreibung werden 'null' gesetzt.
	 * 
	 * @param url, rb
	 * @return none
	 */
	@Override
	public void initialize (URL url, ResourceBundle rb){
		
		try {
			gruppe = Gruppe.getGruppenverwaltung();
			label.setVisible(false);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		gruppe.forEach((gruppe) -> {
			Gruppe b1 = (Gruppe) gruppe;
		});
		
		id_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("id"));		
		gruppe_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("gruppenname"));
		beschreibung_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("gruppenbeschr"));
		
		tvGruppenverwaltung.setItems(gruppe);
		
		fieldGruppe.setText("");
		fieldBeschreibung.setText("");
	}
	
	/**
	 * Diese Methode regelt was passiert, wenn eine Zeile in der Tabelle ausgewählt wird.
	 * Die Textfelder werden mit den Daten der Tabelle gefüllt.
	 * Gleichzeitig füllt sich die 2. Tabelle mit den zugehörigen Mitgliedern der ausgewählten Gruppe.
	 * 
	 * @param none
	 * @return none
	 */
	@FXML
	public void onMouseClicked () {
		try {
			Gruppe id1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
			id12 = id1.getId();
		
			Gruppe g1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
			g12 = g1.getGruppenname();
		
			Gruppe a1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
			a12 = a1.getGruppenbeschr();
		
			fieldGruppe.setText(g12);
			fieldBeschreibung.setText(a12);
			label.setVisible(false);
		}
		catch(NullPointerException npe)
		{
			npe.printStackTrace();
		}
		
		try {
			benutzer = Benutzer.getBenutzerByGruppe(id12);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
				
		benutzer.forEach((benutzer) -> {
			Benutzer b1 = (Benutzer) benutzer;
		});
				
		benutzer_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("benutzername"));
		tvBenutzer.setItems(benutzer);
	}
	
	/**
	 * Diese Methode beschreibt was passiert, wenn der Button 'hinzufügen' gedrückt wird.
	 * Die Daten aus den Text Feldern werden in Variablen gespeichert.
	 * Wenn die Gruppe schon einmal aktiv war, so wird sie reaktiviert. Wenn es sie noch nicht gab,
	 * so wird eine neue Gruppe angelegt.
	 * Sind nicht alle Felder ausgefüllt, oder es wird der Name einer aktiven Gruppe eingetragen, so
	 * wird die Gruppe nicht angelegt und es folgt eine Meldung.
	 * 
	 * @param
	 * @return
	 * @throws SQLException
	 */
	@FXML
	public void handleHinzufuegen() throws SQLException{
		String gruppe = fieldGruppe.getText();
		String beschreibung = fieldBeschreibung.getText();
		
        if (gruppe.equals("")) {
			label.setVisible(true);
			label.setText("Alle Felder müssen ausgefüllt sein!");
			label.setTextFill(Color.RED);
		}
        else if (beschreibung.equals("")){
			label.setVisible(true);
			label.setText("Alle Felder müssen ausgefüllt sein!");
			label.setTextFill(Color.RED);
		}
		else
		{
			gruppe = fieldGruppe.getText().toString();
			beschreibung = fieldBeschreibung.getText().toString();
			
			if(Gruppe.inaktiveGruppe(gruppe)) 
			{
		        if (beschreibung.equals(""))
		        {
					label.setVisible(true);
					label.setText("Alle Felder müssen ausgefüllt sein!");
					label.setTextFill(Color.RED);
				}
				else
				{
					Gruppe.updateGruppeByName(gruppe, beschreibung);;
					initialize (null, null);
					label.setVisible(true);
					label.setText("Gruppe '"+gruppe+"' hinzugefügt");
					label.setTextFill(Color.BLACK);
				}
			}
			else if(Gruppe.selberName(gruppe)) {
				label.setVisible(true);
				label.setText("Die Gruppe existiert bereits!");
				label.setTextFill(Color.RED);
			}
			else 
			{
				Gruppe.insertGruppe(gruppe, beschreibung);
				initialize (null, null);
				label.setVisible(true);
				label.setText("Gruppe '"+gruppe+"' hinzugefügt");	
				label.setTextFill(Color.BLACK);
			}
		}
	}
	
	/**
	 * Diese Methode beschreibt was passiert, wenn der Button 'ändern' gedrückt wird.
	 * Die Daten aus den Text Feldern werden in Variablen gespeichert und in der DB wird
	 * ein update gemacht.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleAendern() throws SQLException{
		String gruppe = fieldGruppe.getText();
		String beschreibung = fieldBeschreibung.getText();
	
		if (gruppe.equals("")) {
			label.setVisible(true);
			label.setText("Bitte Gruppe auswählen");
			label.setTextFill(Color.RED);
		}
        else if (beschreibung.equals("")){
			label.setVisible(true);
			label.setText("Bitte Gruppe auswählen");
			label.setTextFill(Color.RED);
		}
        else if (Gruppe.selberName(gruppe)){
        	label.setVisible(true);
        	label.setText("Der Gruppenname existiert bereits!");
			label.setTextFill(Color.RED);
		}
		else {
			gruppe = fieldGruppe.getText().toString();
			beschreibung = fieldBeschreibung.getText().toString();
			Gruppe.updateGruppeById(id12, gruppe, beschreibung);
			initialize(null, null);
			label.setVisible(true);
			label.setText("Gruppe '"+gruppe+"' geändert");	
			label.setTextFill(Color.BLACK);
		}
	}
	
	/**
	 * Diese Methode beschreibt was passiert, wenn der Button 'löschen' gedrückt wird.
	 * Die Daten aus den Text Feldern werden in Variablen gespeichert und in der DB wird
	 * ein update gemacht (Gruppe wird auf inaktiv gesetzt).
	 * Sind noch Mitarbeiter der Gruppe zugeordnet, so kann die Gruppe nicht gelöscht werden.
	 * 
	 * @param none
	 * @return none
	 * @throws SQLException
	 */
	@FXML
	public void handleLoeschen() throws SQLException{
		String gruppe = fieldGruppe.getText().toString();
		
		if(Benutzer.pruefeBenutzer(id12)) {
			label.setVisible(true);
			label.setText("Es befinden sich noch Benutzer in der Gruppe.");
			label.setTextFill(Color.BLACK);
		}
		else
		{
			Gruppe.deleteGruppe(id12);
			initialize (null, null);
			label.setVisible(true);
			label.setText("Gruppe '"+gruppe+"' gelöscht");
			label.setTextFill(Color.BLACK);
		}
	}
}