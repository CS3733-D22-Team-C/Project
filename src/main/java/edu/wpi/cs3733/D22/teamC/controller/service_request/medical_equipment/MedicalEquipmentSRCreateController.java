package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class MedicalEquipmentSRCreateController extends ServiceRequestCreateController<MedicalEquipmentSR> {
    // Fields
    @FXML private TextField equipID;

    // Dropdowns
    @FXML private JFXComboBox<String> equipType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        equipType.getItems().add("Bed (20)");
        equipType.getItems().add("Recliners (6)");
        equipType.getItems().add("Portable X-Ray");
        equipType.getItems().add("Infusion Pumps (30)");

        tableDisplay = new MedicalEquipmentSRTableDisplay(table);

        // Query Database
        MedicalEquipmentSRDAO medicalEquipmentSRDAO = new MedicalEquipmentSRDAOImpl();
        List<MedicalEquipmentSR> medicalEquipmentSRs = medicalEquipmentSRDAO.getAllServiceRequests();
        for (MedicalEquipmentSR medicalEquipmentSR : medicalEquipmentSRs) {
            tableDisplay.addObject(medicalEquipmentSR);
        }
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);

        equipID.clear();
        equipType.valueProperty().setValue(null);
    }

    @FXML
    protected MedicalEquipmentSR clickSubmit(ActionEvent event) {
        MedicalEquipmentSR medEquip = new MedicalEquipmentSR();

        medEquip.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

        //Sets from textFields
        medEquip.setAssigneeID(assigneeID.getText());
        medEquip.setDescription(description.getText());
        medEquip.setLocation(location.getText());

        //Sets from combo boxes
        medEquip.setStatus(status.getValue());
        medEquip.setPriority(priority.getValue());
        medEquip.setEquipmentType(equipType.getValue());

        //Request ID generator
        int requestID = (int)(Math.random() * (10000000 + 1)) + 0;
        String requestIDString = Integer.toString(requestID);
        medEquip.setRequestID(requestIDString);
        System.out.println(requestIDString);

        //Dealing with the equipment type and the enumerator
        int type = medEquip.getEquipEnum(equipType.getValue());
        String num = equipID.getText();
        medEquip.setEquipmentID(type + num);
        clickReset(event);

        // Table Entry
        tableDisplay.addObject(medEquip);

        // Database entry
        ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        serviceRequestDAO.insertServiceRequest(medEquip);

        return medEquip;
    }
}
