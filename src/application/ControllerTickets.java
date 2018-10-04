package application;


import java.io.IOException;
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
	@FXML private TableView<Antrag> tvTicketsPr�fen;
	@FXML private TableView<Antrag> tvTicketsGenehmigen;
	@FXML private TableView<Antrag> tvAlleTickets;
	@FXML private TableView<Antrag> tvAbgTickets;
	@FXML private TableView<Antrag> tvEigeneTickets;
	
	@FXML private Tab Gruppentickets;
	@FXML private Tab TicketsPr�fen;
	@FXML private Tab eigeneTickets;
	@FXML private Tab abgeschlosseneTickets;
	@FXML private Tab TicketsGenehmigen;
	@FXML private Tab AlleTickets;
	
	@FXML private TableColumn<Antrag, String> select_ColGr, select_ColP, select_ColGe, select_ColA, select_ColET, select_ColAT;
	@FXML private TableColumn<Antrag, String> auftragsID_ColGr,auftragsID_ColP, auftragsID_ColGe, auftragsID_ColA, auftragsID_ColET, auftragsID_ColAT;
	@FXML private TableColumn<Antrag, String> titel_ColGr, titel_ColP, titel_ColGe, titel_ColA, titel_ColET, titel_ColAT;
	@FXML private TableColumn<Antrag, String> datum_ColGr, datum_ColP, datum_ColGe, datum_ColA, datum_ColET, datum_ColAT;

	@FXML private Button btnBearbeiten, btnPr�fen, btnGenehmigen;
	
    private ObservableList<Antrag> data_gr, data_AbgT, data_pr�fen, data_genehmigen, data_AlleT, data_EigT;
	
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
		auftragsID_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("idantrag"));	
		titel_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("name"));
		datum_ColAT.setCellValueFactory(new PropertyValueFactory<Antrag, String>("datum"));

		tvAlleTickets.setItems(data_AlleT);

	}
    
    	//neue FXML - abwandlung von "TicketsWindow.fxml"
    	public void handleBtnBearbeiten()
    	{
    		
    	}
    	
    	//Button umbenennen in GUI - FXML anpassen
    	public void handleBtnPr�fen()
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
    	
    	//Button wieder umbenennen + Kommentar f�llen - neue FXML - abwandlung von "TicketsWindow.fxml"
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
