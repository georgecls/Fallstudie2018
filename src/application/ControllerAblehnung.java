package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class ControllerAblehnung implements Initializable{

	@FXML private JFXButton btnAbschicken;
	@FXML private JFXTextArea taAblehnung;
	@FXML private JFXTextField tfAntragid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tfAntragid.setText(ControllerTickets.getAID());
	}
	
	public void handleAbschicken() throws SQLException
	{
		Antrag.antragAblehnen(tfAntragid.getText(), taAblehnung.getText());
		Stage stage = (Stage) btnAbschicken.getScene().getWindow();
		stage.close();
	}

}
