package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
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

    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        gridPane.setMaxHeight(Double.MAX_VALUE);
        gridPane.setMaxWidth(Double.MAX_VALUE);

        setViewMode();
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
            App.View mapPane = App.instance.loadView(MAP_PATH, new EditMapController());
            gridPane.add(mapPane.getNode(), 0,0);
            mapController = (EditMapController) mapPane.getController();
            mapController.setParentController(this);

            App.View swapPane = App.instance.loadView(EDIT_MAP_CONTROLS_PATH);
            mapControlsBox.getChildren().add(swapPane.getNode());
            ((EditMapControlsController) swapPane.getController()).setBaseMapViewController(this);

            App.View locationPane = App.instance.loadView(LOCATION_INFO_PATH);
            locationInfoBox.getChildren().add(locationPane.getNode());
            locationInfoController = (LocationInfoController) locationPane.getController();
            locationInfoController.setParentController(this);
            locationInfoController.setEditable(true);
            locationInfoController.setVisible(false);
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
            App.View mapPane = App.instance.loadView(MAP_PATH, new ViewMapController());
            gridPane.add(mapPane.getNode(), 0,0);
            mapController = (ViewMapController) mapPane.getController();
            mapController.setParentController(this);

            App.View swapPane = App.instance.loadView(VIEW_MAP_CONTROLS_PATH);
            mapControlsBox.getChildren().add(swapPane.getNode());
            ((ViewMapControlsController) swapPane.getController()).setParentController(this);

            App.View locationPane = App.instance.loadView(LOCATION_INFO_PATH);
            locationInfoBox.getChildren().add(locationPane.getNode());
            locationInfoController = (LocationInfoController) locationPane.getController();
            locationInfoController.setParentController(this);
            locationInfoController.setEditable(false);
            locationInfoController.setVisible(false);
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