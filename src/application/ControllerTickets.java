package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    
    @SuppressWarnings("restriction")
	@Override
    public void initialize(URL url, ResourceBundle rb) {
    	
			try {
				data_EigT = Antrag.getAntraegeByBenutzer(ControllerLogin.user);
//				data_prüfen = Antrag.getAntraegebyStatus("erstellt",ControllerLogin.user);//noch die falsche Methode, da der ersteller seine eigenen Tickets nicht prüfen darf
				data_gr = Antrag.getAntraegebyStatus("genehmigt",ControllerLogin.user); 
//				data_genehmigen = Antrag.getAntraegebyStatus("geprüft",ControllerLogin.user);
//	    		data_AbgT = Antrag.getAntraegebyStatus("abgeschlossen", ControllerLogin.user);
				data_AlleT = Antrag.getAntraege();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
    
    	
//    	data_prüfen.forEach((antrag) -> {
//    		Antrag a1 = (Antrag) antrag;
//    		System.out.println(a1.getName());
//    	}); 
//    	
//			data_AbgT.forEach((antrag) -> {
//    		Antrag a2 = (Antrag) antrag;
//    		System.out.println(a2.getName());
//    	}); 
//    	
    	data_EigT.forEach((antrag) -> {
    		Antrag a3 = (Antrag) antrag;
    		System.out.println(a3.getName());
    	}); 
    	
    	data_gr.forEach((antrag) -> {
    		Antrag a4 = (Antrag) antrag;
    		System.out.println(a4.getName());
    	});    	
    	
    	data_AlleT.forEach((antrag) -> { 
        	Antrag a5 = (Antrag) antrag;
    	    System.out.println(a5.getName());
    	});
    	
//    	data_genehmigen.forEach((antrag) -> {
//    		Antrag a6 = (Antrag) antrag;
//    		System.out.println(a6.getName());
//    	}); 
//	
    	
//    	select_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGr.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));    	
    	
//		select_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColP.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
//		select_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColET.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
//		select_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColA.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
//		select_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColGe.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
    	
//    	select_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("select"));
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("antragid"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("fertigstellungsdatum"));
		
		
		tvGruppentickets.setItems(data_gr);
//		tvTicketsPrüfen.setItems(data_prüfen);
//		tvTicketsGenehmigen.setItems(data_genehmigen);
//		tvAbgTickets.setItems(data_AbgT);
		tvEigeneTickets.setItems(data_EigT);
		tvAlleTickets.setItems(data_AlleT);

	}
    
    	//neue FXML - abwandlung von "TicketsWindow.fxml"
    	public void handleBtnBearbeiten()
    	{
    		
    	}
    	
    	//Button umbenennen in GUI - FXML anpassen
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
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}    	
    	}
    	
    	//Button wieder umbenennen + Kommentar füllen - neue FXML - abwandlung von "TicketsWindow.fxml"
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
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}    	    
    	}
    
}
