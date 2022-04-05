package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.fxml.FXML;

public class ViewMapControlsController {
    // References
    private BaseMapViewController parentController;

    @FXML
    public void onEnterEditModeButtonPress() {
        // TODO: Check if the user is an admin, think we would want to do this before rendering the scene with an initialize
        parentController.swapToEditMode();
        parentController.getLocationInfoController().setEditable(true);
    }

    public void setParentController(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;
    }
}
