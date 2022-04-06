package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.security.SecuritySRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation.SanitationSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.security.SecuritySRFormEvaluator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SecuritySRCreateController extends ServiceRequestCreateController <SecuritySR> {

    // Dropdowns
    @FXML private JFXComboBox<String> secType;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        secType.getItems().add("intruder");

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
        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = sSRFE.getSecuritySRValidationTestResult(location.getText(), assigneeID.getText(), priority.getSelectionModel(), status.getSelectionModel(), secType.getSelectionModel());

        if(sSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            SecuritySR securitySR = new SecuritySR();

            securitySR.setAssigneeID(assigneeID.getText());
            securitySR.setLocation(location.getText());
            securitySR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            securitySR.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            securitySR.setDescription(description.getText());

            //Sets from combo boxes
            securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(secType.getValue()));

            securitySR.setRequestType(ServiceRequest.RequestType.Security);

            // Table Entry
            clickReset(event);
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
    public void prepareErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.prepareErrorMessages(l);
    }

    @Override
    public void resetErrorMessages() {
        super.resetErrorMessages();
    }
}
