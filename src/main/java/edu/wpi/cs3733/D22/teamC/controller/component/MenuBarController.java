package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;

public class MenuBarController {
    @FXML
    protected void onMenuBarHomeButtonPress() {

    }

    @FXML
    protected void onMenuBarLocationsButtonPress() {

    }

    @FXML
    protected void onMenuBarServiceRequestsButtonPress() {

    }

    @FXML
    protected void onMenuBarExitButtonPress() {
        App.instance.getStage().close();
    }
}
