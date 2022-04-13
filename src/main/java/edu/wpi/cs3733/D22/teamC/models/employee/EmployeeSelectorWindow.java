package edu.wpi.cs3733.D22.teamC.models.employee;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.models.generic.SelectorWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EmployeeSelectorWindow extends SelectorWindow<Employee> implements Initializable {
    // FXML
    @FXML private JFXTreeTableView table;

    // Variables
    private EmployeeTableDisplay tableDisplay;
    private Employee activeEmployee;

    public EmployeeSelectorWindow(Consumer<Employee> consumer) {
        super(consumer);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Query Database
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employees = employeeDAO.getAll();

        // Initialize Table
        tableDisplay = new EmployeeTableDisplay(table);
        employees.forEach(tableDisplay::addObject);

        setRowInteraction();
    }

    protected void setRowInteraction() {
        table.setRowFactory(tv -> {
            TreeTableRow<EmployeeTableDisplay.EmployeeTableEntry> row = new TreeTableRow<EmployeeTableDisplay.EmployeeTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    activeEmployee = (Employee) row.getItem().object;

                    if (event.getClickCount() == 2) {
                        // Double Click shortcut back to base page
                        onSelectionMade(activeEmployee);
                    }
                }
            });

            return row ;
        });
    }

    //#region FXML Events
        @FXML
        void onSelect(ActionEvent event) {
            onSelectionMade(activeEmployee);
        }

    @Override
    protected String getView() {
        return "view/selector/employee_table.fxml";
    }
    //#endregion
}
