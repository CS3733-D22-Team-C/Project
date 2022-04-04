package edu.wpi.cs3733.D22.teamC.controller.location.map;

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
    GridPane mapViewGridPane;

    // Text Fields
    @FXML
    TextField IDTextField;
    @FXML
    TextField LongNameTextField;
    @FXML
    TextField ShortNameTextField;
    @FXML
    TextField TypeTextField;

    // Constants
    public static final String MAP_PATH = "../../../view/location/map/map.fxml";
    public static final String EDIT_BUTTON_PANE = "../../../view/location/map/edit_button_pane.fxml";
    public static final String LOCATION_INFO_PANE = "../../../view/location/map/location_info_pane.fxml";
    public static final String EDIT_MODE_PANE = "../../../view/location/map/edit_map_locations_pane.fxml";


    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        try {
            mapViewGridPane.setMaxHeight(Double.MAX_VALUE);
            mapViewGridPane.setMaxWidth(Double.MAX_VALUE);

            // Load edit info pane into grid pane
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(EDIT_BUTTON_PANE));
            inspectButtonPane.getChildren().add(loader.load());

            // Load location info pane into grid pane
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(LOCATION_INFO_PANE));
            locationDescriptionPane.getChildren().add(loader.load());

            // Load map display into grid pane
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(MAP_PATH));
            mapViewGridPane.add(loader.load(), 0,0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
