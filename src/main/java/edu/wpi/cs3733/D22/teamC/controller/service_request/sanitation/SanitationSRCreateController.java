package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.sanitation.SanitationSRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class SanitationSRCreateController extends ServiceRequestCreateController {
    // Class specific dropdown
    @FXML
    private JFXComboBox<String> sanitationType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        // Sanitation Dropdown
        sanitationType.getItems().add("General");
        sanitationType.getItems().add("Hazardous");
        sanitationType.getItems().add("Biohazard");
        sanitationType.getItems().add("Daily Cleaning");

        tableDisplay = new SanitationSRTableDisplay(table);
    }

    @FXML
    protected void clickReset(javafx.event.ActionEvent event) {
        super.clickReset(event);

        // Clear dropdown menu
        sanitationType.valueProperty().set(null);
    }

    @FXML
    protected SanitationSR clickSubmit(ActionEvent event) {
        SanitationSR sanitationSR = new SanitationSR();

        if(assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty() || status.getSelectionModel().isEmpty() || sanitationType.getSelectionModel().isEmpty()) {
            return null;
        }
        // Start setting up a Java object for a SanitationServiceRequest
        // Text field setting
        sanitationSR.setAssigneeID(assigneeID.getText());
        sanitationSR.setLocation(location.getText());

        // Dropdown Boxes
        sanitationSR.setStatus(ServiceRequest.Status.valueOf(status.getValue().toString()));
        sanitationSR.setSanitationType(SanitationSR.SanitationType.valueOf(sanitationType.getValue()));
        sanitationSR.setPriority(ServiceRequest.Priority.valueOf(priority.getValue().toString()));

        // Sanitation type to enum
        int sanitationTypeEnum = SanitationSR.SanitationType.valueOf(sanitationType.getValue()).ordinal();

        sanitationSR.setRequestType(ServiceRequest.RequestType.Sanitation);

        clickReset(event);


        tableDisplay.addObject(sanitationSR);

        return sanitationSR;
    }
}
