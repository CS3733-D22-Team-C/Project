package edu.wpi.cs3733.D22.teamC.controller.component;

import com.jfoenix.controls.JFXTreeTableView;
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

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    private JFXTreeTableView table;

    private EmployeeTableDisplay tableDisplay;
    private BaseServiceRequestCreateController parentController;

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
    }

    @FXML
    void onSelect(ActionEvent event) {

    }
}
