package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LabSystemSRInsertResolveController extends InsertServiceRequestResolveController<LabSystemSR> implements Initializable {
    @FXML
    private JFXComboBox<String> labType;

    @FXML
    private TextField patientID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sanitation Type dropdown
        for (LabSystemSR.LabType labs : LabSystemSR.LabType.values()) {
            labType.getItems().add(labs.toString());
        }
    }

    @Override
    public void setup(BaseServiceRequestResolveController<LabSystemSR> baseServiceRequestResolveController, LabSystemSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        labType.setDisable(!isEditMode);
        patientID.setEditable(isEditMode);

        labType.setPromptText(serviceRequest.getLabType().toString());
        patientID.setText(serviceRequest.getPatientID());
    }

    public boolean requiredFieldsPresent(){
        if(labType.getValue() == null && labType.getPromptText().equals(""))
            return false;
        if(patientID.getText().equals(""))
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(LabSystemSR serviceRequest){
        if(isEditMode){
            if(labType.getValue() != null)
                serviceRequest.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));
            serviceRequest.setPatientID(patientID.getText());
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public DAO<LabSystemSR> createServiceRequestDAO() {
        return new LabSystemSRDAO();
    }


    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated();
    }

}
