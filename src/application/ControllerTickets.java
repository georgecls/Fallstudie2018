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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ControllerTickets implements Initializable{
	/**
	 * Deklarierung der Variablen und GUI Elemente 
	 */
	@FXML private TableView<Antrag> tvGruppentickets, tvEigeneTickets, tvTicketsPr�fen;
	@FXML private TableView<Antrag> tvTicketsGenehmigen, tvAlleTickets, tvAbgTickets;
	
	@FXML private Tab Gruppentickets, TicketsPr�fen, eigeneTickets;
	@FXML private Tab abgeschlosseneTickets, TicketsGenehmigen, AlleTickets;
		
	@FXML private TableColumn<Antrag, String> status_ColGr, status_ColP, status_ColGe, status_ColA, status_ColET, status_ColAT;
	@FXML private TableColumn<Antrag, String> auftragsID_ColGr,auftragsID_ColP, auftragsID_ColGe, auftragsID_ColA, auftragsID_ColET, auftragsID_ColAT;
	@FXML private TableColumn<Antrag, String> titel_ColGr, titel_ColP, titel_ColGe, titel_ColA, titel_ColET, titel_ColAT;
	@FXML private TableColumn<Antrag, String> datum_ColGr, datum_ColP, datum_ColGe, datum_ColA, datum_ColET, datum_ColAT;
	@FXML private TableColumn<Antrag, String> Kommentar_ColGr, Kommentar_ColP, Kommentar_ColGe, Kommentar_ColA, Kommentar_ColET, Kommentar_ColAT;
	@FXML private TableColumn<Antrag, String> Beschreibung_ColGr, Beschreibung_ColP, Beschreibung_ColGe, Beschreibung_ColET, Beschreibung_ColA, Beschreibung_ColAT;
	@FXML private TableColumn<Antrag, String> Edatum_ColGr, Edatum_ColP, Edatum_ColGe, Edatum_ColA, Edatum_ColET, Edatum_ColAT;
	@FXML private TableColumn<Antrag, String> gruppe_ColGr, gruppe_ColP, gruppe_ColGe, gruppe_ColA, gruppe_ColET, gruppe_ColAT;
	@FXML private TableColumn<Antrag, String> Ablehnung_ColGr, Ablehnung_ColP, Ablehnung_ColGe, Ablehnung_ColA, Ablehnung_ColET, Ablehnung_ColAT;

	@FXML private Label lblGrId, lblPrId, lblGeId; 
	@FXML private Label lblGrTitel, lblPrTitel, lblGeTitel;
	@FXML private Label lblGrBeschreibung, lblPrBeschreibung, lblGeBeschreibung;
	@FXML private Label lblGrKommentar, lblPrKommentar, lblGeKommentar;
	@FXML private Label lblGrFertigstellungsdatum, lblPrFertigstellungsdatum, lblGeFertigstellungsdatum;
	@FXML private Label labelGr, labelPr, labelGe;
	@FXML private Label labelET, labelAT, labelAbgT;
	
	@FXML private JFXTextField tfGrId,tfGrTitel,tfGrFertigstellungsdatum;
	@FXML private JFXTextField tfPrId,tfPrTitel,tfPrFertigstellungsdatum;
	@FXML private JFXTextField tfGeId,tfGeTitel,tfGeFertigstellungsdatum;

	@FXML private JFXTextArea taGrBeschreibung, taGrKommentar;
	@FXML private JFXTextArea taPrBeschreibung, taPrKommentar;
	@FXML private JFXTextArea taGeBeschreibung, taGeKommentar;
	
	@FXML private JFXButton btnBearbeiten, btnPr�fen, btnGenehmigen, btnAblehnen,
							btnETInfo, btnATInfo, btnAbgTInfo, btnL�schen;
	
    private ObservableList<Antrag> data_gr, data_AbgT, data_pr�fen, data_genehmigen, data_AlleT, data_EigT;
    private ObservableList<String> cbData;
    
    private String antragsID, name, komm, beschr, status, ablehnung, date, Edate;
    private Gruppe gruppe;
    private Date datum, Edatum;

    private static String aid, nameS, kommS, beschrS, statusS, ablehnungS, datumS, EdatumS;
    
	public Main main;
    public void setMain(Main main) {

        this.main = main;
    }
    
    @SuppressWarnings("restriction")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	initialisiereGUI(ControllerLogin.getBerechtigung());
    	
    	/**
    	 * Die ObservableList Elemente erhalten ihren Inhalt durch die statischen Methoden von der Klasse 'Antrag'.
    	 * Inhalt der Listen sind die jeweiligen Antr�ge.  
    	 */
		try {
				data_EigT = Antrag.getEigeneAntraege(ControllerLogin.getUserid());
				data_pr�fen = Antrag.getAntraegezuPruefen(ControllerLogin.getUser(), ControllerLogin.getUserid());
				data_gr = Antrag.getGruppenantraege("genehmigt",ControllerLogin.getUser()); 
				data_genehmigen = Antrag.getAntraegebyStatus("gepr�ft",ControllerLogin.getUser());
	    		data_AbgT = Antrag.getAntraegebyStatus("erledigt", ControllerLogin.getUser());
				data_AlleT = Antrag.getAlleAntraege();
				
		}
		catch (SQLException e) 
		{
				e.printStackTrace();
		}
		
		/**
		 * mit forEach() wird jedes Element der ObservableList durchgegangen und eine Instanz angelegt. (glaube ich^^)
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
		 * Die Daten der ObservableListe werden der jeweiligen Tabelle zugeordnet.
		 */
		tvGruppentickets.setItems(data_gr);
		tvTicketsPr�fen.setItems(data_pr�fen);
		tvTicketsGenehmigen.setItems(data_genehmigen);
		tvAbgTickets.setItems(data_AbgT);
		tvEigeneTickets.setItems(data_EigT);
		tvAlleTickets.setItems(data_AlleT);
    	
    	/**
    	 * durch die Variablen in den Klammern wird den Tabellenspalten der ben�tigte Wert zugewiesen.
    	 * So wird z.B. in Fertigstellungsdatum auch das Fertigstellungsdatum aus der ObservableList angezeigt.
    	 */
		auftragsID_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));   
		Kommentar_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("beschreibung"));

		auftragsID_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Kommentar_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("beschreibung"));
		
		status_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Edatum_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragsdatum"));
		Kommentar_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("beschreibung"));
		Ablehnung_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("ablehnungsgrund"));
//		gruppe_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("gruppe"));
		
		status_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Edatum_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragsdatum"));
		Kommentar_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("beschreibung"));
		Ablehnung_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("ablehnungsgrund"));
//		gruppe_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("gruppe"));
		
		auftragsID_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Kommentar_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("beschreibung"));
		
    	status_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Edatum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragsdatum"));
		Kommentar_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("beschreibung"));
		Ablehnung_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("ablehnungsgrund"));
//		gruppe_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("gruppe"));
    }
    
    	/**
    	 * 
    	 */
    	@FXML
    	public void omcGruppentickets()
    	{
    		try {
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
    		catch(NullPointerException npe){
				npe.printStackTrace();
    		}	
    	}
    	
    	/**
    	 * 
    	 */
    	@FXML
    	public void omcTicketsPruefen()
    	{
    		try {
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
    		
    			Antrag beschreibung = tvTicketsPr�fen.getSelectionModel().getSelectedItem();
    			beschr = beschreibung.getBeschreibung();
    			taPrBeschreibung.setText(beschr);
    		}
    		catch(NullPointerException npe){
				npe.printStackTrace();
    		}
    	}
    	
    	/**
    	 * 
    	 */
    	@FXML
    	public void omcTicketsGenehmigen()
    	{
    		try {
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
    		catch(NullPointerException npe){
				npe.printStackTrace();
    		}	
    	}
    
    	//Wen Zeile in Tabelle ausgew�hl, alle Infos zum Ticket speichern und in neues Fenster bei handleInfo �bergeben
    	// F�r Tabellen erledigte Tickets, eigene Tickets und Alle Tickets
    	/**
    	 * 
    	 */
    	@FXML
    	public void omcTicketInfoAT()
    	{
    		try {
    		Antrag aID = tvAlleTickets.getSelectionModel().getSelectedItem();
			antragsID = Integer.toString(aID.getAntragid());
		
			Antrag n = tvAlleTickets.getSelectionModel().getSelectedItem();
			name = n.getName();
			
//			Antrag gru = tvAlleTickets.getSelectionModel().getSelectedItem();
//			gruppe = gru.getGruppe();
			
			Antrag est = tvAlleTickets.getSelectionModel().getSelectedItem();
			Edatum = est.getAntragsdatum();
			String pattern = "dd.MM.yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Edate = simpleDateFormat.format(Edatum);
			
			Antrag fst = tvAlleTickets.getSelectionModel().getSelectedItem();
			datum = fst.getFertigstellungsdatum();
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
			date = simpleDateFormat1.format(datum);
		
			Antrag beschreibung = tvAlleTickets.getSelectionModel().getSelectedItem();
			beschr = beschreibung.getBeschreibung();
		
			Antrag kom = tvAlleTickets.getSelectionModel().getSelectedItem();
			komm = kom.getKommentar();
			
			Antrag abl = tvAlleTickets.getSelectionModel().getSelectedItem();
			ablehnung = abl.getAblehnungsgrund();
			
			Antrag stat = tvAlleTickets.getSelectionModel().getSelectedItem();
			status = stat.getStatus();
    		}
    		catch(NullPointerException npe){
				npe.printStackTrace();
    		}	
    	}
    	
    	@FXML
    	public void omcTicketInfoET()
    	{
    		try {
    		Antrag aID = tvEigeneTickets.getSelectionModel().getSelectedItem();
			antragsID = Integer.toString(aID.getAntragid());
		
			Antrag n = tvEigeneTickets.getSelectionModel().getSelectedItem();
			name = n.getName();
			
			Antrag est = tvEigeneTickets.getSelectionModel().getSelectedItem();
			Edatum = est.getAntragsdatum();
			String pattern = "dd.MM.yyyy";
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
			Edate = simpleDateFormat1.format(Edatum);
			
			Antrag fst = tvEigeneTickets.getSelectionModel().getSelectedItem();
			datum = fst.getFertigstellungsdatum();
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			date = simpleDateFormat.format(datum);
		
			Antrag beschreibung = tvEigeneTickets.getSelectionModel().getSelectedItem();
			beschr = beschreibung.getBeschreibung();
		
			Antrag kom = tvEigeneTickets.getSelectionModel().getSelectedItem();
			komm = kom.getKommentar();
			
			Antrag abl = tvEigeneTickets.getSelectionModel().getSelectedItem();
			ablehnung = abl.getAblehnungsgrund();
			
			Antrag stat = tvEigeneTickets.getSelectionModel().getSelectedItem();
			status = stat.getStatus();
    		}
    		catch(NullPointerException npe){
				npe.printStackTrace();
    		}	
    	}
    	
    	@FXML
    	public void omcTicketInfoAbgT()
    	{
    		try {
    		Antrag aID = tvAbgTickets.getSelectionModel().getSelectedItem();
			antragsID = Integer.toString(aID.getAntragid());
		
			Antrag n = tvAbgTickets.getSelectionModel().getSelectedItem();
			name = n.getName();
			
			Antrag est = tvAbgTickets.getSelectionModel().getSelectedItem();
			Edatum = est.getAntragsdatum();
			String pattern = "dd.MM.yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Edate = simpleDateFormat.format(Edatum);
			
			Antrag fst = tvAbgTickets.getSelectionModel().getSelectedItem();
			datum = fst.getFertigstellungsdatum();
			SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern);
			date = simpleDateFormat1.format(datum);
		
			Antrag beschreibung = tvAbgTickets.getSelectionModel().getSelectedItem();
			beschr = beschreibung.getBeschreibung();
		
			Antrag kom = tvAbgTickets.getSelectionModel().getSelectedItem();
			komm = kom.getKommentar();
			
			Antrag abl = tvAbgTickets.getSelectionModel().getSelectedItem();
			ablehnung = abl.getAblehnungsgrund();
			
			Antrag stat = tvAbgTickets.getSelectionModel().getSelectedItem();
			status = stat.getStatus();
    		}
    		catch(NullPointerException npe){
				npe.printStackTrace();
    		}	
    	}
    	
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button bearbeiten auf der GUI gedr�ckt wird.
    	 * @throws SQLException
    	 */
    	@FXML
    	public void handleBtnBearbeiten() throws SQLException
    	{
    		antragsID = tfGrId.getText();
        	if(antragsID == null) 
        	{
        		labelGr.setVisible(true);
        		labelGr.setText("Bitte Ticket ausw�hlen");
        	}
        	else
        	{
        		labelGr.setVisible(false);
	    		Antrag.antragBearbeiten(antragsID);//Methode sollte String bekommen
	    		initialize(null, null);
	    		
	    		labelGr.setVisible(true);
	    		labelGr.setText("Ticket bearbeitet");  	
        	}
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button pr�fen auf der GUI gedr�ckt wird.
    	 * 
    	 * @throws SQLException
    	 */
    	@FXML
    	public void handleBtnPr�fen() throws SQLException
    	{
    		antragsID = tfPrId.getText();
    		if(antragsID == null) 
    		{
    			labelPr.setVisible(true);
    			labelPr.setText("Bitte Ticket ausw�hlen");
    			
    		}
    		else
    		{
    			antragsID= tfPrId.getText();
	    		String kom = taPrKommentar.getText();
	    		Antrag.antragPruefen(antragsID, kom);
	    		initialize(null, null);
	    		
	    		labelPr.setVisible(true);
	    		labelPr.setText("Ticket gepr�ft");		
    		}
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button genehmigen auf der GUI gedr�ckt wird.
    	 * 
    	 * @throws SQLException 
    	 */
    	@FXML
    	public void handleBtnGenehmigen() throws SQLException
    	{
        	antragsID = tfGeId.getText();
        	if(antragsID == null) 
        	{
        		labelGe.setVisible(true);
        		labelGe.setText("Bitte Ticket ausw�hlen");
        	}
        	else
    		{
    			String kom = taGeKommentar.getText();
    			//Methode anpassen
    			Antrag.antragGenehmigen(antragsID, kom);
    			initialize(null, null);
    		}	
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button ablehnen auf der GUI gedr�ckt wird.
    	 * Neues Fenster 'Ablehnung.fxml' wird in neuem Fenster ge�ffnet.
    	 */
    	@FXML
    	public void handleBtnAblehnen()
    	{
    		antragsID = tfGeId.getText();
        	if(antragsID == null)
        	{
        		labelGe.setVisible(true);
        		labelGe.setText("Bitte Ticket ausw�hlen");
        	}
        	else
        	{
        		labelGe.setVisible(false);
        		labelGe.setText(null);
	    		aid = antragsID;
	    		try
	    		{
		    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ablehnung.fxml"));
		    		Parent root1 = (Parent) fxmlLoader.load();
		    		Stage stage = new Stage();
		    		stage.setScene(new Scene(root1));
		    		stage.show();

					ControllerAblehnung ca = fxmlLoader.getController();
					ca.setControllerTickets(this);
				}
	    		catch (IOException e)
	    		{
					e.printStackTrace();
	    		}
        	}
    	}
    	
    	public void handleBtnL�schen() throws SQLException
    	{
    		if(status.equals("erstellt"))
    		{
    			Antrag.deleteAntragById(antragsID);
    			initialize(null, null);
    			labelET.setVisible(true);
           		labelET.setText("Ticket gel�scht");
    		}
    		else
    		{
           		labelET.setVisible(true);
    			labelET.setText("Bitte geeignetes Ticket ausw�hlen");
    		}
    	}

    	//�ffnet neues Fenster Info.fxml
    	@FXML
    	public void handleBtnInfo() {
    		try
    		{
    			if(antragsID == null)
    			{
    				labelET.setVisible(true);
    				labelET.setText("Bitte Ticket ausw�hlen");
    			}
    			else 
    			{
    				aid = antragsID;
    				nameS = name;
    				statusS = status;
    				datumS = date;
    				EdatumS = Edate;
    				kommS = komm;
    				beschrS = beschr;
    				ablehnungS = ablehnung;
    				
    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Info.fxml"));
    				Parent root1 = (Parent) fxmlLoader.load();
    				Stage stage = new Stage();
    				stage.setScene(new Scene(root1));
    				stage.show();
    			}
			}
    		catch (IOException e)
    		{
				e.printStackTrace();
    		}
    	}
    	
		public void refresh(){
				initialize(null, null);
			}

    	/**
    	 * Initialisierung der GUI Elemente.
    	 */
    	public void initialisiereGUI(int berechtigung)
    	{
    		tfGrId.setText(null);
        	tfGrTitel.setText(null);
        	tfGrFertigstellungsdatum.setText(null);
        	taGrBeschreibung.setText(null);
        	taGrKommentar.setText(null);
        	
        	tfPrId.setText(null);
        	tfPrTitel.setText(null);
        	tfPrFertigstellungsdatum.setText(null);
        	taPrBeschreibung.setText(null);
        	taPrKommentar.setText(null);
        	
        	tfGeId.setText(null);	
        	tfGeTitel.setText(null);
        	tfGeFertigstellungsdatum.setText(null);
        	taGeBeschreibung.setText(null);
        	taGeKommentar.setText(null);
        	
        	/**
        	 * Label werden beim initialisieren in der GUI auf unsichtbar gesetzt.
        	 * Die Tabs werden auf verf�gbar gesetzt.
        	 */
        	labelGr.setVisible(false);
        	labelPr.setVisible(false);
        	labelGe.setVisible(false);
        	
        	Gruppentickets.setDisable(false);
    		TicketsPr�fen.setDisable(false);
    		eigeneTickets.setDisable(false);
        	abgeschlosseneTickets.setDisable(false);
    		TicketsGenehmigen.setDisable(false);
    		AlleTickets.setDisable(false);
    		
    		/**
    		 * Je nach Berechtigung kann der angemeldete Nutzer verschiedene Tabs anklicken oder auch nicht.
    		 */
    		if (berechtigung == 0) {
        		abgeschlosseneTickets.setDisable(true);
    			TicketsGenehmigen.setDisable(true);
    			AlleTickets.setDisable(true);
    		}
    		
    		else if (berechtigung == 1) {
    			TicketsPr�fen.setDisable(true);
    			AlleTickets.setDisable(true);
    		}
    		
    		else {
    			abgeschlosseneTickets.setDisable(false);
    			TicketsGenehmigen.setDisable(false);
    			AlleTickets.setDisable(false);
    		}
    	}
    	
    	/**
    	 * Getter Methode f�r Antrags-ID
    	 * @return aid
    	 */
    	public static String getAID()
    	{
    		return aid;
    	}
    	
		public Date getEdatum() {
			return Edatum;
		}

		public void setEdatum(Date edatum) {
			Edatum = edatum;
		}

		public Gruppe getGruppe() {
			return gruppe;
		}

		public void setGruppe(Gruppe gruppe) {
			this.gruppe = gruppe;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getAblehnung() {
			return ablehnung;
		}

		public void setAblehnung(String ablehnung) {
			this.ablehnung = ablehnung;
		}

		public static String getStatusS() {
			return statusS;
		}

		public static void setStatusS(String statusS) {
			ControllerTickets.statusS = statusS;
		}

		public static String getNameS() {
			return nameS;
		}

		public static void setNameS(String nameS) {
			ControllerTickets.nameS = nameS;
		}

		public static String getBeschrS() {
			return beschrS;
		}

		public static void setBeschrS(String beschrS) {
			ControllerTickets.beschrS = beschrS;
		}

		public static String getKommS() {
			return kommS;
		}

		public static void setKommS(String kommS) {
			ControllerTickets.kommS = kommS;
		}

		public static String getAblehnungS() {
			return ablehnungS;
		}

		public static void setAblehnungS(String ablehnungS) {
			ControllerTickets.ablehnungS = ablehnungS;
		}

		public static String getEdatumS() {
			return EdatumS;
		}

		public static void setEdatumS(String edatumS) {
			EdatumS = edatumS;
		}

		public static String getDatumS() {
			return datumS;
		}

		public static void setDatumS(String datumS) {
			ControllerTickets.datumS = datumS;
		}
}