package edu.wpi.cs3733.D22.teamC.controller.service_request.lab_system;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LabSystemSRInsertResolveController extends InsertServiceRequestResolveController<LabSystemSR> implements Initializable {
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
        // Sanitation Type dropdown
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
    public void setup(BaseServiceRequestResolveController<LabSystemSR> baseServiceRequestResolveController, LabSystemSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        labType.setEditable(isEditMode);
        patientSComboBox.setEditable(isEditMode);

        labType.setDisable(!isEditMode);
        patientSComboBox.setEditable(isEditMode);
        
        labType.setPromptText(serviceRequest.getLabType().toString());
        patientSComboBox.setValue(serviceRequest.getPatient());
        patientSComboBox.setPromptText(serviceRequest.getPatient().toString());
    }

    public boolean requiredFieldsPresent(){
        if(labType.getValue() == null)
            return false;
        return patientSComboBox.getValue() != null;
    }

    @Override
    public void updateServiceRequest(LabSystemSR serviceRequest){
        if(isEditMode){
            if(labType.getValue() != null)
                serviceRequest.setLabType(LabSystemSR.LabType.valueOf(labType.getValue()));
            if(patientSComboBox.getValue() != null)
                serviceRequest.setPatient(patientSComboBox.getValue());
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
        new PatientSelectorWindow(this::setPatient);
    }

    private void setPatient(Patient patient){
        this.patientSComboBox.setValue(patient);
    }

}
