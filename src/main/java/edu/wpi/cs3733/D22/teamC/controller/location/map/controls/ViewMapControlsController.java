package edu.wpi.cs3733.D22.teamC.controller.location.map.controls;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ViewMapControlsController extends MapControlsController {
    @FXML private MFXButton editButton;

    //#region Events
        @FXML
        void onEditButtonPressed(ActionEvent actionEvent) {
            parentController.swapToEditMode();
        }
    //#endregion
}
