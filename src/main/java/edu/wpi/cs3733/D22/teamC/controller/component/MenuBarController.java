package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;

public class MenuBarController {
    @FXML
    protected void onMenuBarHomeButtonPress() {
        // TODO: Add home page path
        App.instance.setView("");
    }

    @FXML
    protected void onMenuBarLocationsButtonPress() {
        // TODO: Add Locations Page path
        App.instance.setView("");
    }

    @FXML
    protected void onMenuBarServiceRequestsButtonPress() {
        // TODO: Add Service Requests Page path
        App.instance.setView("");
    }

    @FXML
    protected void onMenuBarExitButtonPress() {
        // Get the stage window and close it
        App.instance.getStage().close();
    }
}
