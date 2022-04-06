package edu.wpi.cs3733.D22.teamC.controller.Employee;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.Employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.Employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.Employee.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.Employee.EmployeeTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    private JFXTreeTableView table;

    private EmployeeTableDisplay tableDisplay;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Constrain column sizes to the size of the table

        //Columns for table
        tableDisplay = new EmployeeTableDisplay(table);

        // Query Database
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        List<Employee> employees = employeeDAO.getAllEmployees();

        for(Employee employee : employees){
            tableDisplay.addObject(employee);
        }
    }
}
