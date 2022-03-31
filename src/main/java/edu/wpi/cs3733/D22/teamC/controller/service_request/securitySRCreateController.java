package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecurityServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.securitySRTable;
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
    @FXML private JFXTreeTableView<securitySRTable> table;
    ObservableList<securitySRTable> METList = FXCollections.observableArrayList();
    final TreeItem<securitySRTable> root = new RecursiveTreeItem<securitySRTable>(METList, RecursiveTreeObject::getChildren);

    ObservableList<securitySRTable> data;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);

        //For equipment type drop down
        secType.getItems().add("Security Type 1");
        secType.getItems().add("Security Type 2");
        secType.getItems().add("Security Type 3");
        secType.getItems().add("Security Type 4");

        securitySRTable.createTableColumns(table);
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
    SecurityServiceRequest clickSubmit(ActionEvent event) {
        SecurityServiceRequest securityServiceRequest = new SecurityServiceRequest();

        //Sets from textFields
        securityServiceRequest.setAssigneeID(assigneeID.getText());
        //securityServiceRequest.setDescription(description.getText());
        securityServiceRequest.setLocation(location.getText());

        //Sets from combo boxes
        securityServiceRequest.setStatus(status.getValue());
        securityServiceRequest.setPriority(priority.getValue());
        securityServiceRequest.setSecurityType(secType.getValue());

        // Table Entry
        clickReset(event);
        securitySRTable met = new securitySRTable(securityServiceRequest);
        METList.add(met);
        return securityServiceRequest;

    }
}
