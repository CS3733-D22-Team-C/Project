package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.fxml.FXML;

public class EditMapLocationsPaneController {
    BaseMapViewController baseMapViewController;


    @FXML
    public void onExitEditModeButtonPress() {
        baseMapViewController.swapToViewMode();
    }

    @FXML
    public void onSaveAndExitButtonPress() {
        baseMapViewController.saveMap();
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
