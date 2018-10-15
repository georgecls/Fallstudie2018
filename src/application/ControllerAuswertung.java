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
	

	@FXML private PieChart pieChart, pieChart2;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub		
		
		ObservableList<PieChart.Data> dataChart = null;
		try {
			dataChart = FXCollections.observableArrayList(
					 	new PieChart.Data("abgeschlossen", Antrag.countAntraegeByStatus("abgelehnt")),
						new PieChart.Data("abgelehnt", Antrag.countAntraegeByStatus("abgeschlossen")),
						new PieChart.Data("offen", Antrag.countAntraegeOffen())
					 );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pieChart.setData(dataChart);
        pieChart.setTitle("Alle Tickets");
        pieChart2.setTitle("Benutzer Tickets");
        
        pieChart.setLabelLineLength(15);
        pieChart.setLegendSide(Side.RIGHT);
        pieChart2.setLabelLineLength(15);
        pieChart2.setLegendSide(Side.RIGHT);

			
	}
}