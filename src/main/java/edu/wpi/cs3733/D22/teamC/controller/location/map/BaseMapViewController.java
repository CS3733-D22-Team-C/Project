package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.controls.EditMapControlsController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.controls.MapControlsController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.EditMapController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.MapController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BaseMapViewController implements Initializable {
    // Constants
    public static final String MAP_PATH = "view/location/map/map_view/map.fxml";
    public static final String VIEW_CONTROLS_PATH = "view/location/map/controls/view_controls.fxml";
    public static final String EDIT_CONTROLS_PATH = "view/location/map/controls/edit_controls.fxml";
    public static final String INFO_PANE_PATH = "view/location/map/info_pane.fxml";

    // Containers
    @FXML Pane mapViewPane;
    @FXML Pane controlsBox;
    @FXML Pane infoPaneBox;

    // Controllers
    MapController mapController;
    public MedicalEquipmentManager medicalEquipmentManager;
    InfoPaneController infoPaneController;
    MapControlsController mapControlsController;
    
    // Variables
    public boolean isEditMode = false;

    private List<Floor> floors;
    private Floor currentFloor;

    private List<Location> locations;
    private Location currentLocation;

    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        // Get Floors
        this.floors = new FloorDAO().getAll();
        this.currentFloor = floors.get(0);

        // Get Locations
        this.locations = new LocationDAO().getAll();
        this.currentLocation = null;

        // Initialize Medical Equipment Manager
        medicalEquipmentManager = new MedicalEquipmentManager(this);

        // Setup Info Pane
        App.View infoPane = App.instance.loadView(INFO_PANE_PATH);
        infoPaneBox.getChildren().add(infoPane.getNode());
        infoPaneController = (InfoPaneController) infoPane.getController();
        infoPaneController.setup(this);
        infoPaneController.setVisible(false);

        // Set View Mode
        setViewMode();
    }

    //#region Floor Interaction
        public List<Floor> getAllFloors() {
            return floors;
        }

        public Floor getFloorByID(String id) {
            List<Floor> floorList = floors.stream().filter(floor -> floor.getFloorID().equals(id)).collect(Collectors.toList());
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
        private void renderCurrentFloor() {
            mapController.renderFloor(currentFloor, locations.stream().filter(location -> location.getFloor().equals(currentFloor.getFloorID())).collect(Collectors.toList()));
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
            List<Location> locationList = locations.stream().filter(location -> location.getNodeID().equals(id)).collect(Collectors.toList());
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

            // Update Info Pane
            infoPaneController.setLocation(currentLocation);
            infoPaneController.setVisible(currentLocation != null);

            // Update Map View
            if (locked) mapController.setLocation(currentLocation);
        }
        public void changeCurrentLocation(Location location) { changeCurrentLocation(location, true); }

        /**
         * Add a Location.
         * @param location The Location to be added.
         */
        public Location addLocation(Location location) {
            // Location addition reflected in Lists
            locations.add(location);

            // Location addition reflected in Controls
            setSaveStatus();

            // Location addition reflected in Map View
            if (location.getFloor().equals(currentFloor.getFloorID())) mapController.addLocationNode(location);

            return location;
        }

        /**
         * Reset changes for the provided Location.
         * @param location The Location to be reset.
         */
        public void resetLocation(Location location) {
            LocationDAO locationDAO = new LocationDAO();
            Location original = locationDAO.getByID(location.getNodeID());

            if (original != null) {
                location.Copy(original);
                mapController.updateLocationNode(location);
            } else {
                deleteLocation(location);
            }
        }

        public void deleteLocation(Location location) {
            // Location deletion reflected in Lists
            if (locations.contains(location)) locations.remove(location);

            // Location deletion reflected in Controls
            setSaveStatus();

            // Location deletion reflected in Map View
            if (location.getFloor().equals(currentFloor.getFloorID())) mapController.removeLocationNode(location);

            if (currentLocation == location) changeCurrentLocation(null);
        }
    //#endregion

    //#region Stashed Changes
        /**
         * Save locations with changes to DB.
         */
        public void saveChanges() {
            LocationDAO locationDAO = new LocationDAO();
            locationDAO.deleteAllFromTable();

            locations.forEach(locationDAO::insert);

            medicalEquipmentManager.saveChanges();
        }

        /**
         * Clear locations of changes from DB.
         */
        public void clearChanges() {
            LocationDAO locationDAO = new LocationDAO();

            locations = locationDAO.getAll();

            medicalEquipmentManager.clearChanges();
        }
    //#endregion

    //#region Mode Switching
        /**
         * Swap to Edit Mode, clearing first.
         */
        public void swapToEditMode() {
            clearLastMode();
            setEditMode();
            isEditMode = true;
        }

        /**
         * Set to Edit Mode by replacing Nodes & Controllers.
         */
        private void setEditMode() {
            // Info Pane
            infoPaneController.setEditable(true);
            infoPaneController.setVisible(false);

            // (Edit) Controls Pane
            App.View controlsPane = App.instance.loadView(EDIT_CONTROLS_PATH);
            controlsBox.getChildren().add(controlsPane.getNode());
            mapControlsController = (MapControlsController) controlsPane.getController();
            mapControlsController.setup(this);

            // (Edit) Map Pane
            App.View mapPane = App.instance.loadView(MAP_PATH, new EditMapController());
            mapViewPane.getChildren().add(mapPane.getNode());
            mapController = (EditMapController) mapPane.getController();
            mapController.setParentController(this);

            // (Edit) Medical Equipment Manager
            medicalEquipmentManager.setEditMode(true);

            renderCurrentFloor();
        }

        /**
         * Swap to View Mode, clearing first.
         */
        public void swapToViewMode() {
            clearLastMode();
            setViewMode();
            isEditMode = false;
        }

        /**
         * Swap to View Mode by replacing Nodes & Controllers.
         */
        private void setViewMode() {
            // Info Pane
            infoPaneController.setEditable(false);
            infoPaneController.setVisible(false);

            // (View) Controls Pane
            App.View controlsPane = App.instance.loadView(VIEW_CONTROLS_PATH);
            controlsBox.getChildren().add(controlsPane.getNode());
            mapControlsController = (MapControlsController) controlsPane.getController();
            mapControlsController.setup(this);

            // (View) Map Pane
            App.View mapPane = App.instance.loadView(MAP_PATH, new MapController());
            mapViewPane.getChildren().add(mapPane.getNode());
            mapController = (MapController) mapPane.getController();
            mapController.setParentController(this);

            // (View) Medical Equipment Manager
            medicalEquipmentManager.setEditMode(false);

            renderCurrentFloor();
        }

        public void clearLastMode() {
            mapViewPane.getChildren().remove(0, 1);
            controlsBox.getChildren().remove(0, 1);
        }
    //#endregion

    //#region Save Status
        public void setSaveStatus() {
            if (isEditMode) ((EditMapControlsController) mapControlsController).setSaveStatus(true);
        }
    //#endregion
}