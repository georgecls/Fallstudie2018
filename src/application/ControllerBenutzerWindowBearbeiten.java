package application;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerBenutzerWindowBearbeiten {
	
	@FXML private javafx.scene.control.Button cancelButton;
	
	
	
	
	
	
	
	public void handleCancel() {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
    	stage.close();
    }

}
