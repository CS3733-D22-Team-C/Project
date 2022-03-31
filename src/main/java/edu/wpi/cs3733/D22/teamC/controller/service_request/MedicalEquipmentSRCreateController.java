package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
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
        equipType.getItems().add("Bed (20)");
        equipType.getItems().add("Recliners (6)");
        equipType.getItems().add("Portable X-Ray");
        equipType.getItems().add("Infusion Pumps (30)");

        MedicalEquipmentSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

        // Query Database
        ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        List<ServiceRequest> serviceRequests = serviceRequestDAO.getAllServiceRequests();
        List<MedicalEquipmentServiceRequest> medicalEquipmentSRs = serviceRequests.stream().map(SR -> {
            return (MedicalEquipmentServiceRequest) SR;
        }).collect(Collectors.toList());
        for (MedicalEquipmentServiceRequest medicalEquipmentSR : medicalEquipmentSRs) {
            METList.add(new MedicalEquipmentSRTable(medicalEquipmentSR));
        }
    }

    @FXML
    void clickReset(ActionEvent event) {
        super.clickReset(event);

        equipID.clear();
        equipType.valueProperty().setValue(null);
    }

    @FXML
    MedicalEquipmentServiceRequest clickSubmit(ActionEvent event) {
        MedicalEquipmentServiceRequest medEquip = new MedicalEquipmentServiceRequest();

        //Sets from textFields
        medEquip.setAssigneeID(assigneeID.getText());
        medEquip.setDescription(description.getText());
        medEquip.setLocation(location.getText());

        //Sets from combo boxes
        medEquip.setStatus(status.getValue());
        medEquip.setPriority(priority.getValue());
        medEquip.setEquipmentType(equipType.getValue());

        //Request ID generator
        int requestID = (int)Math.random() * (1000 + 1) + 0;
        String requestIDString = Integer.toString(requestID);
        medEquip.setRequestID(requestIDString);

        //Dealing with the equipment type and the enumerator
        int type = medEquip.getEquipEnum(equipType.getValue());
        String num = equipID.getText();
        medEquip.setEquipmentID(type + num);
        clickReset(event);

        // Table Entry
        MedicalEquipmentSRTable met = new MedicalEquipmentSRTable(medEquip);
        METList.add(met);

        // Database entry
        ServiceRequestDAO serviceRequestDAO = new MedicalEquipmentSRDAOImpl();
        serviceRequestDAO.insertServiceRequest(medEquip);

        return medEquip;
    }
}
