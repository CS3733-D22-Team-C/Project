package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LabSystemSRInsertCreateController implements InsertServiceRequestCreateController<LabSystemSR>, Initializable {
    @FXML
    private JFXComboBox<String> labType;
    @FXML
    private TextField patientID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (LabSystemSR.LabType labs : LabSystemSR.LabType.values()) {
            labType.getItems().add(labs.toString());
        }
    }

    @Override
    public void clearFields() {
        labType.valueProperty().setValue(null);
        patientID.setText(null);
    }

    @Override
    public LabSystemSR createServiceRequest() {
        LabSystemSR labSR = new LabSystemSR();

        labSR.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));
        labSR.setPatientID(patientID.getText());

        return labSR;
    }

    @Override
    public DAO<LabSystemSR> createServiceRequestDAO() {
        return new LabSystemSRDAO();
    }

    @Override
    public ServiceRequestTableDisplay<LabSystemSR> setupTable(JFXTreeTableView<?> table) {
        return new LabSystemSRTableDisplay(table);
    }

    @Override
    public boolean requiredFieldsPresent(){
        if(labType.getValue() == null)
            return false;
        if(patientID.getText().equals(""))
            return false;
        return true;
    }

    public LabSystemSR createNewServiceRequest(){
        return new LabSystemSR();
    }



}
