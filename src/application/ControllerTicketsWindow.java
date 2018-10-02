package application;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControllerTicketsWindow {
	@FXML private javafx.scene.control.Button cancelButton;
	
	public Main main;

    public void setMain(Main main) {

        this.main = main;
    }
    
    @FXML
    public void handleSpeichern() {
    	
    }
    
    @FXML
    public void handleCancel() {
    	Stage stage = (Stage) cancelButton.getScene().getWindow();
    	stage.close();
    }

}
