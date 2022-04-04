package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.general.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private JFXComboBox<String> equipmentID;
    @FXML private JFXComboBox<String> equipmentType;

    //For equipID dropdown
    private String lastType ="a";

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

        if(isEditMode){
            //Sets status at bottom
            firstStatus.setText(status.getValue());

            //For equipment type drop down
            for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
                equipmentType.getItems().add(type.toString());
            }

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
                medicalEquipmentSR.setEquipmentID(equipmentID.getValue());
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

    @FXML
    void equipTypeChanged(MouseEvent event) {
        if(isEditMode) {
            //If on the same equipment type
            if (equipmentType.getValue().equals(lastType)) {
                return;
            }
            else {
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
        //Delete promptText
        equipmentID.setPromptText("");

    }



}
