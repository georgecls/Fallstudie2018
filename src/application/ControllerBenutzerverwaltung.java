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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerBenutzerverwaltung  implements Initializable {
	
	@FXML private TableView<Benutzer> tvBenutzerverwaltung;
	
	@FXML private TableColumn<Benutzer, String> select_Col;
	@FXML private TableColumn<Benutzer, String> benutzer_Col;
	@FXML private TableColumn<Benutzer, String> passwort_Col;
	@FXML private TableColumn<Benutzer, String> gruppe_Col;
	@FXML private TableColumn<Benutzer, String> berechtigung_Col;
	
	private ObservableList<Benutzer> data;
	
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
			System.out.println(b1.getBenutzername());
		});
				
//		select_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("select"));
		benutzer_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("benutzername"));
		passwort_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("passwort"));
		gruppe_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("gruppe"));
		berechtigung_Col.setCellValueFactory(new PropertyValueFactory<Benutzer, String>("berechtigung"));
		
		tvBenutzerverwaltung.setItems(data);
		
	}
	
	
	
	public void handleHinzufügen() {
		try {
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BenutzerWindow.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root1));
    		stage.show();
    		
			} 
    		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

}
