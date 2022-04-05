package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class MedicalEquipmentSRCreateController extends ServiceRequestCreateController<MedicalEquipmentSR> {
    // Fields
    @FXML private JFXComboBox<String> equipID;

    // Dropdowns
    @FXML private JFXComboBox<String> equipType;

    //For equipID dropdown
    private String lastType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        for (MedicalEquipment.EquipmentType type : MedicalEquipment.EquipmentType.values()) {
            equipType.getItems().add(type.toString());
        }

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

        equipID.valueProperty().setValue(null);
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
        medEquip.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
        medEquip.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));


        //Request ID generator
        int requestID = (int)(Math.random() * (10000000 + 1)) + 0;
        String requestIDString = Integer.toString(requestID);
        medEquip.setRequestID(Integer.parseInt(requestIDString));
        System.out.println(requestIDString);


        clickReset(event);

        medEquip.setRequestType(ServiceRequest.RequestType.Medical_Equipment);

        // Table Entry
        tableDisplay.addObject(medEquip);

        // Database entry
        ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        serviceRequestDAO.insertServiceRequest(medEquip);

        return medEquip;
    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        //If on the same equipment type
        if(equipType.getValue().equals(lastType))
        {
            return;
        }
        else {
            lastType = equipType.getValue();

            //Resetting the values
            equipID.valueProperty().setValue(null);
            equipID.getItems().clear();
            //Number of each equipment item
            int numBeds = 20;
            int numXRay = 1;
            int numInfusion = 30;
            int numRecliners = 6;

            String type = "";
            int nums = 0;

            if (equipType.getValue().equals(MedicalEquipment.EquipmentType.Bed.toString())) {
                type = "BED";
                nums = numBeds;
            }
            else if (equipType.getValue().equals(MedicalEquipment.EquipmentType.Recliner.toString())) {
                type = "REC";
                nums = numRecliners;
            }
            else if (equipType.getValue().equals(MedicalEquipment.EquipmentType.Infusion_Pump.toString())) {
                type = "INF";
                nums = numInfusion;
            }
            else if (equipType.getValue().equals(MedicalEquipment.EquipmentType.Portable_X_Ray.toString())) {
                type = "XRA";
                nums = numXRay;
            }

            //Adds all possible values to dropdown
            for (int i = 1; i <= nums; i++) {
                String ID = type;
                ID += String.format("%07d" , i);
                equipID.getItems().add(ID);
            }
        }

    }
}
