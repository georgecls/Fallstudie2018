package application;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class ControllerAblehnung implements Initializable{

	@FXML private JFXButton btnAbschicken;
	@FXML private JFXTextArea taAblehnung;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void handleAbschicken()
	{
		//hier Methode - update Antrag - Ablehnungsgrund
	}

}
