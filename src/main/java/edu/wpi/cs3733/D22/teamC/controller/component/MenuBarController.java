package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;

public class MenuBarController {
    @FXML
    protected void onMenuBarHomeButtonPress() {
        App.instance.setView(App.HOME_PATH);
    }

    @FXML
    protected void onMenuBarLocationsButtonPress() {
        App.instance.setView(App.VIEW_LOCATIONS_PATH);
    }

    @FXML
    protected void onMenuBarServiceRequestsButtonPress() {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    protected void onMenuBarExitButtonPress() {
        // Get the stage window and close it
        App.instance.getStage().close();
    }
}
