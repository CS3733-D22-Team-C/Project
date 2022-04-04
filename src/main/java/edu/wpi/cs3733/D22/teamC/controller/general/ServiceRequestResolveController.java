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
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ServiceRequestResolveController implements Initializable {

    protected boolean isEditMode;
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
        hospitalLocation.setPromptText(srt.getLocation());

        assigneeID.setText(srt.getAssigneeID());
        assigneeID.setEditable(false);

        //requestID with leading 0's
        requestID.setText(String.format("%07d" , srt.getID()));


        if(isEditMode){
            //Set title
            title.setText("Edit Medical Equipment Request");
            // Priority dropdown
            for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
                priority.getItems().add(pri);
            }

            //location
            hospitalLocation.getItems().add("DEPT000001");
            hospitalLocation.getItems().add("DEPT000002");
            hospitalLocation.getItems().add("DEPT000003");
            hospitalLocation.getItems().add("DEPT000004");
            hospitalLocation.getItems().add("DEPT000005");
            hospitalLocation.getItems().add("DEPT000006");
            hospitalLocation.getItems().add("DEPT000007");
            hospitalLocation.getItems().add("DEPT000008");
            hospitalLocation.getItems().add("DEPT000009");

            //Sets status at bottom
            firstStatus.setText(srt.getStatus().toString().toLowerCase(Locale.ROOT));

            assigneeID.setEditable(true);

        }
        else
        {
            //Sets status at bottom
            firstStatus.setText("processing");
            secondStatus.setText("resolve");
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
            if(!hospitalLocation.getValue().equals(""))
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
