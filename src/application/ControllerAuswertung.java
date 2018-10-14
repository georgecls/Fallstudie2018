package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class ControllerAuswertung implements Initializable {
	
	@FXML private Label fieldTGesamt;
	@FXML private Label fieldTFreigegeben;
	@FXML private Label fieldTGenehmigt;
	@FXML private PieChart pieChart;
	
	private ObservableList<Antrag> dataChart;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
			
		try {
			fieldTGesamt.setText(Antrag.countAntraege());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		dataChart = FXCollections.observableArrayList(
//				new Antrag("erledigt", )
//				
//				);
	}

}
