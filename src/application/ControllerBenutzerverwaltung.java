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
	
	private ObservableList<Integer> boxBerechtigungListe = FXCollections.observableArrayList(0,1,2);
	private ObservableList<String> boxGruppeListe;
	private ObservableList<Benutzer> data;
	
	@FXML private TableView<Benutzer> tvBenutzerverwaltung;
	@FXML private TableColumn<Benutzer, String> benutzer_Col, gruppe_Col, berechtigung_Col;
	@FXML private JFXButton btnHinzufuegen, btnAendern, btnLoeschen;
	@FXML private JFXTextField fieldBenutzer, fieldPasswort;
	@FXML private JFXComboBox boxGruppe, boxBerechtigung;
	@FXML private Label label;
	
	public static String b12, g12;
	public static int a12;
	
	@Override
	public void initialize (URL url, ResourceBundle rb){	
		
		try {
			data = Benutzer.getBenutzerverwaltung();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data.forEach((benutzer) -> {
			Benutzer b1 = (Benutzer) benutzer;

		});
		
//		Übergabe aller aktiven Gruppen
		try {
			boxGruppeListe = Gruppe.getGruppennamen();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
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
        label.setText(null);
		
	}
	
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
	
	@FXML
    public void handleHinzufuegen() throws SQLException {
		
        String benutzer = fieldBenutzer.getText();
        String passwort = fieldPasswort.getText();
        
		if(boxGruppe.getValue()==null){
			label.setVisible(true);
			label.setText("Alle Felder müssen ausgefüllt sein!");
			label.setTextFill(Color.RED);
		}
		else if(boxBerechtigung.getValue() == null){
			label.setVisible(true);
			label.setText("Alle Felder müssen ausgefüllt sein!");
			label.setTextFill(Color.RED);
		}
        else if (benutzer.equals("")) {
			label.setVisible(true);
			label.setText("Alle Felder müssen ausgefüllt sein!");
			label.setTextFill(Color.RED);
		}
        else if (passwort.equals("")){
			label.setVisible(true);
			label.setText("Alle Felder müssen ausgefüllt sein!");
			label.setTextFill(Color.RED);
		}
		else
		{
			benutzer = fieldBenutzer.getText().toString();
			passwort = fieldPasswort.getText().toString();
		    String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
		    int berechtigung =  (int) boxBerechtigung.getSelectionModel().getSelectedItem();

            Boolean selberName = Benutzer.selberName(benutzer);
            Boolean inaktiverBenutzer = Benutzer.inaktiverBenutzer(benutzer);
              
            if(inaktiverBenutzer == true) 
            {
            	if(boxGruppe.getValue()==null){
        			label.setVisible(true);
        			label.setText("Alle Felder müssen ausgefüllt sein!");
        			label.setTextFill(Color.RED);
        		}
        		else if(boxBerechtigung.getValue() == null){
        			label.setVisible(true);
        			label.setText("Alle Felder müssen ausgefüllt sein!");
        			label.setTextFill(Color.RED);
        		}
                else if (passwort.equals("")){
        			label.setVisible(true);
        			label.setText("Alle Felder müssen ausgefüllt sein!");
        			label.setTextFill(Color.RED);
        		}
        		else
        		{
	            	Benutzer.updateInaktiverBenutzerPw(benutzer, passwort, gruppe, berechtigung);
	                initialize (null, null);
	                label.setVisible(true);
	                label.setText("Benutzer '"+benutzer+"' hinzugefügt");
        		}
            }
            else if (selberName == true)
            {
            	label.setText("Der Benutzer existiert bereits!");
            }
            else
            {
            	Benutzer.insertBenutzer(benutzer, passwort, berechtigung, gruppe);                   
                initialize (null, null);
                label.setVisible(true);
                label.setText("Benutzer '"+benutzer+"' hinzugefügt");
            }
		}
    }

	@FXML
	public void handleAendern() throws SQLException{
		String benutzer = fieldBenutzer.getText().toString();
		String passwort = fieldPasswort.getText().toString();
		String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
		int berechtigung =  (int) boxBerechtigung.getSelectionModel().getSelectedItem();
		
			if (passwort.equals("")) {
				Benutzer.updateBenutzer(benutzer, gruppe, berechtigung);
			}
			else
			{
				Benutzer.updateBenutzerPw(benutzer,passwort, gruppe, berechtigung);
			}	
		
		initialize (null, null);
	}
	@FXML
	public void handleLoeschen() throws SQLException {

		Benutzer.deleteBenutzer(b12);
		initialize (null, null);
		
	}
	@FXML
	public void handleClear() throws SQLException {

		initialize (null, null);
		
	}
}
