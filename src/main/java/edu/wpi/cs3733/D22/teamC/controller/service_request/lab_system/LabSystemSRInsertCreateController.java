package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LabSystemSRInsertCreateController implements InsertServiceRequestCreateController<LabSystemSR>, Initializable {

    @FXML private JFXComboBox<String> labType;
    @FXML private SearchableComboBox<Patient> patientSComboBox;
    @FXML private JFXButton patientButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SVGParser svgParser = new SVGParser();
        String patientIcon = svgParser.getPath("static/icons/employee_icon.svg");
        SVGGlyph patientContent = new SVGGlyph(patientIcon);
        patientContent.setSize(20);
        patientButton.setGraphic(patientContent);
        patientButton.setText("");
        // Priority dropdown
        for (LabSystemSR.LabType labs : LabSystemSR.LabType.values()) {
            labType.getItems().add(labs.toString());
        }

        //Patient ComboBox
        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientSComboBox, Patient::toString);

        patientSComboBox.getItems().setAll(patients);
    }

    @Override
    public void clearFields() {
        labType.setValue(null);
        patientSComboBox.setValue(null);
    }

    @Override
    public LabSystemSR createServiceRequest() {
        LabSystemSR labSR = new LabSystemSR();

        labSR.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));
        labSR.setPatient(patientSComboBox.getValue());

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
        return patientSComboBox.getValue() != null;
    }

    public LabSystemSR createNewServiceRequest(){
        return new LabSystemSR();
    }

    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow(this::setPatientComboBox);
    }

    private void setPatientComboBox(Patient patientComboBox) {
        this.patientSComboBox.setValue(patientComboBox);
    }

}
