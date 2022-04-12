package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;

public class MenuBarController {
    @FXML
    protected void onMenuBarHomeButtonPress() {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    protected void onMenuBarLocationsButtonPress() {
        App.instance.setSkeletonView("view/table/base_view.fxml", "view/table/locations/table_insert.fxml");
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

    @FXML
    protected void onMenuBarMapButtonPress() {
        App.instance.setView(App.MAP_PATH);
    }
}
