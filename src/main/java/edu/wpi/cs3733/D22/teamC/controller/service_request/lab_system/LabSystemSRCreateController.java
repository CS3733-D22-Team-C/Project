package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

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
    protected void clickReset(ActionEvent event) {
        super.clickReset(event);

        patientID.clear();
        labType.setValue(null);
    }

    @FXML
    protected LabSystemSR clickSubmit(ActionEvent event) {
        // Check if all fields are filled
        if(labType.getSelectionModel().isEmpty() || patientID.getText().isEmpty() ||
        assigneeID.getText().isEmpty() || location.getText().isEmpty() || priority.getSelectionModel().isEmpty()
        || status.getSelectionModel().isEmpty()) return null;

        LabSystemSR labSystem = new LabSystemSR();

        //Sets from textFields
        labSystem.setAssigneeID(assigneeID.getText());
        labSystem.setDescription(description.getText());
        labSystem.setLocation(location.getText());
        labSystem.setPatientID(patientID.getText());

        //Sets from combo boxes
        labSystem.setStatus(ServiceRequest.Status.valueOf(status.getValue()));
        labSystem.setPriority(ServiceRequest.Priority.valueOf(priority.getValue()));
        labSystem.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));

        labSystem.setRequestType(ServiceRequest.RequestType.Lab_System);

        //Table Entry
        LabSystemSRTable lst = new LabSystemSRTable(labSystem);
        LSTList.add(lst);

        clickReset(event);

        return labSystem;
    }

    private boolean labSystemUserInputValidationTestPassed(int assigneeID, int locationID, int patientID, String priority, String status, String labType)
    {
        return false;
    }
}
