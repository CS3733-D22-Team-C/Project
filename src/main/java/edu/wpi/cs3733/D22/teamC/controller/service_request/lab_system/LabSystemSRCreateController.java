package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class LabSystemSRCreateController extends ServiceRequestCreateController<LabSystemSR> {

    //Fields
    @FXML private TextField patientID;

    //Dropdowns
    @FXML private JFXComboBox<String> labType;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        //For equipment type drop down
       for (LabSystemSR.LabType type : LabSystemSR.LabType.values()){
           labType.getItems().add(type.toString());
       }

        tableDisplay = new LabSystemSRTableDisplay(table);

       // Query Database
        LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAOImpl();
        List<LabSystemSR> labSystemSRs = labSystemSRDAO.getAllServiceRequests();
        for (LabSystemSR labSystemSR : labSystemSRs){
            tableDisplay.addObject(labSystemSR);
        }
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
        labSystem.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));

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
        tableDisplay.addObject(labSystem);

        // Database entry
        LabSystemSRDAO labSystemSRDAO = new LabSystemSRDAOImpl();
        labSystemSRDAO.insertServiceRequest(labSystem);

        clickReset(event);

        return labSystem;
    }


}
