package edu.wpi.cs3733.D22.teamC.controller.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.data.floor.FloorManager;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationManager;
import edu.wpi.cs3733.D22.teamC.controller.map.panel.LocationInfoController;
import edu.wpi.cs3733.D22.teamC.controller.map.panel.MapControlsController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for generic map view which sets up interaction for default map and managers.
 */
public class MapViewController implements Initializable {
    // Constants
    public static final String MAP_PATH = "view/map/map.fxml";

    // FXML
    @FXML Pane mapPane;

    // References
    protected MapController mapController;

    protected FloorManager floorManager;
    protected LocationManager locationManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Managers
        floorManager = new FloorManager(this);
        locationManager = new LocationManager(this);

        // Map
        App.View<MapController> mapView = App.instance.loadView(MAP_PATH);
        mapPane.getChildren().add(mapView.getNode());
        mapController = mapView.getController();
        mapController.setup(this);

        // Events
        floorManager.onChangeCurrentEvents.add((oldFloor, newFloor) -> mapController.setFloor(newFloor));
        floorManager.onChangeCurrentEvents.add((oldFloor, newFloor) -> locationManager.renderFloor(newFloor));
        floorManager.onChangeCurrentEvents.add((oldFloor, newFloor) -> locationManager.changeCurrent(null));

        // TODO: Remove hard-coded change
        floorManager.changeCurrent(floorManager.getAll().get(0));
    }

    //#region Getters
        public Pane getMap() {
            return mapController.getMap();
        }

        public FloorManager getFloorManager() {
            return floorManager;
        }

        public LocationManager getLocationManager() {
            return locationManager;
        }
    //#endregion
}
