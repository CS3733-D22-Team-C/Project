package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestSingleton;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ServiceRequestResolveController {
    // Dropdowns
    @FXML protected JFXComboBox<ServiceRequest.Priority> priority;
    @FXML protected JFXComboBox<String> status;
    @FXML protected TextField assigneeID;
    @FXML private JFXComboBox<String> hospitalLocation;

    // Labels
    @FXML private Label title;
        // DB Labels
    @FXML protected Label createdBy;
    @FXML protected Label creationTime;
    @FXML protected Label lastUpdated;
    @FXML protected Label updatedBy;
    @FXML protected Label requestID;
        // Status Labels
    @FXML protected Label firstStatus;
    @FXML protected Label secondStatus;

    // Description
    @FXML protected JFXTextArea description;

    //Buttons
    @FXML protected JFXButton confirmButton;
    @FXML protected JFXButton goBackButton;

    // Variables
    protected boolean isEditMode;
    protected ServiceRequest serviceRequest;

    @FXML
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        this.serviceRequest = serviceRequest;

        // Set initial values
        priority.setPromptText(serviceRequest.getPriority().toString());
        status.setPromptText(serviceRequest.getStatus().toString());
        hospitalLocation.setPromptText(serviceRequest.getLocation());
        assigneeID.setText(serviceRequest.getAssigneeID());

        // Set labels
        requestID.setText(String.format("%07d" , serviceRequest.getRequestID()));

        if (isEditMode) {
            // Set generic title (overridden in children)
            title.setText("Edit Medical Equipment Request");

            // Priority Dropdown
            for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
                priority.getItems().add(pri);
            }

            // Location
            // TODO: Change from hardcoding !!!
            hospitalLocation.getItems().add("DEPT000001");
            hospitalLocation.getItems().add("DEPT000002");
            hospitalLocation.getItems().add("DEPT000003");
            hospitalLocation.getItems().add("DEPT000004");
            hospitalLocation.getItems().add("DEPT000005");
            hospitalLocation.getItems().add("DEPT000006");
            hospitalLocation.getItems().add("DEPT000007");
            hospitalLocation.getItems().add("DEPT000008");
            hospitalLocation.getItems().add("DEPT000009");

            // Sets status at bottom
            firstStatus.setText(serviceRequest.getStatus().toString().toLowerCase());

            // Set fields editable
            assigneeID.setEditable(true);
        } else {
            // Set generic title (overridden in children)
            title.setText("Resolve Service Request");

            // Sets status at bottom
            firstStatus.setText("processing");
            secondStatus.setText("resolve");

            // Set fields uneditable
            assigneeID.setEditable(false);
        }
    }

    @FXML
    public void clickConfirm(ActionEvent event) {
        //Accessing Service Request in Database
        ServiceRequestDAOImpl serviceRequestDAOImpl = new ServiceRequestDAOImpl();
        ServiceRequest serviceRequest = serviceRequestDAOImpl.getServiceRequest(Integer.parseInt(requestID.getText()));

        if(isEditMode)
        {
            if(priority.getValue() != null) {
                serviceRequest.setPriority(priority.getValue());
            }
            //Assignee ID
            if(!assigneeID.getText().equals("")){
                serviceRequest.setAssigneeID(assigneeID.getText());
            }
            //Location
            if(hospitalLocation.getValue() != null)
            {
               serviceRequest.setLocation(hospitalLocation.getValue());
            }
            //Status
        }
        else {
            //Set status to Done
            serviceRequest.setStatus(ServiceRequest.Status.Done);
        }
        serviceRequest.setDescription(description.getText());
        serviceRequestDAOImpl.updateServiceRequest(serviceRequest);

    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    protected boolean requiredFieldsPresent(){
        if (priority.getValue() == null && priority.getPromptText().equals(""))
            return false;
        if (assigneeID.getText().equals(""))
            return false;
        if (hospitalLocation.getValue() == null && hospitalLocation.getPromptText().equals(""))
            return false;
        return true;
    }
}
