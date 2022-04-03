package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MedicalEquipmentSRCreateController extends ServiceRequestCreateController {
    // Fields
    @FXML private JFXComboBox<String> equipID;

    // Dropdowns
    @FXML private JFXComboBox<String> equipType;

    // For table
    @FXML private JFXTreeTableView<MedicalEquipmentSRTable> table;
    ObservableList<MedicalEquipmentSRTable> METList = FXCollections.observableArrayList();
    final TreeItem<MedicalEquipmentSRTable> root = new RecursiveTreeItem<MedicalEquipmentSRTable>(METList, RecursiveTreeObject::getChildren);

    ObservableList<MedicalEquipmentSRTable> data;

    //For equipID dropdown
    private String lastType;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        for (MedicalEquipmentSR.EquipmentType type : MedicalEquipmentSR.EquipmentType.values()) {
            equipType.getItems().add(type.toString());
        }

        MedicalEquipmentSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

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
        medEquip.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipType.getValue()));

        //Request ID generator
        int requestID = (int)(Math.random() * (10000000 + 1)) + 0;
        String requestIDString = Integer.toString(requestID);
        medEquip.setRequestID(Integer.parseInt(requestIDString));
        System.out.println(requestIDString);

        //Dealing with the equipment type and the enumerator
        int type = medEquip.getEquipmentType().ordinal();
        String num = equipID.getValue();
        medEquip.setEquipmentID(num);
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

            if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Bed.toString())) {
                type = "BED";
                nums = numBeds;
            }
            else if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Recliner.toString())) {
                type = "REC";
                nums = numRecliners;
            }
            else if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Infusion_Pump.toString())) {
                type = "INF";
                nums = numInfusion;
            }
            else if (equipType.getValue().equals(MedicalEquipmentSR.EquipmentType.Portable_X_Ray.toString())) {
                type = "XRA";
                nums = numXRay;
            }

            //Adds all possible values to dropdown
            for (int i = 1; i <= nums; i++) {
                String ID = type;
                int digits = (int) (Math.log10(i) + 1);
                for (int j = 0; j < 7 - digits; j++) {
                    ID += "0";
                }
                ID += i;
                equipID.getItems().add(ID);
            }
        }

    }
}
