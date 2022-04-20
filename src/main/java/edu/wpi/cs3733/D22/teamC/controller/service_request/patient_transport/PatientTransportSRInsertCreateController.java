package edu.wpi.cs3733.D22.teamC.controller.service_request.patient_transport;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.patient_transport.PatientTransportSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class PatientTransportSRInsertCreateController implements InsertServiceRequestCreateController<PatientTransportSR>, Initializable {

    @FXML private MFXDatePicker date;
    @FXML private JFXButton patientTableButton;
    @FXML private SearchableComboBox<Patient> patientComboBox;

    @FXML
    void onClickDate(ActionEvent event) {

    }

//    @FXML
//    void onDropDown(ActionEvent event) {
//
//    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PatientDAO patientDAO = new PatientDAO();

        List<Patient> patientList = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientComboBox,Patient::toString);

        patientComboBox.getItems().setAll(patientList);
    }

    @Override
    public void clearFields() {
        patientComboBox.setValue(null);
        date.setValue(null);
    }

    @Override
    public PatientTransportSR createServiceRequest() {
        PatientTransportSR patientTransportSR = new PatientTransportSR();

        patientTransportSR.setPatientID(patientComboBox.getValue().getID());
        patientTransportSR.setTransportTime(Timestamp.valueOf(date.getValue().atStartOfDay()));

        return patientTransportSR;
    }

    @Override
    public DAO<PatientTransportSR> createServiceRequestDAO() {
        return new PatientTransportSRDAO();
    }

    @Override
    public PatientTransportSR createNewServiceRequest() {
        return new PatientTransportSR();
    }

    @Override
    public ServiceRequestTableDisplay<PatientTransportSR> setupTable(JFXTreeTableView<?> table) {
        return new PatientTransportSRTableDisplay(table);
    }

    @Override
    public boolean requiredFieldsPresent() {
        if(patientComboBox.getValue() == null) {
            return false;
        }
        if(date.getValue() == null) {
            return false;
        }
        return true;
    }

    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow(this::setPatientComboBox);
    }

    private void setPatientComboBox(Patient patientComboBox) {
        this.patientComboBox.setValue(patientComboBox);
    }
}
