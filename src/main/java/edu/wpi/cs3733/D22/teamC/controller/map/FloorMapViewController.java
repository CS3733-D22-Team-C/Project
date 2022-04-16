package edu.wpi.cs3733.D22.teamC.controller.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.data.floor.FloorManager;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationManager;
import edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment.MedicalEquipmentManager;
import edu.wpi.cs3733.D22.teamC.controller.map.panel.LocationInfoController;
import edu.wpi.cs3733.D22.teamC.controller.map.panel.MapControlsController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for floor map view which sets up interaction between multiple controllers and edit mode.
 */
public class FloorMapViewController extends MapViewController {
    // Constants
    public static final String MAP_CONTROLS_PATH = "view/map/panel/controls.fxml";
    public static final String LOCATION_INFO_PATH = "view/map/panel/info.fxml";

    // FXML
    @FXML Pane controlsPane;
    @FXML Pane infoPane;

    // References
    private LocationInfoController locationInfoController;
    private MapControlsController mapControlsController;
    private MedicalEquipmentManager medicalEquipmentManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        // Controls
        App.View<MapControlsController> controlsView = App.instance.loadView(MAP_CONTROLS_PATH);
        controlsPane.getChildren().add(controlsView.getNode());
        mapControlsController = controlsView.getController();
        mapControlsController.setup(this);

        // Info
        App.View<LocationInfoController> infoView = App.instance.loadView(LOCATION_INFO_PATH);
        infoPane.getChildren().add(infoView.getNode());
        locationInfoController = infoView.getController();
        locationInfoController.setup(this);

        // Events
        floorManager.onChangeCurrentEvents.add((oldFloor, newFloor) -> mapControlsController.setFloor(newFloor));

        locationManager.onChangeCurrentEvents.add((oldLocation, newLocation) -> locationInfoController.setLocation(newLocation));
        locationManager.onUpdateDataEvents.add(mapControlsController::canSave);

        switchMode(false);

        // TODO: Remove hard-coded change
        floorManager.changeCurrent(floorManager.getAll().get(0));
    }

    //#region Mode Switching
        public void switchMode(boolean editing) {
            locationManager.switchMode(editing);
            mapControlsController.switchMode(editing);
            locationInfoController.setEditable(editing);
        }
    //#endregion

    //#region Interfacing Getters/Setters
        public MedicalEquipmentManager getMedicalEquipmentManager() {
            return medicalEquipmentManager;
        }

        public void activateMedicalEquipmentManager(boolean activate) {
            if (activate) {
                medicalEquipmentManager = new MedicalEquipmentManager(this);
            } else {
                if (medicalEquipmentManager != null) medicalEquipmentManager.shutdown();
                medicalEquipmentManager = null;
            }
        }
    //#endregion
}
