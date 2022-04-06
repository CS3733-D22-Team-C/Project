package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LocationInfoController implements Initializable {
    // Fields
    @FXML TextField idField;
    @FXML TextField longNameField;
    @FXML TextField shortNameField;

    @FXML JFXComboBox<String> typeField;

    // Containers
    @FXML VBox container;

    // References
    private BaseMapViewController parentController;

    // Variables
    private Location focusedLocation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Type Dropdown
        typeField.getItems().setAll(Arrays.stream(Location.NodeType.values()).map(Enum::name).collect(Collectors.toList()));
    }

    //#region Component State
        /**
         * Set all changeable fields within the location description panes to be editable/uneditable.
         * @param editable Boolean if fields should be editable.
         */
        public void setEditable(boolean editable) {
            longNameField.setDisable(!editable);
            shortNameField.setDisable(!editable);
            typeField.setDisable(!editable);
        }

        /**
         * Sets the visibility of the LocationInfo component.
         * @param visible Boolean if component should be visible.
         */
        public void setVisible(boolean visible) {
            container.setVisible(visible);
        }

        /**
         * Sets the currently focused Location.
         * @param location Location which has received focus.
         */
        public void setLocation(Location location) {
            this.focusedLocation = location;

            idField.setText(Integer.toString(location.getNodeID()));
            longNameField.setText(location.getLongName());
            shortNameField.setText(location.getShortName());
            typeField.setValue(location.getNodeType().toString());
        }
    //#endregion

    //#region External Setup
        public void setParentController(BaseMapViewController baseMapViewController) {
            this.parentController = baseMapViewController;
        }
    //#endregion

    //#region External Interaction
        public void onMapLocationFocus(ViewMapController.MapLocation mapLocation) {
            setVisible((mapLocation != null));
            if (mapLocation != null) setLocation(mapLocation.location);
        }

        @FXML
        public void onUpdateLocation() {
            if (focusedLocation != null) {
                focusedLocation.setLongName(longNameField.getText());
                focusedLocation.setShortName(shortNameField.getText());
                focusedLocation.setNodeType(Location.NodeType.valueOf((typeField.getValue())));
            }
        }
    //#endregion
}
