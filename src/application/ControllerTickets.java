package application;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
	@FXML private TableColumn<Antrag, String> Kommentar_ColGr, Beschreibung_ColGr, Kommentar_ColP, Beschreibung_ColP, Kommentar_ColGe, Beschreibung_ColGe;

	@FXML private Label lblGrId, lblPrId, lblGeId; 
	@FXML private Label lblGrTitel, lblPrTitel, lblGeTitel;
	@FXML private Label lblGrBeschreibung, lblPrBeschreibung, lblGeBeschreibung;
	@FXML private Label lblGrKommentar, lblPrKommentar, lblGeKommentar;
	@FXML private Label lblGrFertigstellungsdatum, lblPrFertigstellungsdatum, lblGeFertigstellungsdatum;
	@FXML private Label labelGr, labelPr, labelGe;
	
	@FXML private JFXTextField tfGrId,tfGrTitel,tfGrFertigstellungsdatum;
	@FXML private JFXTextField tfPrId,tfPrTitel,tfPrFertigstellungsdatum;
	@FXML private JFXTextField tfGeId,tfGeTitel,tfGeFertigstellungsdatum;

	@FXML private JFXTextArea taGrBeschreibung, taGrKommentar;
	@FXML private JFXTextArea taPrBeschreibung, taPrKommentar;
	@FXML private JFXTextArea taGeBeschreibung, taGeKommentar;
	
	@FXML private JFXComboBox cbGruppeZuweisen;	
	@FXML private JFXButton btnBearbeiten, btnPrüfen, btnGenehmigen, btnAblehnen;
	

    private ObservableList<Antrag> data_gr, data_AbgT, data_prüfen, data_genehmigen, data_AlleT, data_EigT;
    private ObservableList<String> cbData;
    
    private String antragsID, name, komm, beschr;
    private static String aid;
    private Date datum;
	
	public Main main;
    public void setMain(Main main) {

        this.main = main;
    }
    
    @SuppressWarnings("restriction")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	initialisiereGUI(ControllerLogin.getBerechtigung());

    	/**
    	 * 
    	 */
    	try {
			cbData = Gruppe.getGruppennamen();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	cbGruppeZuweisen.setItems(cbData);
    	
    	/**
    	 * Die ObservableList Elemente erhalten ihren Inhalt durch die statischen Methoden von der Klasse 'Antrag'.
    	 * Inhalt der Listen sind die jeweiligen Anträge.  
    	 */
			try {
				data_EigT = Antrag.getEigeneAntraege(ControllerLogin.getUser());
				data_prüfen = Antrag.getAntraegezuPruefen(ControllerLogin.getUser(), ControllerLogin.getUserid());
				data_gr = Antrag.getGruppenantraege("genehmigt",ControllerLogin.getUser()); 
				data_genehmigen = Antrag.getAntraegebyStatus("geprüft",ControllerLogin.getUser());
	    		data_AbgT = Antrag.getAntraegebyStatus("erledigt", ControllerLogin.getUser());
				data_AlleT = Antrag.getAlleAntraege();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		/**
		 * mit forEach() wird jedes Element der ObservableList durchgegangen und eine Instanz angelegt. (glaube ich^^)
		 */
    	data_prüfen.forEach((antrag) -> {
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
		tvTicketsPrüfen.setItems(data_prüfen);
		tvTicketsGenehmigen.setItems(data_genehmigen);
		tvAbgTickets.setItems(data_AbgT);
		tvEigeneTickets.setItems(data_EigT);
		tvAlleTickets.setItems(data_AlleT);
    	
    	/**
    	 * durch die Variablen in den Klammern wird den Tabellenspalten der benötigte Wert zugewiesen.
    	 * So wird z.B. in Fertigstellungsdatum auch das Fertigstellungsdatum aus der ObservableList angezeigt.
    	 */
		auftragsID_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));   
		Kommentar_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Beschreibung"));

		auftragsID_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		Kommentar_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
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
		Kommentar_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("kommentar"));
		Beschreibung_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("Beschreibung"));
		
    	status_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("status"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
	}
    
    	/**
    	 * 
    	 */
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
    	
    	/**
    	 * 
    	 */
    	@FXML
    	public void omcTicketsPruefen()
    	{
    		Antrag aID = tvTicketsPrüfen.getSelectionModel().getSelectedItem();
    		antragsID = Integer.toString(aID.getAntragid());
    		tfPrId.setText(antragsID);
    		
    		Antrag n = tvTicketsPrüfen.getSelectionModel().getSelectedItem();
    		name = n.getName();
    		tfPrTitel.setText(name);
    		
    		Antrag fst = tvTicketsPrüfen.getSelectionModel().getSelectedItem();
    		datum = fst.getFertigstellungsdatum();
    		String pattern = "dd.MM.yyyy";
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    		String date = simpleDateFormat.format(datum);
    		tfPrFertigstellungsdatum.setText(date);
    		
    		Antrag beschreibung = tvTicketsPrüfen.getSelectionModel().getSelectedItem();
    		beschr = beschreibung.getBeschreibung();
    		taPrBeschreibung.setText(beschr);
    	}
    	
    	/**
    	 * 
    	 */
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
    	public void omcImage()
    	{
    		
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button bearbeiten auf der GUI gedrückt wird.
    	 * @throws SQLException
    	 */
    	@FXML
    	public void handleBtnBearbeiten() throws SQLException
    	{
    		Antrag.antragBearbeiten(antragsID);//Methode sollte String bekommen
    		initialize(null, null);
    		
    		labelGr.setVisible(true);
    		labelGr.setText("Ticket bearbeitet");  		
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button prüfen auf der GUI gedrückt wird.
    	 * 
    	 * @throws SQLException
    	 */
    	@FXML
    	public void handleBtnPrüfen() throws SQLException
    	{
    		String kom = taPrKommentar.getText();
    		Antrag.antragPruefen(antragsID, kom);
    		initialize(null, null);
    		
    		labelPr.setVisible(true);
    		labelPr.setText("Ticket geprüft");
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button genehmigen auf der GUI gedrückt wird.
    	 * 
    	 * @throws SQLException 
    	 */
    	@FXML
    	public void handleBtnGenehmigen() throws SQLException
    	{
    		if(cbGruppeZuweisen.getValue() == null)
    		{
    			labelGe.setVisible(true);
    			labelGe.setText("Bitte Gruppe auswählen");
    			labelGe.setTextFill(Color.RED);
    		}
    		else
    		{
    			String kom = taGeKommentar.getText();
    			String gru = cbGruppeZuweisen.getValue().toString();
    			Antrag.antragGenehmigen(antragsID, kom, gru);
    			initialize(null, null);
    		}
    		
    		
    	}
    	
    	/**
    	 * Methode definiert, was passiert, wenn der Button ablehnen auf der GUI gedrückt wird.
    	 * Neues Fenster 'Ablehnung.fxml' wird in neuem Fenster geöffnet.
    	 */
    	@FXML
    	public void handleBtnAblehnen()
    	{
    		aid = antragsID;
    		try 
    		{
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Ablehnung.fxml"));
    		Parent root1 = (Parent) fxmlLoader.load();
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root1));
    		stage.show();
    		
			} 
    		catch (IOException e) 
    		{
				// TODO Auto-generated catch block
				e.printStackTrace();
    		}
    	}   

    	/**
    	 * Initialisierung der TextFelder usw.
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
        	cbGruppeZuweisen.setValue(null);
        	
        	/**
        	 * Label werden beim initialisieren in der GUI auf unsichtbar gesetzt
        	 */
        	labelGr.setVisible(false);
        	labelPr.setVisible(false);
        	labelGe.setVisible(false);
        	
        	Gruppentickets.setDisable(false);
    		TicketsPrüfen.setDisable(false);
    		eigeneTickets.setDisable(false);
        	abgeschlosseneTickets.setDisable(false);
    		TicketsGenehmigen.setDisable(false);
    		AlleTickets.setDisable(false);
    		
    		if (berechtigung == 0) {
        		abgeschlosseneTickets.setDisable(true);
    			TicketsGenehmigen.setDisable(true);
    			AlleTickets.setDisable(true);
        		
    		}else {
    			abgeschlosseneTickets.setDisable(false);
    			TicketsGenehmigen.setDisable(false);
    			AlleTickets.setDisable(false);
    		}
    	}
    	
    	
    	/**
    	 * Getter Methode für Antrags-ID
    	 * @return aid
    	 */
    	public static String getAID()
    	{
    		return aid;
    	}
}