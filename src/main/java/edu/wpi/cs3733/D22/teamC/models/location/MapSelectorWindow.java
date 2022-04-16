package edu.wpi.cs3733.D22.teamC.models.location;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.generic.SelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MapSelectorWindow extends SelectorWindow<Location> implements Initializable {
    // Constants
    private static final String SELECTOR_MAP_VIEW_PATH = "view/map/selector_map_view.fxml";

    // FXML
    @FXML private ComboBox<Floor> floorComboBox;
    @FXML private TextField selectedLocationField;
    @FXML private GridPane mapViewContainer;

    // References
    MapViewController mapViewController;

    public MapSelectorWindow(Consumer<Location> consumer) {
        super(consumer);

        // Initialize Map View Controller insert
        App.View<MapViewController> view = App.instance.loadView(SELECTOR_MAP_VIEW_PATH);
        this.mapViewController = view.getController();

        mapViewContainer.getChildren().add(view.getNode());


        // Initialize Floor Combo Box values
        mapViewController.getLocationManager().onChangeCurrentEvents.add((oldLocation, newLocation) -> this.updateLocationField(newLocation));
        floorComboBox.getItems().setAll(mapViewController.getFloorManager().getAll());
        ComponentWrapper.setValueSilently(floorComboBox, mapViewController.getFloorManager().getCurrent());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Setup Floor Combo Box
        ComponentWrapper.initializeComboBox(floorComboBox, Floor::getShortName);
    }

    public void updateLocationField(Location location) {
        selectedLocationField.setText(location == null ? "" : location.getShortName());
    }

    //#region FXML Events
        @FXML
        void onSelectButtonPressed(ActionEvent event) {
            onSelectionMade(mapViewController.getLocationManager().getCurrent());
        }

        @FXML
        void onFloorChanged(ActionEvent event) {
            mapViewController.getFloorManager().changeCurrent(floorComboBox.getValue());
            updateLocationField(null);
        }
    //#endregion

    @Override
    protected String getView() {
        return "view/selector/map_view.fxml";
    }
}
