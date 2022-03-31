package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;

public class MenuBarController {
    @FXML
    protected void onMenuBarHomeButtonPress() {
        // TODO: Make it so that we can use constants here
        App.instance.setView("view/general/HomePage.fxml");
    }

    @FXML
    protected void onMenuBarLocationsButtonPress() {
        // TODO: Add Locations Page path
        App.instance.setView("");
    }

    @FXML
    protected void onMenuBarServiceRequestsButtonPress() {
        App.instance.setView("view/general/view-service.fxml");
    }

    @FXML
    protected void onMenuBarExitButtonPress() {
        // Get the stage window and close it
        App.instance.getStage().close();
    }
}
