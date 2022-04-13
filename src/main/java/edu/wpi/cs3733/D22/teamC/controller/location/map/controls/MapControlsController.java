package edu.wpi.cs3733.D22.teamC.controller.location.map.controls;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class MapControlsController implements Initializable {
    // FXML
    @FXML JFXComboBox<Floor> floorComboBox;
    @FXML private MFXToggleButton medicalEquipmentToggle;

    // References
    protected BaseMapViewController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Location Info
        ComponentWrapper.initializeComboBox(floorComboBox, Floor::getShortName);

        medicalEquipmentToggle.setOnAction(e -> parentController.showMedicalEquipment(medicalEquipmentToggle.isSelected()));
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
            parentController.changeCurrentFloor(floorComboBox.getValue());
            parentController.changeCurrentLocation(null);
        }
    //#endregion
}
