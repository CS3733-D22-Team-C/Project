package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


public class PatientViewController extends InsertTableViewController<Patient> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField number;
    @FXML private TextField theLocation;
    @FXML private JFXButton locationButton;
    @FXML private DatePicker date;
    Location a_location;

    private ValidationSupport validation;


//    @FXML private TextField locationID;
//    @FXML private ComboBox<MedicalEquipment.EquipmentType> typeComboBox;
//    @FXML private TextField number;
//    @FXML private ComboBox<MedicalEquipment.EquipmentStatus> statusComboBox;//

    @FXML Label title;


    public void initialize(URL location, ResourceBundle resources) {
        validation = new ValidationSupport();
        validation.registerValidator(firstName, Validator.createEmptyValidator("first name required"));
        validation.registerValidator(lastName, Validator.createEmptyValidator("last name required"));
        validation.registerValidator(number, Validator.createEmptyValidator("number required"));
        validation.registerValidator(theLocation, Validator.createEmptyValidator("location required"));
        validation.registerValidator(date, Validator.createEmptyValidator("date required"));
        validation.setErrorDecorationEnabled(false);
        date.setEditable(false);
        theLocation.setEditable(false);
    }

    //#region Field Interaction
    /**
     * Set values of the given object based on insert fields.
     * @param object The object to have its values overwritten.
     * @return The modified object.
     */
    public Patient setValues(Patient object) {


        object.setLocationID(a_location.getID());
        object.setDOB(Timestamp.valueOf(date.getValue().atStartOfDay()));
        object.setPhone(number.getText());
        object.setFirstName(firstName.getText());
        object.setLastName(lastName.getText());
        object.setEmergencyContact(null);
        //ID set automatically
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(Patient object) {

        if (object != null){
            a_location = new LocationDAO().getByID(object.getLocationID());
        }

        title.setText((object == null) ? "Add Patient" : "Edit Patient");
        firstName.setText((object == null) ? "" : object.getFirstName());
        lastName.setText((object == null) ? "" : object.getLastName());
        number.setText((object == null) ? "" : object.getPhone());

        LocalDate lD = null;
        if (object != null){
           lD = Instant.ofEpochMilli(object.getDOB().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }

        date.setValue((object == null) ? null : lD);
        theLocation.setText((object == null) ? "" : a_location.getShortName());


//        confirmButton.setDisable(true);
    }
    //#endregion

    public boolean checkFieldsFilled() {

        validation.setErrorDecorationEnabled(true);
        //return true if all the fields are FILLED
        return !(firstName.getText().equals("")
                || lastName.getText().equals("")
                || number.getText().equals("")
                || date.getValue() == null
                || theLocation.getText().equals("")
                );
    }

    public void setLocation(Location location) {
        a_location = location;

        String locationName = "";
        if (location != null) {
            locationName = location.getShortName();
        }

        this.theLocation.setText(locationName);
        onFieldUpdated();
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
    ;
    //#endregion

    //#region FXML Events
    @FXML
    void clickConfirm(ActionEvent event) {
        if (checkFieldsFilled()){
            if (parentController.currentObj == null) addObject();
            else updateObject();
            parentController.setCurrentObj(null);
            validation.setErrorDecorationEnabled(false);
        }
    }

    @FXML
    void onFieldUpdated() {}

    @FXML
    void goToMapView() {
        new MapSelectorWindow(this::setLocation);
    }
    //#endregion
}