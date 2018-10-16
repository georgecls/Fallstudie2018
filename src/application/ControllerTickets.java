package application;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	@FXML private TableView<Antrag> tvGruppentickets, tvEigeneTickets, tvTicketsPr�fen;
	@FXML private TableView<Antrag> tvTicketsGenehmigen, tvAlleTickets, tvAbgTickets;
	
	@FXML private Tab Gruppentickets, TicketsPr�fen, eigeneTickets;
	@FXML private Tab abgeschlosseneTickets, TicketsGenehmigen, AlleTickets;
		
	@FXML private TableColumn<Antrag, String> status_ColGr, status_ColP, status_ColGe, status_ColA, status_ColET, status_ColAT;
	@FXML private TableColumn<Antrag, String> auftragsID_ColGr,auftragsID_ColP, auftragsID_ColGe, auftragsID_ColA, auftragsID_ColET, auftragsID_ColAT;
	@FXML private TableColumn<Antrag, String> titel_ColGr, titel_ColP, titel_ColGe, titel_ColA, titel_ColET, titel_ColAT;
	@FXML private TableColumn<Antrag, String> datum_ColGr, datum_ColP, datum_ColGe, datum_ColA, datum_ColET, datum_ColAT;
	@FXML private TableColumn<Antrag, String> Kommentar_ColGr, Beschreibung_ColGr, Kommentar_ColP, Beschreibung_ColP, Kommentar_ColGe, Beschreibung_ColGe;

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
	
	@FXML private JFXButton btnBearbeiten, btnPr�fen, btnGenehmigen, btnAblehnen;
	
    private ObservableList<Antrag> data_gr, data_AbgT, data_pr�fen, data_genehmigen, data_AlleT, data_EigT;
    
    private String antragsID, name, komm, beschr;
    private Date datum;
	
	public Main main;
    public void setMain(Main main) {

        this.main = main;
    }
    
    @SuppressWarnings("restriction")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	
			try {
				data_EigT = Antrag.getEigeneAntraege(ControllerLogin.user);
				data_pr�fen = Antrag.getAntraegezuPruefen(ControllerLogin.user, Benutzer.benutzerid);
				data_gr = Antrag.getAntraegebyStatus("genehmigt",ControllerLogin.user); 
				data_genehmigen = Antrag.getAntraegebyStatus("gepr�ft",ControllerLogin.user);
	    		data_AbgT = Antrag.getAntraegebyStatus("abgeschlossen", ControllerLogin.user);
				data_AlleT = Antrag.getAlleAntraege();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		/**
		 * Kommentar einf�gen
		 */
    	data_pr�fen.forEach((antrag) -> {
    		Antrag a1 = (Antrag) antrag;
    	}); 
    	
		data_AbgT.forEach((antrag) -> {
    		Antrag a2 = (Antrag) antrag;
    	}); 
    	
    	data_EigT.forEach((antrag) -> {
    		Antrag a3 = (Antrag) antrag;
    	}); 
    	
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
    	 * Kommentar einf�gen
    	 */
		auftragsID_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));   
		Kommentar_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Kommentar"));
		Beschreibung_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Beschreibung"));

		auftragsID_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Kommentar_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Kommentar"));
		Beschreibung_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Beschreibung"));
		
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
		Kommentar_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Kommentar"));
		Beschreibung_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Beschreibung"));
		
    	status_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
		/**
		 * Kommentar einf�gen
		 */
		tvGruppentickets.setItems(data_gr);
		tvTicketsPr�fen.setItems(data_pr�fen);
		tvTicketsGenehmigen.setItems(data_genehmigen);
		tvAbgTickets.setItems(data_AbgT);
		tvEigeneTickets.setItems(data_EigT);
		tvAlleTickets.setItems(data_AlleT);

	}
    
    	@FXML
    	public void omcGruppentickets()
    	{
    		Antrag aID = tvGruppentickets.getSelectionModel().getSelectedItem();
    		antragsID = Integer.toString(aID.getAntragid());
    		tfGrId.setText(antragsID);
    		
    		Antrag n = tvGruppentickets.getSelectionModel().getSelectedItem();
    		name = n.getName();
    		tfGrTitel.setText(name);
    		
    		Antrag fst = tvGruppentickets.getSelectionModel().getSelectedItem();
    		datum = fst.getFertigstellungsdatum();
    		String pattern = "dd.MM.yyyy";
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    		String date = simpleDateFormat.format(datum);
    		tfGrFertigstellungsdatum.setText(date);
    		
    		Antrag kom = tvGruppentickets.getSelectionModel().getSelectedItem();
    		komm = kom.getKommentar();
    		taGrKommentar.setText(komm);
    		
    		Antrag beschreibung = tvGruppentickets.getSelectionModel().getSelectedItem();
    		beschr = beschreibung.getBeschreibung();
    		taGrBeschreibung.setText(beschr);
    		}
    	
    	@FXML
    	public void omcTicketsPruefen()
    	{
    		Antrag aID = tvTicketsPr�fen.getSelectionModel().getSelectedItem();
    		antragsID = Integer.toString(aID.getAntragid());
    		tfPrId.setText(antragsID);
    		
    		Antrag n = tvTicketsPr�fen.getSelectionModel().getSelectedItem();
    		name = n.getName();
    		tfPrTitel.setText(name);
    		
    		Antrag fst = tvTicketsPr�fen.getSelectionModel().getSelectedItem();
    		datum = fst.getFertigstellungsdatum();
    		String pattern = "dd.MM.yyyy";
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    		String date = simpleDateFormat.format(datum);
    		tfPrFertigstellungsdatum.setText(date);
    		
    		Antrag kom = tvTicketsPr�fen.getSelectionModel().getSelectedItem();
    		komm = kom.getKommentar();
    		taPrKommentar.setText(komm);
    		
    		Antrag beschreibung = tvTicketsPr�fen.getSelectionModel().getSelectedItem();
    		beschr = beschreibung.getBeschreibung();
    		taPrBeschreibung.setText(beschr);
    	}
    	
    	@FXML
    	public void omcTicketsGenehmigen()
    	{
    		Antrag aID = tvTicketsGenehmigen.getSelectionModel().getSelectedItem();
    		antragsID = Integer.toString(aID.getAntragid());
    		tfGeId.setText(antragsID);
    		
    		Antrag n = tvTicketsGenehmigen.getSelectionModel().getSelectedItem();
    		name = n.getName();
    		tfGeTitel.setText(name);
    		
    		Antrag fst = tvTicketsGenehmigen.getSelectionModel().getSelectedItem();
    		datum = fst.getFertigstellungsdatum();
    		String pattern = "dd.MM.yyyy";
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    		String date = simpleDateFormat.format(datum);
    		tfGeFertigstellungsdatum.setText(date);
    		
    		Antrag kom = tvTicketsGenehmigen.getSelectionModel().getSelectedItem();
    		komm = kom.getKommentar();
    		taGeKommentar.setText(komm);
    		
    		Antrag beschreibung = tvTicketsGenehmigen.getSelectionModel().getSelectedItem();
    		beschr = beschreibung.getBeschreibung();
    		taGeBeschreibung.setText(beschr);
    	}
    
    	@FXML
    	public void handleBtnBearbeiten()
    	{
    		
    	}
    	
    	@FXML
    	public void handleBtnPr�fen()
    	{
    			
    	}
    	
    	//Kommentar f�llen
    	@FXML
    	public void handleBtnGenehmigen()
    	{
    		    	    
    	}
    	
    	@FXML
    	public void handleBtnAblehnen()
    	{
    		
    	}
    
}
