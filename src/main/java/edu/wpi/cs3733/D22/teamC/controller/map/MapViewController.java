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
 * Controller for floor map view which sets up interaction for inner controllers on the page.
 */
public class MapViewController implements Initializable {
    // Constants
    public static final String MAP_PATH = "view/map/map.fxml";
    public static final String MAP_CONTROLS_PATH = "view/map/panel/controls.fxml";
    public static final String LOCATION_INFO_PATH = "view/map/panel/info.fxml";

    // FXML
    @FXML Pane mapPane;
    @FXML Pane controlsPane;
    @FXML Pane infoPane;

    // References
    private MapController mapController;

    private FloorManager floorManager;
    private LocationManager locationManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Managers
        floorManager = new FloorManager(this);
        locationManager = new LocationManager(this);

        // Map
        App.View<MapController> mapView = App.instance.loadView(MAP_PATH);
        mapPane.getChildren().add(mapView.getNode());
        mapController = mapView.getController();

        // Controls
        App.View<MapControlsController> controlsView = App.instance.loadView(MAP_CONTROLS_PATH);
        controlsPane.getChildren().add(controlsView.getNode());
        MapControlsController mapControlsController = controlsView.getController();
        mapControlsController.setup(this);

        // Info
        App.View<LocationInfoController> infoView = App.instance.loadView(LOCATION_INFO_PATH);
        infoPane.getChildren().add(infoView.getNode());
        LocationInfoController locationInfoController = infoView.getController();
        locationInfoController.setup(this);

        // Events
        floorManager.addChangeCurrentEvent((oldFloor, newFloor) -> mapController.setFloor(newFloor));
        floorManager.addChangeCurrentEvent((oldFloor, newFloor) -> mapControlsController.setFloor(newFloor));
        floorManager.addChangeCurrentEvent((oldFloor, newFloor) -> locationManager.renderFloor(newFloor));
        floorManager.addChangeCurrentEvent((oldFloor, newFloor) -> locationManager.changeCurrent(null));

        locationManager.addChangeCurrentEvent((oldLocation, newLocation) -> locationInfoController.setLocation(newLocation));

        mapController.addClickedMapEvent((event) -> locationManager.unfocusAll());

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
