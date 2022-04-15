package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.MapController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MapViewController implements Initializable {
    // Constants
    public static final String MAP_PATH = "view/location/map/map_view/map.fxml";

    // FXML
    @FXML public GridPane mapViewPane;

    // References
    public MapController mapController;

    // Variables
    public List<Floor> floors;
    public Floor currentFloor;

    public List<Location> locations;
    public Location currentLocation;

    // Hooks
    private Consumer<Location> externalLocationChangeHook;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Get Floors
        this.floors = new FloorDAO().getAll();
        this.currentFloor = floors.get(0);

        // Get Locations
        this.locations = new LocationDAO().getAll();
        this.currentLocation = null;

        setup();
    }

    public void setup() {
        // (View) Map Pane
        App.View mapPane = App.instance.loadView(MAP_PATH, new MapController());
        mapViewPane.getChildren().add(mapPane.getNode());
        mapController = (MapController) mapPane.getController();
        mapController.setParentController(this);
    }

    //#region Floor Interaction
        public List<Floor> getAllFloors() {
            return floors;
        }

        public Floor getFloorByID(String id) {
            List<Floor> floorList = floors.stream().filter(floor -> floor.getID().equals(id)).collect(Collectors.toList());
            return (floorList.size() > 0) ? floorList.get(0) : null;
        }

        public Floor getCurrentFloor() {
            return currentFloor;
        }

        /**
         * Change the current floor to the given Floor.
         * @param floor The Floor to be changed to.
         */
        public void changeCurrentFloor(Floor floor) {
            this.currentFloor = floor;
            this.currentLocation = null;

            renderCurrentFloor();
        }

        /**
         * Render the current floor via the Map Controller
         */
        public void renderCurrentFloor() {
            mapController.renderFloor(currentFloor, locations.stream().filter(location -> location.getFloor().equals(currentFloor.getID())).collect(Collectors.toList()));
        }
    //#endregion

    //#region Location Interaction
        /**
         * Get a list of currently loaded locations.
         * @return A list of currently loaded locations. In edit mode, all map edits will be applied to them.
         */
        public List<Location> getAllLocations() {
            return locations;
        }

        /**
         * Get Location by ID.
         * @return Location of the given ID.
         */
        public Location getLocationByID(String id) {
            List<Location> locationList = locations.stream().filter(location -> location.getID().equals(id)).collect(Collectors.toList());
            return (locationList.size() > 0) ? locationList.get(0) : null;
        }

        public Location getCurrentLocation() {
            return currentLocation;
        }

        /**
         * Change the current location to the given Location.
         * @param location The Location to be changed to.
         * @param locked Whether the Current Location selection is locking.
         */
        public void changeCurrentLocation(Location location, boolean locked) {
            this.currentLocation = location;

            // Update Map View
            if (locked) mapController.setLocation(currentLocation);

            // Use External Listener
            if (externalLocationChangeHook != null) externalLocationChangeHook.accept(location);
        }
        public void changeCurrentLocation(Location location) { changeCurrentLocation(location, true); }
    //#endregion

    public void setExternalChangeListener(Consumer<Location> listener) {
        externalLocationChangeHook = listener;
    }
}
