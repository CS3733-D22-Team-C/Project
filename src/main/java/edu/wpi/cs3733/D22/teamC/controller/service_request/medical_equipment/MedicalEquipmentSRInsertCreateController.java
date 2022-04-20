package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MedicalEquipmentSRInsertCreateController implements InsertServiceRequestCreateController<MedicalEquipmentSR>, Initializable {
    @FXML
    private JFXComboBox<MedicalEquipment.EquipmentType> equipmentType;
    @FXML
    private JFXComboBox<MedicalEquipment> equipmentID;
    private MedicalEquipment.EquipmentType lastType;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        equipmentType.getItems().addAll(MedicalEquipment.EquipmentType.values());
    }

    @Override
    public void clearFields() {
        equipmentType.setValue(null);
        equipmentID.setValue(null);
    }

    @Override
    public MedicalEquipmentSR createServiceRequest() {
        MedicalEquipmentSR MESR = new MedicalEquipmentSR();

        MESR.setEquipmentType(equipmentType.getValue());
        MESR.setEquipmentID(equipmentID.getValue().getID());
        return MESR;
    }

    @Override
    public DAO<MedicalEquipmentSR> createServiceRequestDAO() {
        return new MedicalEquipmentSRDAO();
    }

    public MedicalEquipmentSR createNewServiceRequest() {
        return new MedicalEquipmentSR();
    }

    @Override
    public ServiceRequestTableDisplay<MedicalEquipmentSR> setupTable(JFXTreeTableView<?> table) {
        return new MedicalEquipmentSRTableDisplay(table);
    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        //If on the same equipment type
        if (equipmentType.getValue().equals(lastType)) {
            return;
        } else {
            lastType = equipmentType.getValue();

            //Resetting the values
            equipmentID.setValue(null);
            equipmentID.getItems().clear();
            equipmentID.getItems().setAll(new MedicalEquipmentDAO().getAll().stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType().equals(lastType)).collect(Collectors.toList()));
        }
    }
    @Override
    public boolean requiredFieldsPresent(){
        if(equipmentID.getValue() == null)
            return false;
        if(equipmentType.getValue() == null)
            return false;
        return true;
    }
}
