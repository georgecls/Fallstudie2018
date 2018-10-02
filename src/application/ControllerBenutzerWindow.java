package application;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerBenutzerWindow {
	
	ObservableList<Integer> choiceBoxBerechtigungListe = FXCollections.observableArrayList(0,1,2);
	
	ObservableList<String> choiceBoxGruppeListe = FXCollections.observableArrayList("IT","Consulting (nicht benutzen)",
			"BobDerBaumeister (nicht benutzen)");
	
	@FXML private javafx.scene.control.Button cancelButton;
	
	//View
	@FXML public TextField fieldBenutzer;
	@FXML public TextField fieldPasswort;
	@FXML public ChoiceBox choiceBoxGruppe;
	@FXML public ChoiceBox choiceBoxBerechtigung;
	
	public void initialize() {
		choiceBoxGruppe.setValue("IT");
		choiceBoxGruppe.setItems(choiceBoxGruppeListe);
		choiceBoxBerechtigung.setValue(0);
		choiceBoxBerechtigung.setItems(choiceBoxBerechtigungListe);
		
	}
	
	
	
	public void handleSpeichern() {
		String benutzer = fieldBenutzer.getText().toString();
		String passwort = fieldPasswort.getText().toString();
		String gruppe = (String) choiceBoxGruppe.getSelectionModel().getSelectedItem();
		int berechtigung =  (int) choiceBoxBerechtigung.getSelectionModel().getSelectedItem();
		
		System.out.println(gruppe);
		
		try {
			Benutzer.insertBenutzer(benutzer, passwort, berechtigung, gruppe);
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
