package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request.SRErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.security.SecuritySRTableDisplay;
import edu.wpi.cs3733.D22.teamC.validation.service_request.security.SecuritySRFormEvaluator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecuritySRCreateController extends ServiceRequestCreateController <SecuritySR> {

    // Dropdowns
    @FXML
    private JFXComboBox<String> secType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down

        secType.getItems().add("Intruder");

        tableDisplay = new SecuritySRTableDisplay(table);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);
        secType.valueProperty().setValue(null);
    }


    @FXML
    protected SecuritySR clickSubmit(ActionEvent event) {
        resetErrorMessages();
        SecuritySRFormEvaluator sSRFE = new SecuritySRFormEvaluator();
        ArrayList<SRErrorItem> errors = sSRFE.getSecuritySRValidationTestResult(location.getText(), assigneeID.getText(), priority.getSelectionModel(), status.getSelectionModel(), secType.getSelectionModel());

        if(sSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            SecuritySR securitySR = new SecuritySR();

            //securitySR.setAssigneeID(assigneeID.getText());
            securitySR.setLocation(location.getText());
            securitySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            securitySR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            securitySR.setDescription(description.getText());

            //Sets from combo boxes
            securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(secType.getValue()));

        securitySR.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

        //Sets from textFields
        //securitySR.setAssigneeID(assigneeID.getText());
        securitySR.setDescription(description.getText());
        securitySR.setLocation(location.getText());

        //Sets from combo boxes
        securitySR.setStatus(ServiceRequest.Status.valueOf(status.getValue().toString()));
        securitySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue().toString()));
        securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(secType.getValue()));

        securitySR.setRequestType(ServiceRequest.RequestType.Security);

        securitySR.setRequestType(ServiceRequest.RequestType.Security);

        // Table Entry
        tableDisplay.addObject(securitySR);

        clickReset(event);

        //Database entry:
            SecuritySRDAO serviceRequestDAO = new SecuritySRDAO();
            serviceRequestDAO.insert(securitySR);

            return securitySR;
        }
        else
        {
            prepareErrorMessages(errors);
            errors.clear();
            return null;
        }
    }

    @Override
    public void prepareErrorMessages(ArrayList<SRErrorItem> l) {
        super.prepareErrorMessages(l);
    }

    @Override
    public void resetErrorMessages() {
        super.resetErrorMessages();
    }
}


