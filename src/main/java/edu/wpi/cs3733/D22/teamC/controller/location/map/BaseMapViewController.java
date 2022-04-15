package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.controls.EditMapControlsController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.controls.MapControlsController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.EditMapController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.MapController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class BaseMapViewController extends MapViewController {
    // Constants
    public static final String VIEW_CONTROLS_PATH = "view/location/map/controls/view_controls.fxml";
    public static final String EDIT_CONTROLS_PATH = "view/location/map/controls/edit_controls.fxml";
    public static final String INFO_PANE_PATH = "view/location/map/info_pane.fxml";

    // Containers
    @FXML Pane controlsBox;
    @FXML Pane infoPaneBox;

    // Controllers
    public MedicalEquipmentManager medicalEquipmentManager;
    public InfoPaneController infoPaneController;
    MapControlsController mapControlsController;
    
    // Variables
    public boolean isEditMode = false;
    public boolean showMedicalEquipment = true;

    @Override
    public void setup() {
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

    //#region Location Interaction
        @Override
        public void changeCurrentLocation(Location location, boolean locked) {
            super.changeCurrentLocation(location, locked);

            // Update Info Pane
            infoPaneController.setLocation(currentLocation);
            infoPaneController.setVisible(currentLocation != null);
        }

        @Override
        public void changeCurrentFloor(Floor floor) {
            super.changeCurrentFloor(floor);

            mapControlsController.updateFloorSilent(floor);
        }

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
            if (location.getFloor().equals(currentFloor.getID())) mapController.addLocationNode(location);

            return location;
        }

        /**
         * Reset changes for the provided Location.
         * @param location The Location to be reset.
         */
        public void resetLocation(Location location) {
            LocationDAO locationDAO = new LocationDAO();
            Location original = locationDAO.getByID(location.getID());

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

            // Location deletion reflected in Medical Equipment Manager
            mapController.getLocationNode(location).releaseAllMedicalEquipment();

            // Location deletion reflected in Map View
            if (location.getFloor().equals(currentFloor.getID())) mapController.removeLocationNode(location);

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
        public void showMedicalEquipment(boolean visible) {
            showMedicalEquipment = visible;
            if (mapController.activeLocation != null) mapController.activeLocation.showMedicalEquipmentCounters(visible, isEditMode);
            mapController.rightOverlay.setVisible(visible);
        }

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