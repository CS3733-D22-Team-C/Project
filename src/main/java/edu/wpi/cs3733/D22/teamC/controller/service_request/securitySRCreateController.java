package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.ResourceBundle;

public class securitySRCreateController extends ServiceRequestCreateController {

    // Dropdowns
    @FXML private JFXComboBox<String> secType;

    // For table
    @FXML private JFXTreeTableView<MedicalEquipmentSRTable> table;
    ObservableList<MedicalEquipmentSRTable> METList = FXCollections.observableArrayList();
    final TreeItem<MedicalEquipmentSRTable> root = new RecursiveTreeItem<MedicalEquipmentSRTable>(METList, RecursiveTreeObject::getChildren);

    ObservableList<MedicalEquipmentSRTable> data;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        secType.getItems().add("Security Type 1");
        secType.getItems().add("Security Type 2");
        secType.getItems().add("Security Type 3");
        secType.getItems().add("Security Type 4");

        MedicalEquipmentSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);

        //Practice classes to add
//        MedicalEquipmentSRTable met1 = new MedicalEquipmentSRTable("Bed", "15", "123456",
//              "Room 202", "Done", "High");
//        MedicalEquipmentSRTable met2 = new MedicalEquipmentSRTable("Infusion Pump", "35", "392843",
//               "Room 305", "Blank", "Low");
//        METList.add(met1);
//        METList.add(met2);
    }

    @FXML
    void clickReset(ActionEvent event) {
        super.clickReset(event);
        secType.valueProperty().setValue(null);
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
        medEquip.setEquipmentType(secType.getValue());



        // Table Entry
        MedicalEquipmentSRTable met = new MedicalEquipmentSRTable(medEquip);
        METList.add(met);


        return medEquip;
    }
}
