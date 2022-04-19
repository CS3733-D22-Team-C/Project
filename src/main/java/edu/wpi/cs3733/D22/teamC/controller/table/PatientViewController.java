package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.MapSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.medical_equipment.MedicalEquipmentTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class PatientViewController extends InsertTableViewController<Patient> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField number;
    @FXML private TextField location;
    @FXML private JFXButton locationButton;
    Location a_location;


//    @FXML private TextField locationID;
//    @FXML private ComboBox<MedicalEquipment.EquipmentType> typeComboBox;
//    @FXML private TextField number;
//    @FXML private ComboBox<MedicalEquipment.EquipmentStatus> statusComboBox;//

    @FXML Label title;


    public void initialize(URL location, ResourceBundle resources) {
//        title.setText("Add Equipment");
//
//        //make a list of roles from the enum and put it into the combo box
//        typeComboBox.getItems().setAll(MedicalEquipment.EquipmentType.values());
//        statusComboBox.getItems().setAll(MedicalEquipment.EquipmentStatus.values());
//        confirmButton.setDisable(true);
    }

    //#region Field Interaction
    /**
     * Set values of the given object based on insert fields.
     * @param object The object to have its values overwritten.
     * @return The modified object.
     */
    public MedicalEquipment setValues(MedicalEquipment object) {
        //fields in table
//        object.setStatus(statusComboBox.getValue());
//        object.setLocationID(location.getID());
//        object.setEquipmentType(typeComboBox.getValue());
//        object.setTypeNumber(Integer.parseInt(number.getText()));
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(MedicalEquipment object) {
//        location = new LocationDAO().getByID(object.getLocationID());
//
//        title.setText((object == null) ? "Add Equipment" : "Edit Equipment");
//        locationID.setText((object == null) ? "" : location.getShortName());
//        typeComboBox.setValue((object == null) ? null : object.getEquipmentType());
//        number.setText(String.valueOf((object == null) ? "" : object.getTypeNumber()));
//        statusComboBox.setValue((object == null) ? null : object.getStatus());
//
//        confirmButton.setDisable(true);
    }
    //#endregion

    public boolean checkFieldsFilled() {
//        if (locationID.getText().equals("")) return false;
//
//        return !(typeComboBox.getValue()==null
//                || number.getText().equals("")
//                || statusComboBox.getValue()==null);
        return true;
    }

    public void setLocation(Location location) {
//        this.location = location;
//
//        String locationName = "";
//        if (location != null) {
//            locationName = location.getShortName();
//        }
//
//        locationID.setText(locationName);
    }

    //#region Abstraction
    public Patient createObject() {
        return new Patient();
    }

    public TableDisplay<Patient> createTableDisplay(JFXTreeTableView table){
        TableDisplay<Patient> tableDisplay = new PatientTableDisplay(table);

        // Query Database
        PatientDAO patientDAO = new PatientDAO();
        //ISSUE HERE
        List<Patient> patients = patientDAO.getAll();

        // Add Table Entries
        patients.forEach(tableDisplay::addObject);

        return tableDisplay;
    }

    public PatientDAO createDAO() { return new PatientDAO();}

    @Override
    public Patient setValues(Patient object) {
        return null;
    }

    @Override
    public void setFields(Patient object) {

    }

    ;
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

    @FXML
    void goToMapView() {
        new MapSelectorWindow(this::setLocation);
    }
    //#endregion
}