package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
	
	@FXML private TableView<Gruppe> tvGruppenverwaltung, tvBenutzer;
	
	@FXML private TableColumn<Benutzer, String> benutzer_Col;
	@FXML private TableColumn<Gruppe, String> gruppe_Col, beschreibung_Col;
	
	private static String b12, g12, a12;
	
	
	private ObservableList<Gruppe> gruppe, benutzer;

	@FXML private Button btnHinzufuegen, btnAendern, btnLoeschen;
	
	@FXML private TextField fieldGruppe;
	@FXML private TextArea fieldBeschreibung;

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
				
		gruppe_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("gruppenname"));
		beschreibung_Col.setCellValueFactory(new PropertyValueFactory<Gruppe, String>("gruppenbeschr"));
		
		tvGruppenverwaltung.setItems(gruppe);
		
	}
	
	@FXML
	public void onMouseClicked () {
//		Benutzer b1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
////		System.out.println(b1.getBenutzername());
//		b12 = b1.getBenutzername();
			
		Gruppe g1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
		g12 = g1.getGruppenname();
		
		Gruppe a1 = tvGruppenverwaltung.getSelectionModel().getSelectedItem();
		a12 = a1.getGruppenbeschr();
		
		
//		Nachdem eine auf eine Gruppe geklickt wird, soll die 2. Tabelle mit den zugehörigen Benutzern befüllt werden...
		
//		try {
//			benutzer = Benutzer.getGruppenbenutzer();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//				
//		benutzer.forEach((benutzer) -> {
//			benutzer b1 = (Benutzer) benutzer;
//		});
//				
////		benutzer_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("benutzername"));
//		
//		tvBenutzer.setItems(benutzer);
	}
	
	@FXML
	public void handleHinzufuegen() {
		
	}
	@FXML
	public void handleAendern() {
		
	}
	@FXML
	public void handleLoeschen() {
		try {
			Gruppe.deleteGruppe(g12);
			initialize (null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
