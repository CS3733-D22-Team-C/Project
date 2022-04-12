package edu.wpi.cs3733.D22.teamC.controller.component;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeViewController implements Initializable {

    @FXML
    private JFXTreeTableView<Employee> table;

    @FXML
    private Label title;

    private EmployeeTableDisplay<Employee> tableDisplay;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onSelect(ActionEvent event) {

    }
}
