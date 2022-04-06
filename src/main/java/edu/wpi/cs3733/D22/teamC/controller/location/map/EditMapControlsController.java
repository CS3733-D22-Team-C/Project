package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.fxml.FXML;

public class EditMapControlsController {
    BaseMapViewController parentController;

    @FXML
    public void onExitEditModeButtonPress() {
        parentController.swapToViewMode();
    }

    @FXML
    public void onSaveAndExitButtonPress() {
        ((EditMapController) parentController.getMapController()).saveMap();
        parentController.swapToViewMode();
    }

    @FXML
    public void onImportCSVButtonPress() {

    }

    @FXML
    public void onExportCSVButtonPress() {

    }

    public void setParentController(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;
    }
}
