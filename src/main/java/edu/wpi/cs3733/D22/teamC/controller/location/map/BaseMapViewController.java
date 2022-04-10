package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.controls.EditMapControlsController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.controls.MapControlsController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BaseMapViewController implements Initializable {
    // Constants
    public static final String MAP_PATH = "view/location/map/map.fxml";
    public static final String VIEW_CONTROLS_PATH = "view/location/map/controls/view_controls.fxml";
    public static final String EDIT_CONTROLS_PATH = "view/location/map/controls/edit_controls.fxml";
    public static final String INFO_PANE_PATH = "view/location/map/info_pane.fxml";

    // Containers
    @FXML Pane mapViewPane;
    @FXML Pane controlsBox;
    @FXML Pane infoPaneBox;

    // Controllers
    ViewMapController mapController;
    InfoPaneController infoPaneController;
    MapControlsController mapControlsController;
    
    // Variables
    private boolean isEditMode = false;

    List<Floor> floors;
    Floor currentFloor;

    List<Location> touchedLocations = new ArrayList<>();
    List<Location> additionLocations = new ArrayList<>();
    List<Location> deletionLocations = new ArrayList<>();
    Location currentLocation;

    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        // Get Floors
        floors = new FloorDAO().getAll();
        currentFloor = floors.get(0);

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

        public Floor getFloorByID(int id) {
            return floors.stream().filter(floor -> floor.getFloorID() == id).collect(Collectors.toList()).get(0);
        }

        public Floor getCurrentFloor() {
            return currentFloor;
        }

        public void setCurrentFloor(Floor floor) {
            this.currentFloor = floor;
            mapController.renderFloor(currentFloor);
        }
    //#endregion

    //#region Location Interaction
        public Location getCurrentLocation() {
            return currentLocation;
        }

        public void setCurrentLocation(Location location) {
            // Base
            if (currentLocation != null) touchLocation(location);
            this.currentLocation = location;

            // Info
            infoPaneController.setVisible((currentLocation != null));
            infoPaneController.setLocation(currentLocation);

            // Map
            if (location == null && mapController.clickedLocationNode != null) mapController.clickedLocationNode.deactivateNode();
        }

        public void addLocation(int x, int y) {
            if (isEditMode) {
                // Create Location
                Location location = new Location();

                location.setX((int) x);
                location.setY((int) y);
                location.setFloor(currentFloor.getFloorID());
                location.setNodeType(Location.NodeType.PATI);

                addLocation(location);
            }
        }

    public void addLocation(Location location) {
        if (isEditMode) {
            // Location addition reflected in Lists
            additionLocations.add(location);

            // Location addition reflected in LocationNode
            mapController.addLocationNode(location);

            setCurrentLocation(location);
        }
    }

        public void touchLocation(Location location) {
            if (isEditMode) {
                ((EditMapControlsController) mapControlsController).setSaveStatus(true);

                if (!additionLocations.contains(location) && !deletionLocations.contains(location)) {
                    if (touchedLocations.contains(location)) touchedLocations.remove(location);
                    touchedLocations.add(location);
                }
            }
        }

        public void resetLocation(Location location) {
            // Location reset reflected in Lists
            Location original = new LocationDAO().getByID(currentLocation.getNodeID());
            currentLocation.Copy(original);

            // Location reset reflected in LocationNode
            mapController.resetLocationNode(currentLocation);

            // Location reset reflected in Info
            infoPaneController.setLocation(currentLocation);
        }

        public void deleteLocation(Location location) {
            if (location != null && isEditMode) {
                // Location Deletion reflected in Lists
                if (additionLocations.contains(location)) {
                    additionLocations.remove(location);
                } else {
                    deletionLocations.add(location);
                }

                // Location Deletion reflected in Maps
                mapController.removeLocationNode(location);
            }
        }
    //#endregion

    //#region Mode Switching
        public void saveLocationChanges() {
            LocationDAO locationDAO = new LocationDAO();

            // Update touched locations
            for (Location location : touchedLocations) {
                locationDAO.update(location);
            }
            touchedLocations = new ArrayList<>();

            // Insert addition locations
            for (Location location : additionLocations) {
                locationDAO.insert(location);
            }
            additionLocations = new ArrayList<>();

            // Delete deletion locations
            for (Location location : deletionLocations) {
                locationDAO.delete(location);
            }
            deletionLocations = new ArrayList<>();
        }

        public void resetLocationChanges() {
            touchedLocations = new ArrayList<>();
            additionLocations = new ArrayList<>();
            deletionLocations = new ArrayList<>();
        }

        public void renderLocationChanges() {
            // Update touched location nodes
            for (Location location : touchedLocations.stream().filter(location -> location.getFloor() == currentFloor.getFloorID()).collect(Collectors.toList())) {
                mapController.updateLocationNode(location);
            }

            // Insert additional location nodes
            for (Location location : additionLocations.stream().filter(location -> location.getFloor() == currentFloor.getFloorID()).collect(Collectors.toList())) {
                mapController.addLocationNode(location);
            }

            // Delete deletion location nodes
            for (Location location : deletionLocations.stream().filter(location -> location.getFloor() == currentFloor.getFloorID()).collect(Collectors.toList())) {
                mapController.removeLocationNode(location);
            }
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

            mapController.renderFloor(currentFloor);
            renderLocationChanges();
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
            App.View mapPane = App.instance.loadView(MAP_PATH, new ViewMapController());
            mapViewPane.getChildren().add(mapPane.getNode());
            mapController = (ViewMapController) mapPane.getController();
            mapController.setParentController(this);

            mapController.renderFloor(currentFloor);
        }

        public void clearLastMode() {
            mapViewPane.getChildren().remove(0, 1);
            controlsBox.getChildren().remove(0, 1);
        }
    //#endregion

    // TODO: Partially Implemented Tooltips
    //#region SVG stuff
        // SVGs
        public SVGPath medicalBedIcon = new SVGPath();
        public SVGPath medicalPumpIcon = new SVGPath();
        public SVGPath reclinerIcon = new SVGPath();
        public SVGPath xRayIcon = new SVGPath();

        protected void loadSVGs() {
            // Load SVGs
            medicalBedIcon.setContent("M176 288C220.1 288 256 252.1 256 208S220.1 128 176 128S96 163.9 96 208S131.9 288 176 288zM544 128H304C295.2 128 288 135.2 288 144V320H64V48C64 39.16 56.84 32 48 32h-32C7.163 32 0 39.16 0 48v416C0 472.8 7.163 480 16 480h32C56.84 480 64 472.8 64 464V416h512v48c0 8.837 7.163 16 16 16h32c8.837 0 16-7.163 16-16V224C640 170.1 597 128 544 128z");
            medicalBedIcon.setFill(Color.web("#183153"));
            medicalBedIcon.setScaleX(.03);
            medicalBedIcon.setScaleY(.03);
            //splitPane.getChildren().add(medicalBedIcon);

            // load XRay SVG
            xRayIcon.setContent("M208 352C199.2 352 192 359.2 192 368C192 376.8 199.2 384 208 384S224 376.8 224 368C224 359.2 216.8 352 208 352zM304 384c8.836 0 16-7.164 16-16c0-8.838-7.164-16-16-16S288 359.2 288 368C288 376.8 295.2 384 304 384zM496 96C504.8 96 512 88.84 512 80v-32C512 39.16 504.8 32 496 32h-480C7.164 32 0 39.16 0 48v32C0 88.84 7.164 96 16 96H32v320H16C7.164 416 0 423.2 0 432v32C0 472.8 7.164 480 16 480h480c8.836 0 16-7.164 16-16v-32c0-8.836-7.164-16-16-16H480V96H496zM416 216C416 220.4 412.4 224 408 224H272v32h104C380.4 256 384 259.6 384 264v16C384 284.4 380.4 288 376 288H272v32h69.33c25.56 0 40.8 28.48 26.62 49.75l-21.33 32C340.7 410.7 330.7 416 319.1 416H192c-10.7 0-20.69-5.347-26.62-14.25l-21.33-32C129.9 348.5 145.1 320 170.7 320H240V288H136C131.6 288 128 284.4 128 280v-16C128 259.6 131.6 256 136 256H240V224H104C99.6 224 96 220.4 96 216v-16C96 195.6 99.6 192 104 192H240V160H136C131.6 160 128 156.4 128 152v-16C128 131.6 131.6 128 136 128H240V104C240 99.6 243.6 96 248 96h16c4.4 0 8 3.6 8 8V128h104C380.4 128 384 131.6 384 136v16C384 156.4 380.4 160 376 160H272v32h136C412.4 192 416 195.6 416 200V216z");
            xRayIcon.setFill(Color.web("#183153"));
            xRayIcon.setScaleX(.03);
            xRayIcon.setScaleY(.03);
            //splitPane.getChildren().add(xRayIcon);

            // load recliner SVG
            reclinerIcon.setContent("M592 224C565.5 224 544 245.5 544 272V352H96V272C96 245.5 74.51 224 48 224S0 245.5 0 272v192C0 472.8 7.164 480 16 480h64c8.836 0 15.1-7.164 15.1-16L96 448h448v16c0 8.836 7.164 16 16 16h64c8.836 0 16-7.164 16-16v-192C640 245.5 618.5 224 592 224zM128 272V320h384V272c0-38.63 27.53-70.95 64-78.38V160c0-70.69-57.31-128-128-128H191.1c-70.69 0-128 57.31-128 128L64 193.6C100.5 201.1 128 233.4 128 272z");
            reclinerIcon.setFill(Color.web("#183153"));
            reclinerIcon.setScaleX(.03);
            reclinerIcon.setScaleY(.03);
            //splitPane.getChildren().add(reclinerIcon);

            // load medical pump svg
            medicalPumpIcon.setContent("M379.3 94.06l-43.32-43.32C323.1 38.74 307.7 32 290.8 32h-66.75c0-17.67-14.33-32-32-32H127.1c-17.67 0-32 14.33-32 32L96 128h128l-.0002-32h66.75l43.31 43.31c6.248 6.248 16.38 6.248 22.63 0l22.62-22.62C385.6 110.4 385.6 100.3 379.3 94.06zM235.6 160H84.37C51.27 160 23.63 185.2 20.63 218.2l-20.36 224C-3.139 479.7 26.37 512 64.01 512h191.1c37.63 0 67.14-32.31 63.74-69.79l-20.36-224C296.4 185.2 268.7 160 235.6 160zM239.1 333.3c0 7.363-5.971 13.33-13.33 13.33h-40v40c0 7.363-5.969 13.33-13.33 13.33h-26.67c-7.363 0-13.33-5.971-13.33-13.33v-40H93.33c-7.363 0-13.33-5.971-13.33-13.33V306.7c0-7.365 5.971-13.33 13.33-13.33h40v-40C133.3 245.1 139.3 240 146.7 240h26.67c7.363 0 13.33 5.969 13.33 13.33v40h40c7.363 0 13.33 5.969 13.33 13.33V333.3z");reclinerIcon.setFill(Color.web("#183153"));
            medicalPumpIcon.setFill(Color.web("#183153"));
            medicalPumpIcon.setScaleX(.03);
            medicalPumpIcon.setScaleY(.03);
//            splitPane.getChildren().add(medicalPumpIcon);
        }
    //#endregion
}