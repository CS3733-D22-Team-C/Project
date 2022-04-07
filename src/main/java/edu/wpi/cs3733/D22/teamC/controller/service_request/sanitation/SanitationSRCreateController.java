package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.sanitation.SanitationSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance.FacilityMaintenanceSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.sanitation.SanitationSRFormEvaluator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class SanitationSRCreateController extends ServiceRequestCreateController<SanitationSR>{
    // Class specific dropdown
    @FXML
    private JFXComboBox<String> sanitationType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @FXML
    protected void clickReset(javafx.event.ActionEvent event) {
        super.clickReset(event);
    }

    @FXML
    protected SanitationSR clickSubmit(ActionEvent event) {
        resetErrorMessages();
        SanitationSRFormEvaluator sSRFE = new SanitationSRFormEvaluator();
        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = sSRFE.getSanitationSRValidationTestResult(location.getText(), assigneeID.getText(), priority.getSelectionModel(), status.getSelectionModel(), sanitationType.getSelectionModel());

        if(sSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            SanitationSR sanitationSR = new SanitationSR();

        sanitationSR.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
        if(assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty() || status.getSelectionModel().isEmpty() || sanitationType.getSelectionModel().isEmpty()) {
            return null;
        }
        // Start setting up a Java object for a SanitationServiceRequest
        // Text field setting
        sanitationSR.setAssigneeID(assigneeID.getText());
        sanitationSR.setLocation(location.getText());
        sanitationSR.setDescription(description.getText());

            // Start setting up a Java object for a SanitationServiceRequest
            sanitationSR.setAssigneeID(assigneeID.getText());
            sanitationSR.setLocation(location.getText());
            sanitationSR.setStatus(ServiceRequest.Status.valueOf(status.getValue().toString()));
            sanitationSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue().toString()));
            sanitationSR.setDescription(description.getText());

            // Dropdown Boxes
            sanitationSR.setSanitationType(SanitationSR.SanitationType.valueOf(sanitationType.getValue()));

            // Sanitation type to enum
            int sanitationTypeEnum = SanitationSR.SanitationType.valueOf(sanitationType.getValue()).ordinal();

            sanitationSR.setRequestType(ServiceRequest.RequestType.Sanitation);

            tableDisplay.addObject(sanitationSR);

            clickReset(event);

            // Database entry
            ServiceRequestDAO serviceRequestDAO = new SanitationSRDAOImpl();
            serviceRequestDAO.insertServiceRequest(sanitationSR);

            return sanitationSR;
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
