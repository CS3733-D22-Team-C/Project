package edu.wpi.cs3733.D22.teamC.controller.map.panel;

import edu.wpi.cs3733.D22.teamC.controller.map.MapController;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MapControlsController implements Initializable {
    // FXML
    @FXML ComboBox<Floor> floorComboBox;

    // References
    private MapViewController mapViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Combo Box
        ComponentWrapper.initializeComboBox(floorComboBox, Floor::getShortName);
    }

    public void setup(MapViewController mapViewController) {
        this.mapViewController = mapViewController;

        // Set Combo Box Values
        floorComboBox.getItems().setAll(mapViewController.getFloorManager().getAll());
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
    //#endregion
}
