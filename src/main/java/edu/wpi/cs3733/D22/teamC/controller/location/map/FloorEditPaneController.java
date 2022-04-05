package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;

public class FloorEditPaneController {

    BaseMapViewController baseMapViewController;

    @FXML
    public void onEnterEditModeButtonPress() {
        // TODO: Check if the user is an admin, think we would want to do this before rendering the scene with an initialize
        baseMapViewController.swapToEditMode();
        baseMapViewController.getLocationInfoController().setEditable(true);
    }

    public void setBaseMapViewController(BaseMapViewController baseMapViewController) {
        this.baseMapViewController = baseMapViewController;
    }
}
