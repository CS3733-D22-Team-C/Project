package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

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

public class MedicalEquipmentSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private JFXComboBox<String> equipmentID;
    @FXML private JFXComboBox<String> equipmentType;

    //For equipID dropdown
    private String lastType;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);

        //Set title
        title.setText("Resolve Medical Equipment Service Request");

        // Querying for full Medical Equipment Service Request
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(serviceRequest.getRequestID());

        // Setting fields from Querying Database
        equipmentType.setPromptText(medicalEquipmentSR.getEquipmentType().toString());
        equipmentID.setPromptText(medicalEquipmentSR.getEquipmentID());
        description.setText(medicalEquipmentSR.getDescription());
        creationTime.setText(medicalEquipmentSR.getCreationTimestamp().toString());


        if(isEditMode){
            // Equipment Type Dropdown
            for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
                equipmentType.getItems().add(type.toString());
            }
            equipmentType.valueProperty().setValue(equipmentType.getPromptText());

            //Status labels at bottom
            if (requiredFieldsPresent()) secondStatus.setText("processing");
            else secondStatus.setText("blank");

            //Set title
            title.setText("Edit Medical Equipment Service Request");
        }
        System.out.println(isEditMode);
    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        super.clickConfirm(event);
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
    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        if(isEditMode) {
            //If on the same equipment type
            if (equipmentType.getValue().equals(lastType)) {
                return;
            }
            else {
                equipmentID.setPromptText("");
                lastType = equipmentType.getValue();

                //Resetting the values
                equipmentID.valueProperty().setValue(null);
                equipmentID.getItems().clear();
                //Number of each equipment item
                int numBeds = 20;
                int numXRay = 1;
                int numInfusion = 30;
                int numRecliners = 6;

                String type = "";
                int nums = 0;

                if (lastType.equals(MedicalEquipmentSR.EquipmentType.Bed.toString())) {
                    type = "BED";
                    nums = numBeds;
                } else if (lastType.equals(MedicalEquipmentSR.EquipmentType.Recliner.toString())) {
                    type = "REC";
                    nums = numRecliners;
                } else if (lastType.equals(MedicalEquipmentSR.EquipmentType.Infusion_Pump.toString())) {
                    type = "INF";
                    nums = numInfusion;
                } else if (lastType.equals(MedicalEquipmentSR.EquipmentType.Portable_X_Ray.toString())) {
                    type = "XRA";
                    nums = numXRay;
                }

                //Adds all possible values to dropdown
                for (int i = 1; i <= nums; i++) {
                    String ID = type;
                    ID += String.format("%07d" , i);
                    equipmentID.getItems().add(ID);
                }
            }
        }


    }

    protected boolean requiredFieldsPresent(){
        if(equipmentType.getValue() == null && equipmentType.getPromptText().equals(""))
            return false;
        if(equipmentID.getValue() == null && equipmentID.getPromptText().equals(""))
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
