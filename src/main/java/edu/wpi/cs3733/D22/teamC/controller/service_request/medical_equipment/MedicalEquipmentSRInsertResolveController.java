package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MedicalEquipmentSRInsertResolveController extends InsertServiceRequestResolveController<MedicalEquipmentSR> implements Initializable {

    @FXML
    private JFXComboBox<MedicalEquipment.EquipmentType> equipmentType;
    @FXML
    private JFXComboBox<MedicalEquipment> equipment;

    private MedicalEquipment.EquipmentType lastType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        equipmentType.getItems().addAll(MedicalEquipment.EquipmentType.values());
    }

    @Override
    public void setup(BaseServiceRequestResolveController<MedicalEquipmentSR> baseServiceRequestResolveController, MedicalEquipmentSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        equipmentType.setDisable(!isEditMode);
        equipment.setDisable(!isEditMode);
        ComponentWrapper.setValueSilently(equipmentType, serviceRequest.getEquipmentType());
        createEquipIDs();
        ComponentWrapper.setValueSilently(equipment, new MedicalEquipmentDAO().getByID(serviceRequest.getEquipment().getID()));
    }

    public boolean requiredFieldsPresent(){
        if(equipmentType.getValue() == null)
            return false;
        if(equipment.getValue() == null)
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(MedicalEquipmentSR serviceRequest){
        if(isEditMode){
            if(equipment.getValue() != null)
                serviceRequest.setEquipment(equipment.getValue());
            if(equipmentType.getValue() != null)
                serviceRequest.setEquipmentType(equipmentType.getValue());
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public DAO<MedicalEquipmentSR> createServiceRequestDAO() {
        return new MedicalEquipmentSRDAO();
    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        if(isEditMode) {
            //If on the same equipment type
            if (equipmentType.getValue().equals(lastType)) {
                return;
            }
            else {
                createEquipIDs();
            }

        }


    }

    private void createEquipIDs(){
        lastType = equipmentType.getValue();

        //Resetting the values
        equipment.setValue(null);
        equipment.getItems().clear();
        equipment.getItems().setAll(new MedicalEquipmentDAO().getAll().stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType().equals(lastType)).collect(Collectors.toList()));
    }

}


