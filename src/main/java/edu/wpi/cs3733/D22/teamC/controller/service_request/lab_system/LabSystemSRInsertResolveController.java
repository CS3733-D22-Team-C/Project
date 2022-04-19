package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LabSystemSRInsertResolveController extends InsertServiceRequestResolveController<LabSystemSR> implements Initializable {
    @FXML
    private JFXComboBox<String> labType;

    @FXML
    private SearchableComboBox<Patient> patientSComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sanitation Type dropdown
        for (LabSystemSR.LabType labs : LabSystemSR.LabType.values()) {
            labType.getItems().add(labs.toString());
        }

        //Patient ComboBox
        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientSComboBox, Patient::getFirstName);

        patientSComboBox.getItems().setAll(patients);
    }

    @Override
    public void setup(BaseServiceRequestResolveController<LabSystemSR> baseServiceRequestResolveController, LabSystemSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        labType.setDisable(!isEditMode);
        patientSComboBox.setEditable(isEditMode);

        PatientDAO patientDAO = new PatientDAO();

        labType.setPromptText(serviceRequest.getLabType().toString());
        patientSComboBox.setValue(patientDAO.getByID(serviceRequest.getPatientID()));
    }

    public boolean requiredFieldsPresent(){
        if(labType.getValue() == null && labType.getPromptText().equals(""))
            return false;
        if(patientSComboBox.getValue() == null)
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(LabSystemSR serviceRequest){
        if(isEditMode){
            if(labType.getValue() != null)
                serviceRequest.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));
            if(patientSComboBox.getValue() != null)
                serviceRequest.setPatientID(patientSComboBox.getId());
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

    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow((patient -> this.setPatient(patient)));
    }

    private void setPatient(Patient patient){
        this.patientSComboBox.setValue(patient);
    }

}
