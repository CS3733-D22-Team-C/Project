package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.sanitation.SanitationSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LabSystemSRInsertCreateController implements InsertServiceRequestCreateController<LabSystemSR>, Initializable {
    @FXML
    private JFXComboBox<String> LabType;
    @FXML
    private TextField patientID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (LabSystemSR.LabType labType : LabSystemSR.LabType.values()) {
            LabType.getItems().add(labType.toString());
        }
    }

    @Override
    public void clearFields() {
        LabType.valueProperty().setValue(null);
        patientID.setText(null);
    }

    @Override
    public LabSystemSR createServiceRequest() {
        LabSystemSR labSR = new LabSystemSR();

        labSR.setLabType(LabSystemSR.LabType.valueOf(LabType.getValue()));
        labSR.setPatientID(patientID.getText());

        return labSR;
    }

    @Override
    public ServiceRequestDAO<LabSystemSR> createServiceRequestDAO() {
        return new LabSystemSRDAOImpl();
    }

    @Override
    public ServiceRequestTableDisplay<LabSystemSR> setupTable(JFXTreeTableView<?> table) {
        return new LabSystemSRTableDisplay(table);
    }



}
