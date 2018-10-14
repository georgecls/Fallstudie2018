package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerNeuBenutzerverwaltung  implements Initializable {
	
	@FXML private TableView<Benutzer> tvBenutzerverwaltung;
	
	@FXML private TableColumn<Benutzer, String> benutzer_Col;
	@FXML private TableColumn<Benutzer, String> gruppe_Col;
	@FXML private TableColumn<Benutzer, String> berechtigung_Col;
	
	public static String b12;
	public static String g12;
	public static int a12;
	
	private ObservableList<Benutzer> data;
	
	@FXML private JFXButton btnHinzufuegen;
	@FXML private JFXButton btnAendern;
	@FXML private JFXButton btnLoeschen;
	
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
				
//		select_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("select"));
		benutzer_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("benutzername"));
		gruppe_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("gruppe"));
		berechtigung_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("berechtigung"));
		
		tvBenutzerverwaltung.setItems(data);
		
	}
	
	@FXML
	public void onMouseClicked () {
		Benutzer b1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
//		System.out.println(b1.getBenutzername());
		b12 = b1.getBenutzername();
			
		Benutzer g1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
		g12 = g1.getGruppe();
		
		Benutzer a1 = tvBenutzerverwaltung.getSelectionModel().getSelectedItem();
		a12 = a1.getBerechtigung();
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
			Benutzer.deleteBenutzer(b12);
			initialize (null, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
