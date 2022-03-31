package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.util.ResourceBundle;

public class LabSystemSRCreateController extends ServiceRequestCreateController {

    //Fields
    @FXML private TextField patientID;


    //Dropdowns
    @FXML private JFXComboBox<String> labType;

    //For table
    @FXML private JFXTreeTableView<LabSystemSRTable> table;
    ObservableList<LabSystemSRTable> LSTList = FXCollections.observableArrayList();
    final TreeItem<LabSystemSRTable> root = new RecursiveTreeItem<LabSystemSRTable>(LSTList, RecursiveTreeObject::getChildren);

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        //For equipment type drop down
        labType.getItems().add("Blood Sample");
        labType.getItems().add("Urine Sample");
        labType.getItems().add("X-Ray");
        labType.getItems().add("CAT scans");
        labType.getItems().add("MRI");

        LabSystemSRTable.createTableColumns(table);
        table.setRoot(root);
        table.setShowRoot(false);
    }

    @FXML
    void clickReset(ActionEvent event) {
        super.clickReset(event);

        patientID.clear();
        labType.setValue(null);
    }

    @FXML
    LabSystemServiceRequest clickSubmit(ActionEvent event) {
        // Check if all fields are filled
        if(labType.getSelectionModel().isEmpty() || patientID.getText().isEmpty() ||
        assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty()
        || status.getSelectionModel().isEmpty()) return null;

        LabSystemServiceRequest labSystem = new LabSystemServiceRequest();

        //Sets from textFields
        labSystem.setAssigneeID(assigneeID.getText());
        labSystem.setDescription(description.getText());
        labSystem.setLocation(location.getText());
        labSystem.setPatientID(patientID.getText());

        //Sets from combo boxes
        labSystem.setStatus(status.getValue());
        labSystem.setPriority(priority.getValue());
        labSystem.setLabType(labType.getValue());

        //Table Entry
        LabSystemSRTable lst = new LabSystemSRTable(labSystem);
        LSTList.add(lst);

        clickReset(event);

        return labSystem;
    }


}
