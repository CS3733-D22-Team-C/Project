package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.medical_equipment.MedicalEquipmentTableDisplay;
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

public class MedicalEquipmentViewController extends InsertTableViewController<MedicalEquipment> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private TextField location;
    @FXML private ComboBox<MedicalEquipment.EquipmentType> typeComboBox;
    @FXML private TextField number;
    @FXML private ComboBox<MedicalEquipment.EquipmentStatus> statusComboBox;//
    @FXML Label title;


    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Add Equipment");

        //make a list of roles from the enum and put it into the combo box
        typeComboBox.getItems().setAll(MedicalEquipment.EquipmentType.values());
        statusComboBox.getItems().setAll(MedicalEquipment.EquipmentStatus.values());
        confirmButton.setDisable(true);
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
        object.setLocationID(location.getText());
        object.setEquipmentType(typeComboBox.getValue());
        object.setTypeNumber(Integer.parseInt(number.getText()));
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(MedicalEquipment object) {
        title.setText((object == null) ? "Add Equipment" : "Edit Equipment");
        location.setText((object == null) ? "" : new LocationDAO().getByID(object.getLocationID()).getShortName());
        typeComboBox.setValue((object == null) ? null : object.getEquipmentType());
        number.setText(String.valueOf((object == null) ? "" : object.getTypeNumber()));
        statusComboBox.setValue((object == null) ? null : object.getStatus());

        confirmButton.setDisable(true);
    }
    //#endregion

    public boolean checkFieldsFilled() {
        return !(location.getText().equals("")
                || typeComboBox.getValue()==null
                || number.getText().equals("")
                || statusComboBox.getValue()==null);
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