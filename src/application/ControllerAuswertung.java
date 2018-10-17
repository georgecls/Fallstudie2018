package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class ControllerAuswertung implements Initializable {
	

	@FXML private PieChart pieChartGes, pieChartGr;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub		
		loadAuswertungGr();
		loadAuswertungGes();
	}
	
	public void loadAuswertungGes()
	{
		ObservableList<PieChart.Data> dataChartGes = null;
		try {
			dataChartGes = FXCollections.observableArrayList(
					 	new PieChart.Data("erledigt", Antrag.countAntraegeByStatus("erledigt")),
						new PieChart.Data("offen", Antrag.countAntraegeByStatus("genehmigt"))
					 );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pieChartGes.setData(dataChartGes);
        pieChartGes.setTitle("Auswertung Gesamt");
 
	}
	
	
	public void loadAuswertungGr()
	{
		 ObservableList<PieChart.Data> dataChartGr = null;
			try {
				dataChartGr = FXCollections.observableArrayList(
						 	new PieChart.Data("erledigt", Antrag.countAntraegeByGruppe(ControllerLogin.getUser(), "erledigt")),
							new PieChart.Data("offen", Antrag.countAntraegeByGruppe(ControllerLogin.getUser() ,"genehmigt"))
						 );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        pieChartGr.setData(dataChartGr);
	        pieChartGr.setTitle("Auswertung Gruppe");
	        
	}
}