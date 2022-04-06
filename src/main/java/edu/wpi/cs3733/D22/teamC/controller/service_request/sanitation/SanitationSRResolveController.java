package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class SanitationSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML
    private JFXComboBox<String> sanitationType;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);

        // Querying for full Medical Equipment Service Request
        SanitationSRDAOImpl sanitationSRDAOImpl = new SanitationSRDAOImpl();
        SanitationSR sanitationSR = sanitationSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());

        // Setting fields from Querying Database
        sanitationType.setPromptText(sanitationSR.getSanitationType().toString());
        description.setText(sanitationSR.getDescription());
        creationTime.setText(sanitationSR.getCreationTimestamp().toString());

        sanitationType.setDisable(true);

        title.setText("Resolve Sanitation Request");

        if(isEditMode){
            // Equipment Type Dropdown
            for (SanitationSR.SanitationType type : SanitationSR.SanitationType.values()) {
                sanitationType.getItems().add(type.toString());
            }
            sanitationType.valueProperty().setValue(sanitationType.getPromptText());
            sanitationType.setDisable(true);
            //Status labels at bottom
            if (requiredFieldsPresent()) secondStatus.setText("processing");
            else secondStatus.setText("blank");

            sanitationType.setDisable(false);

            title.setText("Edit Sanitation Request");

        }
        System.out.println(isEditMode);
    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        super.clickConfirm(event);
        //Accessing Service Request in Database
        SanitationSRDAOImpl sanitationSRDAOImpl = new SanitationSRDAOImpl();
        SanitationSR sanitationSR = sanitationSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());
        if(isEditMode){
            //check if value has changed
            if(sanitationType.getValue() != null)
            {
                sanitationSR.setSanitationType(SanitationSR.SanitationType.valueOf(sanitationType.getValue()));
            }

            //Status
            if(requiredFieldsPresent())
                sanitationSR.setStatus(ServiceRequest.Status.Processing);
            else
                sanitationSR.setStatus(ServiceRequest.Status.Blank);
            sanitationSRDAOImpl.updateServiceRequest(sanitationSR);

        }

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }
    protected boolean requiredFieldsPresent(){
        if(sanitationType.getValue() == null && sanitationType.getPromptText().equals(""))
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
