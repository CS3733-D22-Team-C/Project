package edu.wpi.cs3733.D22.teamC.controller.general;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTable;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceRequestResolveController implements Initializable {

    protected boolean isEditMode;
    // Dropdowns
    @FXML protected JFXComboBox<ServiceRequest.Priority> priority;
    @FXML protected JFXComboBox<String> status;
    @FXML protected JFXComboBox<?> assigneeID;
    @FXML private JFXComboBox<?> hospitalLocation;


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


    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Get data from row
        ServiceRequestTable srt = receiveData();

        //Check if is in edit mode
        isEditMode = receiveIsEditMode();

        //Set title
        title.setText("Resolve Medical Equipment Request");
        //Setting fields from ServiceRequestTable
        priority.setPromptText(srt.getPriority());
        status.setPromptText(srt.getStatus());
        assigneeID.setPromptText(srt.getAssigneeID());
        hospitalLocation.setPromptText(srt.getLocation());

        //requestID with leading 0's
        requestID.setText(String.format("%07d" , srt.getID()));


        if(isEditMode){
            //Set title
            title.setText("Edit Medical Equipment Request");
            // Priority dropdown
            for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
                priority.getItems().add(pri);
            }

            //Other dropdowns

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
            //Assignee Id
            //Location
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

    protected ServiceRequestTable receiveData() {
        ServiceRequestSingleton holder = ServiceRequestSingleton.INSTANCE;
        ServiceRequestTable u = holder.getServiceRequestTable();
        String p = u.getPriority();
        System.out.println(p);
        return u;
    }

    //If is in edit mode
    protected boolean receiveIsEditMode(){
        ServiceRequestSingleton holder = ServiceRequestSingleton.INSTANCE;
        return holder.getIsEditMode();
    }


}
