package edu.wpi.cs3733.D22.teamC.controller.map.panel;

import edu.wpi.cs3733.D22.teamC.controller.map.FloorMapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MapControlsController implements Initializable {
    // FXML
    @FXML private Button editModeButton;
    @FXML private Button viewModeButton;
    @FXML ComboBox<Floor> floorComboBox;
    @FXML private Button saveButton;
    @FXML private Button exitButton;
//    @FXML private MFXToggleButton medicalEquipmentToggle;

    // References
    private FloorMapViewController mapViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Combo Box
        ComponentWrapper.initializeComboBox(floorComboBox, Floor::getShortName);
    }

    public void setup(FloorMapViewController mapViewController) {
        this.mapViewController = mapViewController;

        // Set Combo Box Values
        floorComboBox.getItems().setAll(mapViewController.getFloorManager().getAll());

        // Set Access
        saveButton.setDisable(true);
    }

    //#region External Events
        public void setFloor(Floor newFloor) {
            ComponentWrapper.setValueSilently(floorComboBox, newFloor);
        }
    //#endregion

    //#region FXML Events
        @FXML
        void onFloorChanged(ActionEvent event) {
            mapViewController.getFloorManager().changeCurrent(floorComboBox.getValue());
        }

        @FXML
        void onViewModeButtonPressed(ActionEvent event) {
            mapViewController.switchMode(false);
        }

        @FXML
        void onEditModeButtonPressed(ActionEvent event) {
            mapViewController.switchMode(true);
        }

        @FXML
        void onSaveButtonPressed(ActionEvent event) {
            mapViewController.getLocationManager().saveChanges();
            saveButton.setDisable(true);
        }

        @FXML
        void onExitButtonPressed(ActionEvent event) {
            // TODO: Return to map dashboard page
        }
    //#endregion

    public void switchMode(boolean editing) {
        editModeButton.setVisible(!editing);
        viewModeButton.setVisible(editing);
        saveButton.setVisible(editing);
        exitButton.setVisible(!editing);
        saveButton.setDisable(true);
    }

    public void canSave() {
        saveButton.setDisable(false);
    }
}
