package edu.wpi.cs3733.D22.teamC.controller.map;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for floor map view which sets up interaction for inner controllers on the page.
 */
public class FloorMapViewController implements Initializable {
    // Constants
    public static final String MAP_PATH = "view/map/map.fxml";

    // FXML
    @FXML Pane mapPane;
    @FXML Pane controlPane;
    @FXML Pane infoPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.View<MapController> mapView = App.instance.loadView(MAP_PATH);
        mapPane.getChildren().add(mapView.getNode());
        MapController mapController = mapView.getController();
    }
}
