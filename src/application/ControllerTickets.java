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
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControllerTickets implements Initializable{
	
	@FXML private TableView<Antrag> tvGruppentickets, tvEigeneTickets, tvTicketsPrüfen;
	@FXML private TableView<Antrag> tvTicketsGenehmigen, tvAlleTickets, tvAbgTickets;
	
	@FXML private Tab Gruppentickets, TicketsPrüfen, eigeneTickets;
	@FXML private Tab abgeschlosseneTickets, TicketsGenehmigen, AlleTickets;
		
	@FXML private TableColumn<Antrag, String> status_ColGr, status_ColP, status_ColGe, status_ColA, status_ColET, status_ColAT;
	@FXML private TableColumn<Antrag, String> auftragsID_ColGr,auftragsID_ColP, auftragsID_ColGe, auftragsID_ColA, auftragsID_ColET, auftragsID_ColAT;
	@FXML private TableColumn<Antrag, String> titel_ColGr, titel_ColP, titel_ColGe, titel_ColA, titel_ColET, titel_ColAT;
	@FXML private TableColumn<Antrag, String> datum_ColGr, datum_ColP, datum_ColGe, datum_ColA, datum_ColET, datum_ColAT;

	@FXML private Label lblGrId, lblPrId, lblGeId; 
	@FXML private Label lblGrTitel, lblPrTitel, lblGeTitel;
	@FXML private Label lblGrBeschreibung, lblPrBeschreibung, lblGeBeschreibung;
	@FXML private Label lblGrKommentar, lblPrKommentar, lblGeKommentar;
	@FXML private Label lblGrFertigstellungsdatum, lblPrFertigstellungsdatum, lblGeFertigstellungsdatum;
	
	@FXML private JFXTextField tfGrId,tfGrTitel,tfGrFertigstellungsdatum;
	@FXML private JFXTextField tfPrId,tfPrTitel,tfPrFertigstellungsdatum;
	@FXML private JFXTextField tfGeId,tfGeTitel,tfGeFertigstellungsdatum;

	@FXML private JFXTextArea taGrBeschreibung, taGrKommentar;
	@FXML private JFXTextArea taPrBeschreibung, taPrKommentar;
	@FXML private JFXTextArea taGeBeschreibung, taGeKommentar;
	
	@FXML private JFXButton btnBearbeiten, btnPrüfen, btnGenehmigen, btnAblehnen;
//	@FXML private Button btnBearbeiten, btnPrüfen, btnGenehmigen;
	
    private ObservableList<Antrag> data_gr, data_AbgT, data_prüfen, data_genehmigen, data_AlleT, data_EigT;
	
	public Main main;
    public void setMain(Main main) {

        this.main = main;
    }
    
    @SuppressWarnings("restriction")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	
			try {
//				data_EigT = Antrag.getEigeneAntraege(ControllerLogin.user);
				data_prüfen = Antrag.getAntraegezuPruefen(ControllerLogin.user);
				data_gr = Antrag.getAntraegebyStatus("genehmigt",ControllerLogin.user); 
				data_genehmigen = Antrag.getAntraegebyStatus("geprüft",ControllerLogin.user);
	    		data_AbgT = Antrag.getAntraegebyStatus("abgeschlossen", ControllerLogin.user);
				data_AlleT = Antrag.getAlleAntraege();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		/**
		 * Kommentar einfügen
		 */
    	data_prüfen.forEach((antrag) -> {
    		Antrag a1 = (Antrag) antrag;
    	}); 
    	
		data_AbgT.forEach((antrag) -> {
    		Antrag a2 = (Antrag) antrag;
    	}); 
    	
//    	data_EigT.forEach((antrag) -> {
//    		Antrag a3 = (Antrag) antrag;
//    	}); 
//    	
    	data_gr.forEach((antrag) -> {
    		Antrag a4 = (Antrag) antrag;
    	});    	
    	
    	data_AlleT.forEach((antrag) -> { 
        	Antrag a5 = (Antrag) antrag;
    	});
    	
    	data_genehmigen.forEach((antrag) -> {
    		Antrag a6 = (Antrag) antrag;
    	}); 
	
    	/**
    	 * Kommentar einfügen
    	 */
		auftragsID_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));    	
    	
		auftragsID_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
		status_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
		status_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
		auftragsID_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
    	
    	status_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
		/**
		 * Kommentar einfügen
		 */
		tvGruppentickets.setItems(data_gr);
		tvTicketsPrüfen.setItems(data_prüfen);
		tvTicketsGenehmigen.setItems(data_genehmigen);
		tvAbgTickets.setItems(data_AbgT);
//		tvEigeneTickets.setItems(data_EigT);
		tvAlleTickets.setItems(data_AlleT);

	}
    
    	@FXML
    	public void handleBtnBearbeiten()
    	{
    		
    	}
    	
    	@FXML
    	public void handleBtnPrüfen()
    	{
    		try {
        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicketsWindow.fxml"));
        		Parent root1 = (Parent) fxmlLoader.load();
        		Stage stage = new Stage();
        		stage.setScene(new Scene(root1));
        		stage.show();
        		
    			} 
        		catch (IOException e) {
    				e.printStackTrace();
    			}    	
    	}
    	
    	//Kommentar füllen
    	@FXML
    	public void handleBtnGenehmigen()
    	{
    		try {
        		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TicketsWindow.fxml"));
        		Parent root1 = (Parent) fxmlLoader.load();
        		Stage stage = new Stage();
        		stage.setScene(new Scene(root1));
        		stage.show();
        		
    			} 
        		catch (IOException e) {
    				e.printStackTrace();
    			}    	    
    	}
    	
    	@FXML
    	public void handleBtnAblehnen()
    	{
    		
    	}
    
}
