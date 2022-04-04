package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

    // Constants
    public static final String MAP_PATH = "";
    public static final String EDIT_BUTTON_PANE = "../../../view/location/map/edit_button_pane.fxml";
    public static final String LOCATION_INFO_PANE = "../../../view/location/map/location_info_pane.fxml";


    @Override
    public final void initialize(URL location, ResourceBundle resource) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(LOCATION_INFO_PANE));
            Node node = loader.load();
            inspectButtonPane.getChildren().add(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
