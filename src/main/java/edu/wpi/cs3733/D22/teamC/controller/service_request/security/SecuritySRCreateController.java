package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.security.SecuritySRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class SecuritySRCreateController extends ServiceRequestCreateController {

    // Dropdowns
    @FXML
    private JFXComboBox<String> secType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        secType.getItems().add("Intruder");
//        secType.getItems().add("Security Type 2");
//        secType.getItems().add("Security Type 3");
//        secType.getItems().add("Security Type 4");

        tableDisplay = new SecuritySRTableDisplay(table);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);
        secType.valueProperty().setValue(null);
    }


    @FXML
    protected SecuritySR clickSubmit(ActionEvent event) {

        SecuritySR securitySR = new SecuritySR();

        securitySR.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

        //Sets from textFields
        securitySR.setAssigneeID(assigneeID.getText());
        securitySR.setDescription(description.getText());
        securitySR.setLocation(location.getText());

        //Sets from combo boxes
        securitySR.setStatus(ServiceRequest.Status.valueOf(status.getValue().toString()));
        securitySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue().toString()));
        securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(secType.getValue()));

        securitySR.setRequestType(ServiceRequest.RequestType.Security);

        securitySR.setRequestType(ServiceRequest.RequestType.Security);
        tableDisplay.addObject(securitySR);


        ServiceRequestDAO serviceRequestDAO = new SecuritySRDAOImpl();
        serviceRequestDAO.insertServiceRequest(securitySR);

        // Table Entry
        clickReset(event);
        return securitySR;
    }
}


