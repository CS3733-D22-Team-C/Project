package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance.FacilityMaintenanceSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance.FacilityMaintenanceSRFormEvaluator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRCreateController extends ServiceRequestCreateController<FacilityMaintenanceSR> {

    //Fields:
    @FXML private JFXComboBox<String> maintType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        tableDisplay = new FacilityMaintenanceSRTableDisplay(table);

        maintType.getItems().add("Cleaning");
        maintType.getItems().add("Organizing");
    }

    @FXML
    protected void clickReset(ActionEvent event) { //A JavaFX action event is a JavaFX Event, which represents some kind of action performed by the user or the program.
        super.clickReset(event);
        maintType.setValue(null);
    }

    @FXML
    protected FacilityMaintenanceSR clickSubmit(ActionEvent event) {
        resetErrorMessages();
        FacilityMaintenanceSRFormEvaluator fMSRFE = new FacilityMaintenanceSRFormEvaluator();
        ArrayList <ServiceRequestUserInputValidationErrorItem> errors = fMSRFE.getFacilityMaintenanceSRValidationTestResult(location.getText(), assigneeID.getText(), priority.getSelectionModel(), status.getSelectionModel(), maintType.getSelectionModel());

        if(fMSRFE.noServiceRequestFormUserInputErrors(errors))
        {
            FacilityMaintenanceSR fmsr = new FacilityMaintenanceSR();

            fmsr.setAssigneeID(assigneeID.getText());
            fmsr.setLocation(location.getText());
            fmsr.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            fmsr.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            fmsr.setDescription(description.getText());

            fmsr.setMaintenanceType(FacilityMaintenanceSR.MaintenanceType.valueOf(maintType.getValue()));
            fmsr.setRequestType(ServiceRequest.RequestType.Facility_Maintenance);

            // Add Table Entry
            tableDisplay.addObject(fmsr);

            clickReset(event);

            //Add database entry:

            return fmsr;
        }
        else
        {
            prepareErrorMessages(errors);
            errors.clear();
            return null; //Code will be placed here to display error messages
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
