package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.general.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private JFXComboBox<?> equipmentID;
    @FXML private JFXComboBox<String> equipmentType;


    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        super.initialize(url, rb);
        //Querying
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(Integer.parseInt(requestID.getText()));

        //Setting fields from Querying Database
        equipmentType.setPromptText(medicalEquipmentSR.getEquipmentType().toString());
        equipmentID.setPromptText(medicalEquipmentSR.getEquipmentID());
        description.setText(medicalEquipmentSR.getDescription());
        creationTime.setText(medicalEquipmentSR.getCreationTimestamp().toString());
        System.out.println(medicalEquipmentSR.getCreationTimestamp().toString());

        if(isEditMode){
            //Sets status at bottom
            firstStatus.setText(status.getValue());

            //For equipment type drop down
            for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
                equipmentType.getItems().add(type.toString());
            }

            //For equipment ID
            //For assigneeID make editable

        }
        else {
            //Sets status at bottom
            firstStatus.setText("processing");
            secondStatus.setText("resolve");
        }


    }


    @FXML
    public void clickConfirm(ActionEvent event) {

        super.clickConfirm(event);
        //Accessing Service Request in Database
        MedicalEquipmentSRDAOImpl medicalEquipmentSRDAOImpl = new MedicalEquipmentSRDAOImpl();
        MedicalEquipmentSR medicalEquipmentSR = medicalEquipmentSRDAOImpl.getServiceRequest(Integer.parseInt(requestID.getText()));
        if(isEditMode){
            //check if value has changed
            if(equipmentID.getValue() != null)
            {
                medicalEquipmentSR.setEquipmentID(equipmentID.getValue().toString());
            }
            if(equipmentType.getValue() != null) {
                medicalEquipmentSR.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipmentType.getValue()));
            }
            medicalEquipmentSRDAOImpl.updateServiceRequest(medicalEquipmentSR);
        }
        else {
        }

        //Back to service request view
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }



}
