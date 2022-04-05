package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseMapViewController implements Initializable {

    // Base view components
    @FXML
    VBox inspectButtonPane;

    @FXML
    VBox locationDescriptionPane;

    @FXML
    VBox mapPane;

    @FXML
    GridPane mapViewGridPane;

    // External controllers
    EditMapController editMapController;
    ViewMapController viewMapController;

    // Constants
    public static final String MAP_PATH = "view/location/map/map.fxml";
    public static final String FLOOR_EDIT_PANE = "view/location/map/floor_edit_pane.fxml";
    public static final String LOCATION_INFO_PANE = "view/location/map/location_info_pane.fxml";
    public static final String EDIT_MODE_PANE = "view/location/map/edit_map_locations_pane.fxml";


    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        mapViewGridPane.setMaxHeight(Double.MAX_VALUE);
        mapViewGridPane.setMaxWidth(Double.MAX_VALUE);

        // Load edit info pane into grid pane
        App.View floorPane = App.instance.loadView(FLOOR_EDIT_PANE);
        inspectButtonPane.getChildren().add(floorPane.getNode());
        ((FloorEditPaneController) floorPane.getController()).setBaseMapViewController(this);

        // Load location info pane into grid pane
        App.View locationInfoPane = App.instance.loadView(LOCATION_INFO_PANE);
        locationDescriptionPane.getChildren().add(locationInfoPane.getNode());

        // Load map display into grid pane
        App.View mapPane = App.instance.loadView(MAP_PATH, new ViewMapController());
        mapViewGridPane.add(mapPane.getNode(), 0,0);
    }

    public void swapToEditMode() {
        inspectButtonPane.getChildren().remove(0,1);
        App.View swapPane = App.instance.loadView(EDIT_MODE_PANE);
        inspectButtonPane.getChildren().add(swapPane.getNode());
        ((EditMapLocationsPaneController) swapPane.getController()).setBaseMapViewController(this);

        App.View mapPane = App.instance.loadView(MAP_PATH, new EditMapController());
        mapViewGridPane.getChildren().remove(0, 0);
        mapViewGridPane.add(mapPane.getNode(), 0,0);
    }

    public void swapToViewMode() {
        inspectButtonPane.getChildren().remove(0,1);
        App.View swapPane = App.instance.loadView(FLOOR_EDIT_PANE);
        inspectButtonPane.getChildren().add(swapPane.getNode());
        ((FloorEditPaneController) swapPane.getController()).setBaseMapViewController(this);

        App.View mapPane = App.instance.loadView(MAP_PATH, new ViewMapController());
        mapViewGridPane.getChildren().remove(0, 0);
        mapViewGridPane.add(mapPane.getNode(), 0,0);
    }

    public void saveMap() {

    }
}
