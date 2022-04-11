package edu.wpi.cs3733.D22.teamC.controller.location.map.controls;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class MapControlsController implements Initializable {
    // FXML
    @FXML
    JFXComboBox<Floor> floorComboBox;

    // References
    protected BaseMapViewController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Location Info
        ComponentWrapper.InitializeComboBox(floorComboBox, Floor::getShortName);
    }

    public void setup(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;

        // Set Floor Values
        floorComboBox.getItems().setAll(parentController.getAllFloors());
        floorComboBox.setValue(parentController.getCurrentFloor());
    }

    //#region FXML Events
        @FXML
        void onFloorChanged(ActionEvent event) {
            parentController.setCurrentFloor(floorComboBox.getValue());
            parentController.setCurrentLocation(null);
        }
    //#endregion
}
