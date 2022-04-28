package edu.wpi.cs3733.D22.teamC.controller.service_request.patient_transport;

import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import io.github.palexdev.materialfx.controls.MFXButton;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
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
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PatientTransportSRInsertResolveController extends InsertServiceRequestResolveController<PatientTransportSR> implements Initializable {

    @FXML private MFXDatePicker date;
    @FXML private MFXButton patientTableButton;
    @FXML private SearchableComboBox<Patient> patientSComboBox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setEditable(false);
        //Patient ComboBox
        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();
        
        ComponentWrapper.initializeComboBox(patientSComboBox,Patient::toString);
        patientSComboBox.getItems().setAll(patients);

        LocalDate today = LocalDate.now();
        date.setCellFactory(picker -> new MFXDateCell(date, today) {
            @Override
            public void updateItem(LocalDate d) {
                super.updateItem(d);
                setDisable(d.compareTo(today) < 0);
            }
        });

        SVGParser svgParser = new SVGParser();
        String patientIcon = svgParser.getPath("static/icons/employee_icon.svg");
        SVGGlyph employeeContent = new SVGGlyph(patientIcon);
        employeeContent.setSize(20);
        patientTableButton.setGraphic(employeeContent);
    }
    
    @Override
    public void setup(BaseServiceRequestResolveController<PatientTransportSR> baseServiceRequestResolveController, PatientTransportSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        date.setEditable(false);
        patientSComboBox.setEditable(isEditMode);

        date.setDisable(!isEditMode);
        patientSComboBox.setDisable(!isEditMode);



        String dateMonth = serviceRequest.getTransportTime().toLocalDateTime().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        int dateDay = serviceRequest.getTransportTime().toLocalDateTime().getDayOfMonth();
        int dateYear = serviceRequest.getTransportTime().toLocalDateTime().getYear();

        String dateString =  dateMonth + " " + dateDay + ", " + dateYear;



        date.setText((serviceRequest == null) ? "" : dateString);
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
