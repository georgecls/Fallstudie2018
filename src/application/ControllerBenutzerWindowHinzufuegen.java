package application;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerBenutzerWindowHinzufuegen {
	
	ObservableList<Integer> choiceBoxBerechtigungListe = FXCollections.observableArrayList(0,1,2);
	
	ObservableList<String> choiceBoxGruppeListe = FXCollections.observableArrayList
			("Controlling","Finanzen","Geschäftsführung","Hausverwaltung","IT","Kundenberatung",
					"Marketingabteilung","Materialwirtschaft","Personalabteilung","Rechnungswesen",
					"Rechtsabteilung","Vertrieb");
	
	@FXML private javafx.scene.control.Button cancelButton;
	@FXML private javafx.scene.control.Button speichern;
	
	//View
	@FXML public TextField fieldBenutzer;
	@FXML public TextField fieldPasswort;
	@FXML public ChoiceBox choiceBoxGruppe;
	@FXML public ChoiceBox choiceBoxBerechtigung;
	
	public void initialize() {
		choiceBoxGruppe.setValue("Controlling");
		choiceBoxGruppe.setItems(choiceBoxGruppeListe);
		choiceBoxBerechtigung.setValue(0);
		choiceBoxBerechtigung.setItems(choiceBoxBerechtigungListe);
		
	}
	
	
	
	public void handleSpeichern() {
		String benutzer = fieldBenutzer.getText().toString();
		String passwort = fieldPasswort.getText().toString();
		String gruppe = (String) choiceBoxGruppe.getSelectionModel().getSelectedItem();
		int berechtigung =  (int) choiceBoxBerechtigung.getSelectionModel().getSelectedItem();
		
		try {
			Benutzer.insertBenutzer(benutzer, passwort, berechtigung, gruppe);
			Stage stage = (Stage) speichern.getScene().getWindow();
			stage.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	public void handleCancel() {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
    	stage.close();
    }

}
