package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTable;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance.FacilityMaintenanceSRFormEvaluator;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medical_equipment.MedicalEquipmentSRFormEvaluator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MedicalEquipmentSRCreateController extends ServiceRequestCreateController {
    // Fields
    @FXML private TextField equipID;

    // Dropdowns
    @FXML private JFXComboBox<String> equipType;

    // For table
    @FXML private JFXTreeTableView<MedicalEquipmentSRTable> table;
    ObservableList<MedicalEquipmentSRTable> METList = FXCollections.observableArrayList();
    final TreeItem<MedicalEquipmentSRTable> root = new RecursiveTreeItem<MedicalEquipmentSRTable>(METList, RecursiveTreeObject::getChildren);

    ObservableList<MedicalEquipmentSRTable> data;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
            equipType.getItems().add(type.toString());
        }

        //For table
        MedicalEquipmentSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

        //For TextFields:
        //EquipID is being changed to a dropdown, so holding off on this.

        // Query Database
        ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAllServiceRequests();
        List<MedicalEquipmentSR> medicalEquipmentSRs = serviceRequests.stream().map(SR -> {
            return (MedicalEquipmentSR) SR;
        }).collect(Collectors.toList());
        for (MedicalEquipmentSR medicalEquipmentSR : medicalEquipmentSRs) {
            METList.add(new MedicalEquipmentSRTable(medicalEquipmentSR));
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

        MedicalEquipmentSRFormEvaluator mESRFE = new MedicalEquipmentSRFormEvaluator();

        //Even though the initialize function in ServiceRequestController sets up the assigneeID and location textfields to only read in integers, parseInt() still needs to be used on these text fields.

        ArrayList<ServiceRequestUserInputValidationErrorItem> errors = mESRFE.getMedicalEquipmentSRValidationTestResult(location.getText(), assigneeID.getText(), status.getSelectionModel(), priority.getSelectionModel(), equipType.getSelectionModel(), equipID.getText());

        if(errors.isEmpty())
        {
            MedicalEquipmentSR medEquip = (MedicalEquipmentSR) super.clickSubmit(event);
            medEquip.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

            //Set values from combo boxes:
            medEquip.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipType.getValue()));

            //Request ID generator
            int requestID = (int)(Math.random() * (10000000 + 1)) + 0;
            String requestIDString = Integer.toString(requestID);
            medEquip.setRequestID(Integer.parseInt(requestIDString));
            System.out.println(requestIDString);

            //Dealing with the equipment type and the enumerator
            int type = medEquip.getEquipmentType().ordinal();
            String num = equipID.getText();
            medEquip.setEquipmentID(type + num);
            clickReset(event);

            medEquip.setRequestType(ServiceRequest.RequestType.Medical_Equipment);

            // Table Entry
            MedicalEquipmentSRTable met = new MedicalEquipmentSRTable(medEquip);
            METList.add(met);

            // Database entry
            ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
            serviceRequestDAO.insertServiceRequest(medEquip);

            return medEquip;
        }
        else
        {
            prepareErrorMessages(errors);
            return null;
        }

    }

    @Override
    public void prepareErrorMessages(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        super.prepareErrorMessages(l);
    }

    @Override
    public boolean noUserErrors(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        return super.noUserErrors(l);
    }
}
