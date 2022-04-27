package edu.wpi.cs3733.D22.teamC.controller.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import io.github.palexdev.materialfx.controls.MFXButton;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class PatientTransportSRInsertResolveController extends InsertServiceRequestResolveController<PatientTransportSR> implements Initializable {

    @FXML private MFXDatePicker date;
    @FXML private MFXButton patientTableButton;
    @FXML private SearchableComboBox<Patient> patientSComboBox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //Patient ComboBox
        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();
        
        ComponentWrapper.initializeComboBox(patientSComboBox,Patient::toString);
        patientSComboBox.getItems().setAll(patients);
    }
    
    @Override
    public void setup(BaseServiceRequestResolveController<PatientTransportSR> baseServiceRequestResolveController, PatientTransportSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        date.setEditable(isEditMode);
        patientSComboBox.setEditable(isEditMode);

        date.setDisable(!isEditMode);
        patientSComboBox.setDisable(!isEditMode);


        date.setText((serviceRequest == null) ? "" : serviceRequest.getTransportTime().toLocalDateTime().toLocalDate().toString());
        patientSComboBox.setValue(serviceRequest.getPatient());
        patientSComboBox.setPromptText(serviceRequest.getPatient().toString());
    }

    public boolean requiredFieldsPresent(){
        if(date.getValue() == null)
            return false;
        if(patientSComboBox.getValue() == null)
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(PatientTransportSR serviceRequest) {
        if(isEditMode) {
            if(patientSComboBox.getValue() != null)
                serviceRequest.setPatient(patientSComboBox.getValue());
            if(date.getValue() != null)
                serviceRequest.setTransportTime(Timestamp.valueOf(date.getValue().atStartOfDay()));
        }
    }
    @Override
    public DAO<PatientTransportSR> createServiceRequestDAO() {
        return new PatientTransportSRDAO();
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated();
    }


    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow(patient -> this.setPatientComboBox(patient));
    }

    private void setPatientComboBox(Patient patient) {
        this.patientSComboBox.setValue(patient);
    }
}
