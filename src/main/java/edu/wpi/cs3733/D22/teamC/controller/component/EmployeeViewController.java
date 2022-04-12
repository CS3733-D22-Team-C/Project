package edu.wpi.cs3733.D22.teamC.controller.component;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeTableDisplay;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    private JFXTreeTableView table;

    private EmployeeTableDisplay tableDisplay;
    private BaseServiceRequestCreateController parentController;
    private Employee activeEmployee;

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

    public void setup(BaseServiceRequestCreateController parentController){
        this.parentController = parentController;
        setRowInteraction();
    }

    @FXML
    void onSelect(ActionEvent event) {

    }

    protected void setRowInteraction() {
        table.setRowFactory(tv -> {
            TreeTableRow<EmployeeTableDisplay.EmployeeTableEntry> row = new TreeTableRow<EmployeeTableDisplay.EmployeeTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    activeEmployee = (Employee) row.getItem().object;
                    System.out.println(activeEmployee.getFirstName());

                    if (event.getClickCount() == 2) {
                        // Double Click shortcut back to base page
                        parentController.setEmployee(activeEmployee);
                        App.instance.getStage().close();
                    }
                }
            });

            return row ;
        });
    }

}
