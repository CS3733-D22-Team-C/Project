package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SecuritySRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private JFXComboBox<String> securityType;

    //For equipID dropdown
    private String lastType;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);

        // Querying for full Medical Equipment Service Request
//        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
//        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());
        SecuritySRDAOImpl securitySRDAOImpl = new SecuritySRDAOImpl();
        SecuritySR securitySR = securitySRDAOImpl.getServiceRequest(serviceRequest.getRequestID());
        title.setText("Resolve Security Service Request");



        // Setting fields from Querying Database
        securityType.setPromptText(securitySR.getSecurityType().toString());
        description.setText(securitySR.getDescription());
        creationTime.setText(securitySR.getCreationTimestamp().toString());


        if(isEditMode){
            title.setText("Edit Security Service Request");
            // Equipment Type Dropdown
            for (SecuritySR.SecurityType type : SecuritySR.SecurityType.values()) {
                securityType.getItems().add(type.toString());
            }
            securityType.valueProperty().setValue(securityType.getPromptText());

            //Status labels at bottom
            if (requiredFieldsPresent()) secondStatus.setText("processing");
            else secondStatus.setText("blank");
        }
    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        super.clickConfirm(event);
        //Accessing Service Request in Database

        SecuritySRDAOImpl securitySRDAOImpl = new SecuritySRDAOImpl();
        SecuritySR securitySR = securitySRDAOImpl.getServiceRequest(serviceRequest.getRequestID());
        if(isEditMode){
            //check if value has changed
            if(securityType.getValue() != null) {
                securitySR.setSecurityType(SecuritySR.SecurityType.valueOf(securityType.getValue()));
            }

            System.out.println(requiredFieldsPresent());
            //Status
            if(requiredFieldsPresent())
                securitySR.setStatus(ServiceRequest.Status.Processing);
            else
                securitySR.setStatus(ServiceRequest.Status.Blank);
            securitySRDAOImpl.updateServiceRequest(securitySR);

        }

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    protected boolean requiredFieldsPresent(){
        if(securityType.getValue() == null && securityType.getPromptText().equals(""))
            return false;
        return super.requiredFieldsPresent();
    }

    //On action (in scenebuilder)
    @FXML
    void statusUpdated(ActionEvent event) {
        //Only in edit mode
        if(!isEditMode)
            return;
        if(requiredFieldsPresent()){
            secondStatus.setText("processing");
        }
        else
            secondStatus.setText("blank");
    }

    //Just for textfields
    //on key pressed (in scenebuilder)
    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated(null);
    }



}
