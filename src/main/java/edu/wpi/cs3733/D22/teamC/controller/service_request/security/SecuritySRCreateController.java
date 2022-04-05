package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.models.service_request.security.SecuritySRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class SecuritySRCreateController extends ServiceRequestCreateController {

    // Dropdowns
    @FXML private JFXComboBox<String> secType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        secType.getItems().add("Security Type 1");
        secType.getItems().add("Security Type 2");
        secType.getItems().add("Security Type 3");
        secType.getItems().add("Security Type 4");

        tableDisplay = new SecuritySRTableDisplay(table);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);
        secType.valueProperty().setValue(null);
    }


    @FXML
    protected SecuritySR clickSubmit(ActionEvent event) {

        if (assigneeID.getText().isEmpty() || secType.getSelectionModel().isEmpty() ||
        location.getText().isEmpty() || priority.getSelectionModel().isEmpty() ||
                status.getSelectionModel().isEmpty())
        {
            return null;
        } else
        {

            SecuritySR securitySR = new SecuritySR();

            //Sets from textFields
            securitySR.setAssigneeID(assigneeID.getText());
            securitySR.setDescription(description.getText());
            securitySR.setLocation(location.getText());

            //Sets from combo boxes
            securitySR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            securitySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(secType.getValue()));

            securitySR.setRequestType(ServiceRequest.RequestType.Security);

            // Table Entry
            clickReset(event);

            tableDisplay.addObject(securitySR);

            return securitySR;
        }
    }
}
