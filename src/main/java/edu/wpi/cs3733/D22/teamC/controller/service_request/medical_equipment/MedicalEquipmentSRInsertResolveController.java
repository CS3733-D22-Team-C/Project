package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRInsertResolveController extends InsertServiceRequestResolveController<MedicalEquipmentSR> implements Initializable {

    @FXML
    private JFXComboBox<String> equipmentType;
    @FXML
    private JFXComboBox<String> equipmentID;

    private String lastType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (MedicalEquipmentSR.EquipmentType medType : MedicalEquipmentSR.EquipmentType.values()) {
            equipmentType.getItems().add(medType.toString());
        }


    }

    @Override
    public void setup(BaseServiceRequestResolveController<MedicalEquipmentSR> baseServiceRequestResolveController, MedicalEquipmentSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        equipmentType.setDisable(!isEditMode);
        equipmentID.setDisable(!isEditMode);
        equipmentType.setPromptText(serviceRequest.getEquipmentType().toString());
        equipmentID.setPromptText(serviceRequest.getEquipmentID());
        createEquipIDs(true);
    }

    public boolean requiredFieldsPresent(){
        if(equipmentType.getValue() == null && equipmentType.getPromptText().equals(""))
            return false;
        if(equipmentID.getValue() == null && equipmentID.getPromptText().equals(""))
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(MedicalEquipmentSR serviceRequest){
        if(isEditMode){
            if(equipmentID.getValue() != null)
                serviceRequest.setEquipmentID(equipmentID.getValue());
            if(equipmentType.getValue() != null)
                serviceRequest.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipmentType.getValue()));
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public ServiceRequestDAO<MedicalEquipmentSR> createServiceRequestDAO() {
        return new MedicalEquipmentSRDAOImpl();
    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        if(isEditMode) {
            //If on the same equipment type
            if (equipmentType.getValue().equals(lastType)) {
                return;
            }
            else {
                createEquipIDs(false);
            }

        }


    }

    private void createEquipIDs(boolean first){
        if(first){
            lastType = equipmentType.getPromptText();
        }
        else {
            lastType = equipmentType.getValue();
        }

            equipmentID.setPromptText("");

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
                ID += String.format("%07d", i);
                equipmentID.getItems().add(ID);
            }
    }

}


