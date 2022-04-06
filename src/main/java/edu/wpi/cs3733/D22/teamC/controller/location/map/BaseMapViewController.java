package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BaseMapViewController implements Initializable {
    // Containers
    @FXML GridPane gridPane;
    @FXML VBox mapBox;
    @FXML VBox mapControlsBox;
    @FXML VBox locationInfoBox;

    // Controllers
    ViewMapController mapController;
    LocationInfoController locationInfoController;

    // Constants
    public static final String MAP_PATH = "view/location/map/map.fxml";
    public static final String VIEW_MAP_CONTROLS_PATH = "view/location/map/view_map_controls.fxml";
    public static final String EDIT_MAP_CONTROLS_PATH = "view/location/map/edit_map_controls.fxml";
    public static final String LOCATION_INFO_PATH = "view/location/map/location_info.fxml";

    // SVGs
    public SVGPath medicalBedIcon = new SVGPath();
    public SVGPath medicalPumpIcon = new SVGPath();
    public SVGPath reclinerIcon = new SVGPath();
    public SVGPath xRayIcon = new SVGPath();
    
    // Variables
    private Floor floor;

    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        gridPane.setMaxHeight(Double.MAX_VALUE);
        gridPane.setMaxWidth(Double.MAX_VALUE);

        setViewMode();
        loadSVGs();
    }

    //#region SVG stuff
        protected void loadSVGs() {
            // Load SVGs
            medicalBedIcon.setContent("M176 288C220.1 288 256 252.1 256 208S220.1 128 176 128S96 163.9 96 208S131.9 288 176 288zM544 128H304C295.2 128 288 135.2 288 144V320H64V48C64 39.16 56.84 32 48 32h-32C7.163 32 0 39.16 0 48v416C0 472.8 7.163 480 16 480h32C56.84 480 64 472.8 64 464V416h512v48c0 8.837 7.163 16 16 16h32c8.837 0 16-7.163 16-16V224C640 170.1 597 128 544 128z");
            medicalBedIcon.setFill(Color.web("#183153"));
            medicalBedIcon.setScaleX(.03);
            medicalBedIcon.setScaleY(.03);
            //gridPane.getChildren().add(medicalBedIcon);

            // load XRay SVG
            xRayIcon.setContent("M208 352C199.2 352 192 359.2 192 368C192 376.8 199.2 384 208 384S224 376.8 224 368C224 359.2 216.8 352 208 352zM304 384c8.836 0 16-7.164 16-16c0-8.838-7.164-16-16-16S288 359.2 288 368C288 376.8 295.2 384 304 384zM496 96C504.8 96 512 88.84 512 80v-32C512 39.16 504.8 32 496 32h-480C7.164 32 0 39.16 0 48v32C0 88.84 7.164 96 16 96H32v320H16C7.164 416 0 423.2 0 432v32C0 472.8 7.164 480 16 480h480c8.836 0 16-7.164 16-16v-32c0-8.836-7.164-16-16-16H480V96H496zM416 216C416 220.4 412.4 224 408 224H272v32h104C380.4 256 384 259.6 384 264v16C384 284.4 380.4 288 376 288H272v32h69.33c25.56 0 40.8 28.48 26.62 49.75l-21.33 32C340.7 410.7 330.7 416 319.1 416H192c-10.7 0-20.69-5.347-26.62-14.25l-21.33-32C129.9 348.5 145.1 320 170.7 320H240V288H136C131.6 288 128 284.4 128 280v-16C128 259.6 131.6 256 136 256H240V224H104C99.6 224 96 220.4 96 216v-16C96 195.6 99.6 192 104 192H240V160H136C131.6 160 128 156.4 128 152v-16C128 131.6 131.6 128 136 128H240V104C240 99.6 243.6 96 248 96h16c4.4 0 8 3.6 8 8V128h104C380.4 128 384 131.6 384 136v16C384 156.4 380.4 160 376 160H272v32h136C412.4 192 416 195.6 416 200V216z");
            xRayIcon.setFill(Color.web("#183153"));
            xRayIcon.setScaleX(.03);
            xRayIcon.setScaleY(.03);
            //gridPane.getChildren().add(xRayIcon);

            // load recliner SVG
            reclinerIcon.setContent("M592 224C565.5 224 544 245.5 544 272V352H96V272C96 245.5 74.51 224 48 224S0 245.5 0 272v192C0 472.8 7.164 480 16 480h64c8.836 0 15.1-7.164 15.1-16L96 448h448v16c0 8.836 7.164 16 16 16h64c8.836 0 16-7.164 16-16v-192C640 245.5 618.5 224 592 224zM128 272V320h384V272c0-38.63 27.53-70.95 64-78.38V160c0-70.69-57.31-128-128-128H191.1c-70.69 0-128 57.31-128 128L64 193.6C100.5 201.1 128 233.4 128 272z");
            reclinerIcon.setFill(Color.web("#183153"));
            reclinerIcon.setScaleX(.03);
            reclinerIcon.setScaleY(.03);
            //gridPane.getChildren().add(reclinerIcon);

            // load medical pump svg
            medicalPumpIcon.setContent("M379.3 94.06l-43.32-43.32C323.1 38.74 307.7 32 290.8 32h-66.75c0-17.67-14.33-32-32-32H127.1c-17.67 0-32 14.33-32 32L96 128h128l-.0002-32h66.75l43.31 43.31c6.248 6.248 16.38 6.248 22.63 0l22.62-22.62C385.6 110.4 385.6 100.3 379.3 94.06zM235.6 160H84.37C51.27 160 23.63 185.2 20.63 218.2l-20.36 224C-3.139 479.7 26.37 512 64.01 512h191.1c37.63 0 67.14-32.31 63.74-69.79l-20.36-224C296.4 185.2 268.7 160 235.6 160zM239.1 333.3c0 7.363-5.971 13.33-13.33 13.33h-40v40c0 7.363-5.969 13.33-13.33 13.33h-26.67c-7.363 0-13.33-5.971-13.33-13.33v-40H93.33c-7.363 0-13.33-5.971-13.33-13.33V306.7c0-7.365 5.971-13.33 13.33-13.33h40v-40C133.3 245.1 139.3 240 146.7 240h26.67c7.363 0 13.33 5.969 13.33 13.33v40h40c7.363 0 13.33 5.969 13.33 13.33V333.3z");reclinerIcon.setFill(Color.web("#183153"));
            medicalPumpIcon.setFill(Color.web("#183153"));
            medicalPumpIcon.setScaleX(.03);
            medicalPumpIcon.setScaleY(.03);
            gridPane.getChildren().add(medicalPumpIcon);

        }

        //TODO: create instance of a medical equipment icon
    //#endregion

    public void setFloor(Floor floor) {
        this.floor = floor;
        mapController.setFloor(floor);
    }

    public Floor getFloor() {
        return floor;
    }

    //#region Mode Switching
        /**
         * Swap to Edit Mode, clearing first.
         */
        public void swapToEditMode() {
            clearLastMode();
            setEditMode();
        }

        /**
         * Set to Edit Mode by replacing Nodes & Controllers.
         */
        private void setEditMode() {
            App.View swapPane = App.instance.loadView(EDIT_MAP_CONTROLS_PATH);
            mapControlsBox.getChildren().add(swapPane.getNode());
            EditMapControlsController editMapControlsController = (EditMapControlsController) swapPane.getController();
            editMapControlsController.setParentController(this);

            App.View locationPane = App.instance.loadView(LOCATION_INFO_PATH);
            locationInfoBox.getChildren().add(locationPane.getNode());
            locationInfoController = (LocationInfoController) locationPane.getController();
            locationInfoController.setParentController(this);
            locationInfoController.setEditable(true);
            locationInfoController.setVisible(false);

            App.View mapPane = App.instance.loadView(MAP_PATH, new EditMapController());
            gridPane.add(mapPane.getNode(), 0,0);
            mapController = (EditMapController) mapPane.getController();
            mapController.setParentController(this);

            mapController.setFloor(floor);
        }

        /**
         * Swap to View Mode, clearing first.
         */
        public void swapToViewMode() {
            clearLastMode();
            setViewMode();
        }

        /**
         * Swap to View Mode by replacing Nodes & Controllers.
         */
        private void setViewMode() {
            App.View swapPane = App.instance.loadView(VIEW_MAP_CONTROLS_PATH);
            mapControlsBox.getChildren().add(swapPane.getNode());
            ViewMapControlsController viewMapControlsController = (ViewMapControlsController) swapPane.getController();
            viewMapControlsController.setParentController(this);

            App.View locationPane = App.instance.loadView(LOCATION_INFO_PATH);
            locationInfoBox.getChildren().add(locationPane.getNode());
            locationInfoController = (LocationInfoController) locationPane.getController();
            locationInfoController.setParentController(this);
            locationInfoController.setEditable(false);
            locationInfoController.setVisible(false);

            App.View mapPane = App.instance.loadView(MAP_PATH, new ViewMapController());
            gridPane.add(mapPane.getNode(), 0,0);
            mapController = (ViewMapController) mapPane.getController();
            mapController.setParentController(this);

            ((ViewMapControlsController) swapPane.getController()).setup(this, floor);
            mapController.setFloor(floor);
        }

        public void clearLastMode() {
            gridPane.getChildren().remove(0, 0);
            mapControlsBox.getChildren().remove(0, 1);
            locationInfoBox.getChildren().remove(0, 1);
        }
    //#endregion

    //#region External Referencing
        public ViewMapController getMapController() {
            return (ViewMapController) mapController;
        }

        public LocationInfoController getLocationInfoController() {
            return (LocationInfoController) locationInfoController;
        }
    //#endregion


}