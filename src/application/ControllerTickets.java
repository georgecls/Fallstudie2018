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
	
    private ObservableList<Antrag> data_gr, data_AbgT, data_prüfen, data_genehmigen, data_AlleT, data_EigT;
	
	public Main main;
    public void setMain(Main main) {

        this.main = main;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	    
//    	String benutzername = ControllerLogin.user;
    	data_AlleT = Antrag.getAntraege();

    	data_AlleT.forEach((antrag) -> { 

        	Antrag a1 = (Antrag) antrag;
    	    System.out.println(a1.getName());
    	});
	
//    	select_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));

		tvAlleTickets.setItems(data_AlleT);

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
