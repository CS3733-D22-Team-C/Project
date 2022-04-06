package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medical_equipment.MedicalEquipmentSRFormEvaluator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
        for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
            equipType.getItems().add(type.toString());
        }

        tableDisplay = new MedicalEquipmentSRTableDisplay(table);

        //For TextFields:
        //EquipID is being changed to a dropdown, so holding off on this.

        // Query Database
        ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAllServiceRequests();
        List<MedicalEquipmentSR> medicalEquipmentSRs = serviceRequests.stream().map(SR -> {
            return (MedicalEquipmentSR) SR;
        }).collect(Collectors.toList());
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
        resetErrorMessages();
        MedicalEquipmentSRFormEvaluator mESRFE = new MedicalEquipmentSRFormEvaluator();
        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = mESRFE.getMedicalEquipmentSRValidationTestResult(location.getText(), assigneeID.getText(), priority.getSelectionModel(), status.getSelectionModel(), equipType.getSelectionModel(), equipID.getSelectionModel());

        if(mESRFE.noServiceRequestFormUserInputErrors(errors))
        {
            MedicalEquipmentSR medEquip = new MedicalEquipmentSR();

            medEquip.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

            medEquip.setAssigneeID(assigneeID.getText());
            medEquip.setLocation(location.getText());
            medEquip.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
            medEquip.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
            medEquip.setDescription(description.getText());

            //Set values from combo boxes:
            medEquip.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipType.getValue()));

            //Request ID generator
            int requestID = (int)(Math.random() * (10000000 + 1)) + 0;
            String requestIDString = Integer.toString(requestID);
            medEquip.setRequestID(Integer.parseInt(requestIDString));
            System.out.println(requestIDString);

            //Dealing with the equipment type and the enumerator
            int type =  medEquip.getEquipmentType().ordinal();
            String num = equipID.getValue();
            medEquip.setEquipmentID(type + num);

            medEquip.setRequestType(ServiceRequest.RequestType.Medical_Equipment);

            // Table Entry
            tableDisplay.addObject(medEquip);

            clickReset(event);

            // Database entry
            ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
            serviceRequestDAO.insertServiceRequest(medEquip);

            return medEquip;
        }
        else
        {
            prepareErrorMessages(errors);
            errors.clear();
            return null;
        }

    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        //If on the same equipment type
        if (equipType.getValue().equals(lastType)) {
            return;
        } else {
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

            if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Bed.toString())) {
                type = "BED";
                nums = numBeds;
            } else if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Recliner.toString())) {
                type = "REC";
                nums = numRecliners;
            } else if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Infusion_Pump.toString())) {
                type = "INF";
                nums = numInfusion;
            } else if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Portable_X_Ray.toString())) {
                type = "XRA";
                nums = numXRay;
            }

            //Adds all possible values to dropdown
            for (int i = 1; i <= nums; i++) {
                String ID = type;
                ID += String.format("%07d", i);
                equipID.getItems().add(ID);
            }
        }
    }

    @Override
    public void prepareErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.prepareErrorMessages(l);
    }

    @Override
    public void resetErrorMessages() {
        super.resetErrorMessages();
    }
}
