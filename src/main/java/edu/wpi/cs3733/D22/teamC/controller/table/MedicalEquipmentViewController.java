package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.MapSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.medical_equipment.MedicalEquipmentTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MedicalEquipmentViewController extends InsertTableViewController<MedicalEquipment> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private TextField locationID;
    @FXML private ComboBox<MedicalEquipment.EquipmentType> typeComboBox;
    @FXML private TextField number;
    @FXML private ComboBox<MedicalEquipment.EquipmentStatus> statusComboBox;//
    @FXML Label title;

    @FXML JFXButton mapViewButton;

    Location location;
    private ValidationSupport validation;

    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Add Equipment");
        locationID.setEditable(false);

        //make a list of roles from the enum and put it into the combo box
        typeComboBox.getItems().setAll(MedicalEquipment.EquipmentType.values());
        statusComboBox.getItems().setAll(MedicalEquipment.EquipmentStatus.values());

        validation = new ValidationSupport();
        validation.registerValidator(locationID, Validator.createEmptyValidator("location required"));
        validation.registerValidator(typeComboBox, Validator.createEmptyValidator("type required"));
        validation.registerValidator(number, Validator.createEmptyValidator("number required"));
        validation.registerValidator(statusComboBox, Validator.createEmptyValidator("status required"));
        validation.setErrorDecorationEnabled(false);
    }

    //#region Field Interaction
    /**
     * Set values of the given object based on insert fields.
     * @param object The object to have its values overwritten.
     * @return The modified object.
     */
    public MedicalEquipment setValues(MedicalEquipment object) {
        //fields in table
        object.setStatus(statusComboBox.getValue());
        object.setLocationID(location.getID());
        object.setEquipmentType(typeComboBox.getValue());
        object.setTypeNumber(Integer.parseInt(number.getText()));
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(MedicalEquipment object) {
        location = (object == null)? null : new LocationDAO().getByID(object.getLocationID());

        title.setText((object == null) ? "Add Equipment" : "Edit Equipment");
        locationID.setText((object == null) ? "" : location.getShortName());
        typeComboBox.setValue((object == null) ? null : object.getEquipmentType());
        number.setText(String.valueOf((object == null) ? "" : object.getTypeNumber()));
        statusComboBox.setValue((object == null) ? null : object.getStatus());

    }
    //#endregion

    public boolean checkFieldsFilled() {

        validation.setErrorDecorationEnabled(true);
        if (locationID.getText().equals("")) return false;
        return !(typeComboBox.getValue()==null
                || number.getText().equals("")
                || statusComboBox.getValue()==null);
    }

    public void setLocation(Location location) {
        this.location = location;

        String locationName = "";
        if (location != null) {
            locationName = location.getShortName();
        }

        locationID.setText(locationName);
    }

    //#region Abstraction
    public MedicalEquipment createObject() {
        return new MedicalEquipment();
    }

    public TableDisplay<MedicalEquipment> createTableDisplay(JFXTreeTableView table){
        TableDisplay<MedicalEquipment> tableDisplay = new MedicalEquipmentTableDisplay(table);

        // Query Database
        MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
        //ISSUE HERE
        List<MedicalEquipment> medicalEquipments = medicalEquipmentDAO.getAll();

        // Add Table Entries
        medicalEquipments.forEach(tableDisplay::addObject);

        return tableDisplay;
    }

    public MedicalEquipmentDAO createDAO() {
        return new MedicalEquipmentDAO();
    };
    //#endregion

    //#region FXML Events
        @FXML
        void clickConfirm(ActionEvent event) {
            if (checkFieldsFilled()) {
                if (parentController.currentObj == null) addObject();
                else updateObject();
                parentController.setCurrentObj(null);
                validation.setErrorDecorationEnabled(false);
            }
        }

        @FXML
        void onFieldUpdated() {
            if (!number.getText().matches("\\d*"))
                number.setText("");
        }

        @FXML
        void goToMapView() {
            new MapSelectorWindow(this::setLocation);
        }
    //#endregion
}