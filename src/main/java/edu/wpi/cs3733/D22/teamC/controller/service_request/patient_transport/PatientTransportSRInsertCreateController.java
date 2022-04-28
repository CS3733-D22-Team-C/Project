package edu.wpi.cs3733.D22.teamC.controller.service_request.patient_transport;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.patient_transport.PatientTransportSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import net.bytebuddy.asm.Advice;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
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

        date.setStartingYearMonth(YearMonth.now());
        date.setEditable(false);

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
    public void clearFields() {
        patientComboBox.setValue(null);
        date.setValue(null);
    }

    @Override
    public PatientTransportSR createServiceRequest() {
        PatientTransportSR patientTransportSR = new PatientTransportSR();

        patientTransportSR.setPatient(patientComboBox.getValue());
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
        return date.getValue() != null;
    }

    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow(patient -> this.setPatientComboBox(patient));
    }

    private void setPatientComboBox(Patient patientComboBox) {
        this.patientComboBox.setValue(patientComboBox);
    }
}
