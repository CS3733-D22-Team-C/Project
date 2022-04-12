package edu.wpi.cs3733.D22.teamC.models.employee;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
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

public class EmployeeSelectorWindow implements Initializable {

    @FXML
    private JFXTreeTableView table;

    private EmployeeTableDisplay tableDisplay;
    private ServiceRequestController parentController;
    private Employee activeEmployee;
    private Stage primaryStage;

    @FXML
    private Label title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Select an Employee");
        //Columns for table
        tableDisplay = new EmployeeTableDisplay(table);

        // Query Database
        System.out.println("38");
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> employees = employeeDAO.getAll();
        System.out.println("41");

        for (Employee employee : employees) {
            System.out.println(employee.getFirstName());
            tableDisplay.addObject(employee);
        }

    }

    public void setup(ServiceRequestController parentController, Stage primaryStage){
        this.parentController = parentController;
        this.primaryStage = primaryStage;
        setRowInteraction();
    }


    @FXML
    void onSelect(ActionEvent event) {
        parentController.setEmployee(activeEmployee);
        primaryStage.close();

    }

    protected void setRowInteraction() {
        table.setRowFactory(tv -> {
            TreeTableRow<EmployeeTableDisplay.EmployeeTableEntry> row = new TreeTableRow<EmployeeTableDisplay.EmployeeTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    activeEmployee = (Employee) row.getItem().object;

                    if (event.getClickCount() == 2) {
                        // Double Click shortcut back to base page
                        parentController.setEmployee(activeEmployee);
                        primaryStage.close();
                    }
                }
            });

            return row ;
        });
    }
}
