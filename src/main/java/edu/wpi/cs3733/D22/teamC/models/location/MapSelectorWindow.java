package edu.wpi.cs3733.D22.teamC.models.location;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.MapController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController.MAP_PATH;

public class MapSelectorWindow implements Initializable {
    // FXML
    @FXML private GridPane mapViewPane;
    @FXML private TextField selectedLocationField;

    // References
    private MapController mapController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        App.View<MapController> mapPane = App.instance.loadView(MAP_PATH, new MapController());
//        mapViewPane.getChildren().add(mapPane.getNode());
//        mapController = mapPane.getController();
//        mapController.setParentController(this);
    }

    //#region FXML Events
        @FXML
        void onSelectButtonPressed(ActionEvent event) {

        }
    //#endregion
}
