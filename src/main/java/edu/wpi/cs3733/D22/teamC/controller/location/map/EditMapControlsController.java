package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.fxml.FXML;

public class EditMapControlsController {
    BaseMapViewController baseMapViewController;

    @FXML
    public void onExitEditModeButtonPress() {
        baseMapViewController.swapToViewMode();
    }

    @FXML
    public void onSaveAndExitButtonPress() {
        ((EditMapController) baseMapViewController.getMapController()).saveMap();
        baseMapViewController.swapToViewMode();
    }

    @FXML
    public void onImportCSVButtonPress() {

    }

    @FXML
    public void onExportCSVButtonPress() {

    }

    public void setBaseMapViewController(BaseMapViewController baseMapViewController) {
        this.baseMapViewController = baseMapViewController;
    }
}
