package edu.wpi.cs3733.D22.teamC.controller.dashboard;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController<T extends ServiceRequest> implements Initializable {

    @FXML
    JFXTreeTableView assignedTable;
    @FXML
    JFXTreeTableView createdTable;

    private DashboardAssignedTableDisplay assignedTableDisplay;
    private DashboardCreatedTableDisplay createdTableDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assignedTableDisplay = new DashboardAssignedTableDisplay(assignedTable);
        createdTableDisplay = new DashboardCreatedTableDisplay(createdTable);

        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAll();
        serviceRequests.forEach(assignedTableDisplay::addObject);
        serviceRequests.forEach(createdTableDisplay::addObject);

    }


}
