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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerGruppenverwaltung  implements Initializable {
	
	@FXML private TableView<Gruppe> tvGruppenverwaltung;
	@FXML private TableView<Benutzer> tvBenutzer;
	
	@FXML private TableColumn<Benutzer, String> benutzer_Col;
	@FXML private TableColumn<Gruppe, String> gruppe_Col, beschreibung_Col;
	@FXML private TableColumn<Gruppe, Integer> id_Col;

	
	private static String b12, g12, a12;
	private static int id12;
	
	
	private ObservableList<Gruppe> gruppe;
	private ObservableList<Benutzer> benutzer;

	@FXML private JFXButton btnHinzufuegen, btnAendern, btnLoeschen;
	@FXML private JFXTextField fieldGruppe;
	@FXML private JFXTextArea fieldBeschreibung;

	@Override
	public void initialize (URL url, ResourceBundle rb){
		
		
		try {
			gruppe = Gruppe.getGruppenverwaltung();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		gruppe.forEach((gruppe) -> {
			Gruppe b1 = (Gruppe) gruppe;
		});
		
		id_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, Integer>("agid"));		
		gruppe_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("gruppenname"));
		beschreibung_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("gruppenbeschr"));
		
		tvGruppenverwaltung.setItems(gruppe);
		
	}
	
	@FXML
	public void onMouseClicked () {
		Gruppe id1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
		id12 = id1.getId();
		
		Gruppe g1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
		g12 = g1.getGruppenname();
		
		Gruppe a1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
		a12 = a1.getGruppenbeschr();
		
		fieldGruppe.setText(g12);
		fieldBeschreibung.setText(a12);
		
		
//		Nachdem eine auf eine Gruppe geklickt wird, soll die 2. Tabelle mit den zugehörigen Benutzern befüllt werden...
		
		try {
			benutzer = Benutzer.getBenutzerByGruppe(id12);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		benutzer.forEach((benutzer) -> {
			Benutzer b1 = (Benutzer) benutzer;
		});
				
		benutzer_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("benutzername"));
		
		tvBenutzer.setItems(benutzer);
	}
	
	@FXML
	public void handleHinzufuegen() {
		String gruppe = fieldGruppe.getText().toString();
		String beschreibung = fieldBeschreibung.getText().toString();
		
		try {
			Gruppe.insertGruppe(gruppe, beschreibung);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		initialize (null, null);
		
	}
	@FXML
	public void handleAendern() {
		
	}
	@FXML
	public void handleLoeschen() {
		try {
			Gruppe.deleteGruppe(id12);
			initialize (null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
