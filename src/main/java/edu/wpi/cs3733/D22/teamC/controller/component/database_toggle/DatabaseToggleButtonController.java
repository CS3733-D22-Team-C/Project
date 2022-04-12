package edu.wpi.cs3733.D22.teamC.controller.component.database_toggle;

import edu.wpi.cs3733.D22.teamC.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DatabaseToggleButtonController implements Initializable {

    @FXML
    Label toggleLabel;

    @FXML
    public void toggleDragged(ActionEvent event)
    {
        if(toggleLabel.getText().equals("Switch to Server"))
        {
            toggleLabel.setText("Switch to Embedded");
            toggleLabel.setAlignment(Pos.CENTER);
            SessionManager.switchDatabase(SessionManager.DBMode.EMBEDDED);
        }
        else if(toggleLabel.getText().equals("Switch to Embedded"))
        {
            toggleLabel.setText("Switch to Server");
            toggleLabel.setAlignment(Pos.CENTER);
            SessionManager.switchDatabase(SessionManager.DBMode.SERVER);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleLabel.setVisible(true);
        toggleLabel.setText("Switch to Server");
        toggleLabel.setAlignment(Pos.CENTER);
    }
}
