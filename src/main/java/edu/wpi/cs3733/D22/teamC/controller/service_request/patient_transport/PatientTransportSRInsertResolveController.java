package edu.wpi.cs3733.D22.teamC.controller.service_request.patient_transport;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class PatientTransportSRInsertResolveController extends InsertServiceRequestResolveController<PatientTransportSR> implements Initializable {

    @FXML private MFXDatePicker date;
    @FXML private JFXButton patientTableButton;
    @FXML private SearchableComboBox<Patient> patient;

    @Override
    public void setup(BaseServiceRequestResolveController<PatientTransportSR> baseServiceRequestResolveController, PatientTransportSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        date.setEditable(isEditMode);
        patient.setEditable(isEditMode);

        date.setDisable(!isEditMode);
        patient.setDisable(!isEditMode);

        PatientDAO patientDAO = new PatientDAO();

        date.setText(serviceRequest.getTransportTime().toString());
        patient.setValue(patientDAO.getByID(serviceRequest.getPatientID()));
    }

    public boolean requiredFieldsPresent(){
        if(date.getValue() == null)
            return false;
        if(patient.getValue() == null)
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(PatientTransportSR serviceRequest) {
        if(isEditMode) {
            if(patient.getValue() != null)
                serviceRequest.setPatientID(patient.getId());
            if(date.getValue() != null)
                serviceRequest.setTransportTime(Timestamp.valueOf(date.getValue().atStartOfDay()));
        }
    }
    @Override
    public DAO<PatientTransportSR> createServiceRequestDAO() {
        return new PatientTransportSRDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        new PatientSelectorWindow(patient -> this.setPatient(patient));
    }

    private void setPatient(Patient patient) {
        this.patient.setValue(patient);
    }
}
