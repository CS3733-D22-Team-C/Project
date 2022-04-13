package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LocationTableViewInsertController extends InsertTableViewController<Location> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private TextField longNameField;
    @FXML private TextField shortNameField;
    @FXML private ComboBox<Location.NodeType> nodeTypeComboBox;
    @FXML private TextField buildingField;
    @FXML private ComboBox<Floor> floorComboBox;
    @FXML private TextField xField;
    @FXML private TextField yField;

    @FXML Label title;

    // References
    List<Floor> floors;


    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Add Location");

        // Initialize Data
        floors = new FloorDAO().getAll();

        // Initialize Node Type Combo Box
        nodeTypeComboBox.getItems().setAll(Location.NodeType.values());

        // Initialize Floor Combo Box
        ComponentWrapper.initializeComboBox(floorComboBox, Floor::getShortName);
        floorComboBox.getItems().setAll(floors);

        // Initialize Number-Only Fields
        ComponentWrapper.setIDFieldToNumeric(xField);
        ComponentWrapper.setIDFieldToNumeric(yField);

        confirmButton.setDisable(true);
    }

    //#region Field Interaction
        /**
         * Set values of the given object based on insert fields.
         * @param object The object to have its values overwritten.
         * @return The modified object.
         */
        public Location setValues(Location object) {
            object.setLongName(longNameField.getText());
            object.setShortName(shortNameField.getText());
            object.setNodeType(nodeTypeComboBox.getValue());
            object.setBuilding(buildingField.getText());
            object.setFloor(floorComboBox.getValue().getFloorID());
            object.setX(Integer.valueOf(xField.getText()));
            object.setY(Integer.valueOf(yField.getText()));
            return object;
        }

        /**
         * Set values of insert fields from the given object.
         * @param object The (nullable) object to set field values from.
         */
        public void setFields(Location object) {
            title.setText((object == null) ? "Add Location" : "Edit Location");

            longNameField.setText((object == null) ? "" : object.getLongName());
            shortNameField.setText((object == null) ? "" : object.getShortName());
            nodeTypeComboBox.setValue((object == null) ? null : object.getNodeType());
            buildingField.setText((object == null) ? "" : object.getBuilding());
            floorComboBox.setValue((object == null) ? null : floors.stream().filter(floor -> floor.getFloorID().equals(object.getFloor())).collect(Collectors.toList()).get(0));
            xField.setText((object == null) ? "" : Integer.toString(object.getX()));
            yField.setText((object == null) ? "" : Integer.toString(object.getY()));

            confirmButton.setDisable(true);
        }
    //#endregion

    public boolean checkFieldsFilled() {
        return !(longNameField.getText().equals("")
                || shortNameField.getText().equals("")
                || nodeTypeComboBox.getValue() == null
                || buildingField.getText().equals("")
                || floorComboBox.getValue() == null
                || xField.getText().equals("")
                || yField.getText().equals(""));
    }

    //#region Abstraction
        public Location createObject() {
            return new Location();
        }

        public TableDisplay<Location> createTableDisplay(JFXTreeTableView table){
            TableDisplay<Location> tableDisplay = new LocationTableDisplay(table);

            // Query Database
            LocationDAO locationsDAO = new LocationDAO();
            List<Location> locations = locationsDAO.getAll();

            // Add Table Entries
            locations.forEach(tableDisplay::addObject);

            return tableDisplay;
        }

        public LocationDAO createDAO() {
            return new LocationDAO();
        };
    //#endregion

    //#region FXML Events
        @FXML
        void clickConfirm(ActionEvent event) {
            if (parentController.currentObj == null) addObject();
            else updateObject();
            parentController.setCurrentObj(null);
        }

        @FXML
        void onFieldUpdated() {
            confirmButton.setDisable(!checkFieldsFilled());
        }
    //#endregion
}
