package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerBenutzerWindowBearbeiten  implements Initializable{
	
	ObservableList<Integer> choiceBoxBerechtigungListe = FXCollections.observableArrayList(0,1,2);
	
	ObservableList<String> choiceBoxGruppeListe = FXCollections.observableArrayList
			("Controlling","Finanzen","Geschäftsführung","Hausverwaltung","IT","Kundenberatung",
					"Marketingabteilung","Materialwirtschaft","Personalabteilung","Rechnungswesen",
					"Rechtsabteilung","Vertrieb");
	
	@FXML private javafx.scene.control.Button cancelButton;
	@FXML private javafx.scene.control.Button ändern;
	
	@FXML private TextField fieldBenutzer;
	@FXML private TextField fieldPasswort;
	@FXML private ChoiceBox choiceBoxGruppe;
	@FXML private ChoiceBox choiceBoxBerechtigung;
	
	/*
	tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
	    @Override
	    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
	        //Check whether item is selected and set value of selected item to Label
	        if(tableview.getSelectionModel().getSelectedItem() != null) 
	        {    
	           TableViewSelectionModel selectionModel = tableview.getSelectionModel();
	           ObservableList selectedCells = selectionModel.getSelectedCells();
	           TablePosition tablePosition = (TablePosition) selectedCells.get(0);
	           Object val = tablePosition.getTableColumn().getCellData(newValue);
	           System.out.println("Selected Value" + val);
	         }
	         }
	     });
	*/
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		fieldBenutzer.setText(ControllerBenutzerverwaltung.b12);
//		fieldPasswort.setText(ControllerBenutzerverwaltung.p12);
		choiceBoxGruppe.setValue(ControllerBenutzerverwaltung.g12);
		choiceBoxBerechtigung.setValue(ControllerBenutzerverwaltung.a12);
		
		choiceBoxGruppe.setItems(choiceBoxGruppeListe);
		choiceBoxBerechtigung.setItems(choiceBoxBerechtigungListe);
	}
	
	
	public void handleÄndern() {
		
		String benutzer = fieldBenutzer.getText().toString();
		String passwort = fieldPasswort.getText().toString();
		String gruppe = (String) choiceBoxGruppe.getSelectionModel().getSelectedItem();
		int berechtigung =  (int) choiceBoxBerechtigung.getSelectionModel().getSelectedItem();
//		System.out.println(benutzer+passwort+gruppe+berechtigung);
		try {
			if (passwort.equals("")) 
			{
				Benutzer.updateBenutzer(benutzer, gruppe, berechtigung);
				Stage stage = (Stage) ändern.getScene().getWindow();
				stage.close();
			}else
			{
				Benutzer.updateBenutzerPw(benutzer,passwort, gruppe, berechtigung);
				Stage stage = (Stage) ändern.getScene().getWindow();
				stage.close();
			}
			
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
