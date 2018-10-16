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
import javafx.stage.Stage;

public class ControllerBenutzerverwaltung  implements Initializable {
	
	ObservableList<Integer> boxBerechtigungListe = FXCollections.observableArrayList(0,1,2);
	
	
	
	ObservableList<String> boxGruppeListe;
	
	@FXML private TableView<Benutzer> tvBenutzerverwaltung;
	
	@FXML private TableColumn<Benutzer, String> benutzer_Col, gruppe_Col, berechtigung_Col;
	
	public static String b12, g12;
	public static int a12;
	
	private ObservableList<Benutzer> data;
	
	@FXML private JFXButton btnHinzufuegen, btnAendern, btnLoeschen;
	@FXML private JFXTextField fieldBenutzer, fieldPasswort;
	@FXML private JFXComboBox boxGruppe, boxBerechtigung;
	@FXML private Label label;
	
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
		Benutzer b1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
		b12 = b1.getBenutzername();
								
		Benutzer g1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
		g12 = g1.getGruppe();
						
		Benutzer a1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
		a12 = a1.getBerechtigung();
					
		fieldBenutzer.setText(b12);
		boxGruppe.setValue(g12);
		boxBerechtigung.setValue(a12);		
	}
	
	@FXML
    public void handleHinzufuegen() throws SQLException {
              String benutzer = fieldBenutzer.getText().toString();
              String passwort = fieldPasswort.getText().toString();
              String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
              int berechtigung =  (int) boxBerechtigung.getSelectionModel().getSelectedItem();
              
              //ÜBERARBEITEN
//              Boolean selberName = false;
//              selberName = Benutzer.selberName(benutzer);
              
              Benutzer.insertBenutzer(benutzer, passwort, berechtigung, gruppe);
              initialize(null, null);
              
              
//              if(selberName==true) {
//                         label.setText("Der Benutzer existiert bereits!");
//              }else {
//              
//                         try {
//                                   Benutzer.insertBenutzer(benutzer, passwort, berechtigung, gruppe);
//                         } catch (SQLException e) {
//                                   // TODO Auto-generated catch block
//                                   e.printStackTrace();
//                         }                    
//                         initialize (null, null);
//              }
    }

	@FXML
	public void handleAendern() {
		String benutzer = fieldBenutzer.getText().toString();
		String passwort = fieldPasswort.getText().toString();
		String gruppe = (String) boxGruppe.getSelectionModel().getSelectedItem();
		int berechtigung =  (int) boxBerechtigung.getSelectionModel().getSelectedItem();
		try {
			if (passwort.equals("")) {
				Benutzer.updateBenutzer(benutzer, gruppe, berechtigung);
			}else{
				Benutzer.updateBenutzerPw(benutzer,passwort, gruppe, berechtigung);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initialize (null, null);
	}
	@FXML
	public void handleLoeschen() {
		try {
			Benutzer.deleteBenutzer(b12);
			initialize (null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
