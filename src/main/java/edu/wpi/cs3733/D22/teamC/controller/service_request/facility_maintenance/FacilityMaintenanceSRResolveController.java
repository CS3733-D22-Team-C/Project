package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRResolveController extends ServiceRequestResolveController {

    //Dropdowns:
    @FXML private JFXComboBox<String> maintType;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);
        //Need the FacilityMaintenanceDAOImpl to complete this function

        /*
        // Querying for full Medical Equipment Service Request
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());

        // Setting fields from Querying Database
        equipmentType.setPromptText(medicalEquipmentSR.getEquipmentType().toString());
        equipmentID.setPromptText(medicalEquipmentSR.getEquipmentID());
        description.setText(medicalEquipmentSR.getDescription());
        creationTime.setText(medicalEquipmentSR.getCreationTimestamp().toString());

        equipmentID.setDisable(true);
        equipmentType.setDisable(true);
         */

        if(isEditMode){
            // Equipment Type Dropdown
            for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
                maintType.getItems().add(type.toString());
            }
            maintType.valueProperty().setValue(maintType.getPromptText());

            //Status labels at bottom
            if (requiredFieldsPresent()) secondStatus.setText("processing");
            else secondStatus.setText("blank");

            maintType.setDisable(false);
        }
        System.out.println(isEditMode);
    }

    @Override
    public void clickConfirm(ActionEvent event) {
        //Need access to the FacilityMaintenanceDAOImpl to complete this function
        super.clickConfirm(event);

        /*
        //Accessing Service Request in Database
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());
        if(isEditMode){
            //check if value has changed
            if(equipmentID.getValue() != null)
            {
                medicalEquipmentSR.setEquipmentID(equipmentID.getValue());
            }
            if(equipmentType.getValue() != null) {
                medicalEquipmentSR.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipmentType.getValue()));
            }

            System.out.println(requiredFieldsPresent());
            //Status
            if(requiredFieldsPresent())
                medicalEquipmentSR.setStatus(ServiceRequest.Status.Processing);
            else
                medicalEquipmentSR.setStatus(ServiceRequest.Status.Blank);
            medicalEquipmentSRDAOImpl.updateServiceRequest(medicalEquipmentSR);

        }

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
         */
    }

    @Override
    protected boolean requiredFieldsPresent() {
        if(maintType.getValue() == null && maintType.getPromptText().equals(""))
            return false;
        return super.requiredFieldsPresent();
    }

    @FXML
    void statusUpdated(ActionEvent event)
    {
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
