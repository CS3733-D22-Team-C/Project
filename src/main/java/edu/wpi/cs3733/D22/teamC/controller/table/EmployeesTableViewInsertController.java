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
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

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

    private ValidationSupport validation;


    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Add Employee");

        //make a list of roles from the enum and put it into the combo box
        roleComboBox.getItems().setAll(Employee.Role.values());
        //validationfname.registerValidator(firstName, Validator.createEmptyValidator("I love batman"));
        //confirmButton.setDisable(true);
        validation = new ValidationSupport();
        validation.registerValidator(firstName, Validator.createEmptyValidator("first name required"));
        validation.registerValidator(lastName, Validator.createEmptyValidator("last name required"));
        validation.registerValidator(phone, Validator.createEmptyValidator("phone number required"));
        validation.registerValidator(roleComboBox, Validator.createEmptyValidator("role required"));
        validation.setErrorDecorationEnabled(false);
    }

    //#region Field Interaction
    /**
     * Set values of the given object based on insert fields.
     * @param object The object to have its values overwritten.
     * @return The modified object.
     */
    public Employee setValues(Employee object) {

        //Those displayed in the table
        object.setFirstName(firstName.getText());
        object.setLastName(lastName.getText());
        object.setPhone(phone.getText());
        object.setRole(roleComboBox.getValue());
        //defaults, not in table / text field
        object.setUsername(firstName.getText());
        object.setPassword("password");
        object.setAdmin(false);
        return object;
    }

    /**
     * Set values of insert fields from the given object.
     * @param object The (nullable) object to set field values from.
     */
    public void setFields(Employee object) {
        title.setText((object == null) ? "Add Employee" : "Edit Employee");
        firstName.setText((object == null) ? "" : object.getFirstName());
        lastName.setText((object == null) ? "" : object.getLastName());
        phone.setText((object == null) ? "" : object.getPhone());
        roleComboBox.setValue((object == null) ? null : object.getRole());
        //confirmButton.setDisable(true);
    }
    //#endregion

    /**
     *
     * @return
     */
    public boolean checkFieldsFilled() {

        //boolean failed = false;
        //validation = new ValidationSupport();
        validation.setErrorDecorationEnabled(true);
        return !(firstName.getText().equals("")
                || lastName.getText().equals("")
                || phone.getText().equals("")
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
        if (checkFieldsFilled()){
            if (parentController.currentObj == null) addObject();
            else updateObject();
            parentController.setCurrentObj(null);
            validation.setErrorDecorationEnabled(false);
        }
    }

//    @FXML
//    void onFieldUpdated() {
//        confirmButton.setDisable(!checkFieldsFilled());
//    }

    @FXML
    void onFieldUpdated() {
        if (!phone.getText().matches("\\d*"))
            phone.setText("");
    }
    //#endregion
}