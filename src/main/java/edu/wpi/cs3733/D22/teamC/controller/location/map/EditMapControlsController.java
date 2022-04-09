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
        parentController.saveLocationChanges();
        parentController.swapToViewMode();
    }

    public void setParentController(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;
    }
}
