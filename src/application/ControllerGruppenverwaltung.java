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
	
	@FXML private TableView<Gruppe> tvGruppenverwaltung;
	@FXML private TableView<Benutzer> tvBenutzer;
	
	@FXML private TableColumn<Benutzer, String> benutzer_Col;
	@FXML private TableColumn<Gruppe, String> gruppe_Col, beschreibung_Col;
	@FXML private TableColumn<Gruppe, String> id_Col;

	private static String b12, g12, a12;
	private static String id12;	
	
	private ObservableList<Gruppe> gruppe;
	private ObservableList<Benutzer> benutzer;

	@FXML private JFXButton btnHinzufuegen, btnAendern, btnLoeschen;
	@FXML private JFXTextField fieldGruppe;
	@FXML private JFXTextArea fieldBeschreibung;
	@FXML private Label label;

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
		label.setVisible(false);
		
//		Nachdem eine auf eine Gruppe geklickt wird, soll die 2. Tabelle mit den zugehörigen Benutzern befüllt werden...
		
		try {
			benutzer = Benutzer.getBenutzerByGruppe(id12);
		}
		catch (SQLException e)
		{
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
			Boolean selberName = false;
			selberName = Gruppe.selberName(gruppe);
			
			if(selberName==true) {
				label.setText("Die Gruppe existiert bereits!");
			}
			else 
			{
				Gruppe.insertGruppe(gruppe, beschreibung);
				initialize (null, null);
				label.setVisible(true);
				label.setText("Gruppe '"+gruppe+"' hinzugefügt");	
			}
		}
	}
	
	@FXML
	public void handleAendern() throws SQLException{
		String gruppe = fieldGruppe.getText().toString();
		String beschreibung = fieldBeschreibung.getText().toString();
	

		Gruppe.updateGruppeById(id12, gruppe, beschreibung);
		initialize(null, null);
		label.setVisible(true);
		label.setText("Gruppe '"+gruppe+"' geändert");	
	}
	
	@FXML
	public void handleLoeschen() throws SQLException{
		if(Benutzer.pruefeBenutzer(id12)) {
			label.setVisible(true);
			label.setText("Es befinden sich noch Benutzer in der Gruppe.");
		}
		else
		{
			Gruppe.deleteGruppe(id12);
			initialize (null, null);
			label.setVisible(true);
			label.setText("Gruppe '"+gruppe+"' gelöscht");
		}
	}
}