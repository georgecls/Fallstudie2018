package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerTickets implements Initializable{
	
	@FXML private TableView<Antrag> tvGruppentickets;
	@FXML private TableView<Antrag> tvTicketsPrüfen;
	@FXML private TableView<Antrag> tvTicketsGenehmigen;
	@FXML private TableView<Antrag> tvAlleTickets;
	@FXML private TableView<Antrag> tvAbgTickets;
	@FXML private TableView<Antrag> tvEigeneTickets;
	
	@FXML private Tab Gruppentickets;
	@FXML private Tab TicketsPrüfen;
	@FXML private Tab eigeneTickets;
	@FXML private Tab abgeschlosseneTickets;
	@FXML private Tab TicketsGenehmigen;
	@FXML private Tab AlleTickets;
	
	@FXML private TableColumn<Antrag, String> select_ColGr, select_ColP, select_ColGe, select_ColA, select_ColET, select_ColAT;
	@FXML private TableColumn<Antrag, String> auftragsID_ColGr,auftragsID_ColP, auftragsID_ColGe, auftragsID_ColA, auftragsID_ColET, auftragsID_ColAT;
	@FXML private TableColumn<Antrag, String> titel_ColGr, titel_ColP, titel_ColGe, titel_ColA, titel_ColET, titel_ColAT;
	@FXML private TableColumn<Antrag, String> datum_ColGr, datum_ColP, datum_ColGe, datum_ColA, datum_ColET, datum_ColAT;

	@FXML private Button btnBearbeiten, btnPrüfen, btnGenehmigen;
	
    private ObservableList data_gr, data_AbgT, data_prüfen, data_genehmigen, data_AlleT, data_EigT;
	
	public Main main;
    public void setMain(Main main) {

        this.main = main;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	//Daten kommen aus BackEnd - Klasse Antrag
    	//    
    	String benutzername = ControllerLogin.Benutzer;

    	try {
			data_EigT = Antrag.getAntraegeByErsteller(benutzername);
//	    	data_gr = Antrag.;
//  	  	data_prüfen = Antrag.;
//	    	data_AbgT = Antrag.;
//	 	   	data_genehmigen = Antrag.;
			data_AlleT = Antrag.getAntraege();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
		//Verknüpfen der Daten mit den Spalten
		//In die Klammer immer den Variablennamen der Auftrag-Klasse!!		
    	
    	select_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		select_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		select_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		select_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		select_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		select_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));

		auftragsID_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("auftragsID"));	
		auftragsID_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("auftragsID"));	
		auftragsID_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("auftragsID"));	
		auftragsID_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("auftragsID"));
		auftragsID_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("auftragsID"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("auftragsID"));
		
		titel_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("titel"));
		titel_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("titel"));
		titel_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("titel"));
		titel_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("titel"));
		titel_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("titel"));
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("titel"));

		datum_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));
		datum_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));
		datum_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));
		datum_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));
		datum_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));

		//Daten zu Tabelle hinzufügen - per ObservableList
		tvGruppentickets.setItems(data_gr);
		tvTicketsPrüfen.setItems(data_prüfen);
		tvTicketsGenehmigen.setItems(data_genehmigen);
		tvAlleTickets.setItems(data_AlleT);
		tvEigeneTickets.setItems(data_EigT);
		tvAbgTickets.setItems(data_AbgT);
	}
    
    	
    	public void handleBtnBearbeiten()
    	{
    		//neue Scene öffnen mit Bearbeiten-Fenster
    	}
    	
    	public void handleBtnPrüfen()
    	{
    		//neue Scene öffnen mit Prüfen-Fenster
    	}
    	
    	public void handleBtnGenehmigen()
    	{
    		//neue Scene öffnen mit Genehmigen-Fenster
    	}
    
}
