package edu.wpi.cs3733.D22.teamC.controller.table;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeTableDisplay;
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

public class EmployeesTableViewInsertController extends InsertTableViewController<Employee> implements Initializable {
    // FXML
    @FXML protected JFXButton confirmButton;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField phone;
    @FXML private ComboBox<Employee.Role> roleComboBox;//
    @FXML Label title;

    // References
    List<Employee.Role> Roles;


    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Add Location");

        //make a list of roles from the enum and put it into the combo box
        for (Employee.Role role : Employee.Role.values()) {
            Roles.add(role);
        }
        roleComboBox.getItems().setAll(Roles);
        confirmButton.setDisable(true);
    }

    //#region Field Interaction
    /**
     * Set values of the given object based on insert fields.
     * @param object The object to have its values overwritten.
     * @return The modified object.
     */
    public Employee setValues(Employee object) {
        object.setFirstName(firstName.getText());
        object.setLastName(lastName.getText());
        object.setUsername(phone.getText());
        object.setRole(roleComboBox.getValue());
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(Employee object) {
        title.setText((object == null) ? "Add Location" : "Edit Location");
        firstName.setText((object == null) ? "" : object.getFirstName());
        lastName.setText((object == null) ? "" : object.getLastName());
        phone.setText((object == null) ? "" : object.getUsername());
        roleComboBox.setValue((object == null) ? null : object.getRole());
        confirmButton.setDisable(true);
    }
    //#endregion

    public boolean checkFieldsFilled() {
        return !(firstName.getText().equals("")
                || lastName.getText().equals("")
                || phone.getText() == ("")
                || roleComboBox.getValue()==null);
    }

    //#region Abstraction
    public Employee createObject() {
        return new Employee();
    }

    public TableDisplay<Employee> createTableDisplay(JFXTreeTableView table){
        TableDisplay<Employee> tableDisplay = new EmployeeTableDisplay(table);

        // Query Database
        EmployeeDAO employeeDAO = new EmployeeDAO();
        //ISSUE HERE
        List<Employee> employees = employeeDAO.getAll();

        // Add Table Entries
        employees.forEach(tableDisplay::addObject);

        return tableDisplay;
    }

    public EmployeeDAO createDAO() {
        return new EmployeeDAO();
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