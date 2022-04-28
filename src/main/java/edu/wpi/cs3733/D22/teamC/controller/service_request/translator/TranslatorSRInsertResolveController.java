package edu.wpi.cs3733.D22.teamC.controller.service_request.translator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.SearchableComboBox;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TranslatorSRInsertResolveController extends InsertServiceRequestResolveController<TranslatorSR> implements Initializable {

    @FXML
    private SearchableComboBox<Patient> patientSComboBox;

    @FXML
    private JFXButton patientButton;

    @FXML
    private SearchableComboBox<String> languageSComboBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Language dropdown
        for (TranslatorSR.Language language : TranslatorSR.Language.values()) {
            languageSComboBox.getItems().add(language.toString());
        }

        SVGParser svgParser = new SVGParser();
        String patientIcon = svgParser.getPath("static/icons/employee_icon.svg");
        SVGGlyph employeeContent = new SVGGlyph(patientIcon);
        employeeContent.setSize(20);
        patientButton.setGraphic(employeeContent);

        //Patient ComboBox
        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientSComboBox,Patient::toString);
        patientSComboBox.getItems().setAll(patients);

    }

    @Override
    public void setup(BaseServiceRequestResolveController<TranslatorSR> baseServiceRequestResolveController, TranslatorSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        languageSComboBox.setEditable(isEditMode);
        patientSComboBox.setEditable(isEditMode);

        languageSComboBox.setDisable(!isEditMode);
        patientSComboBox.setDisable(!isEditMode);
        
        languageSComboBox.setValue(serviceRequest.getLanguage().toString());
        patientSComboBox.setValue(serviceRequest.getPatient());
        patientSComboBox.setPromptText(serviceRequest.getPatient().toString());
        patientButton.setDisable(!isEditMode);
    }

    public boolean requiredFieldsPresent(){
        if(languageSComboBox.getValue() == null)
            return false;
        return patientSComboBox.getValue() != null;
    }

    @Override
    public void updateServiceRequest(TranslatorSR serviceRequest){
        if(isEditMode){
            if(patientSComboBox.getValue() != null)
                serviceRequest.setPatient(patientSComboBox.getValue());
            if(languageSComboBox.getValue() != null)
                serviceRequest.setLanguage(TranslatorSR.Language.valueOf(languageSComboBox.getValue()));
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public DAO<TranslatorSR> createServiceRequestDAO() {
        return new TranslatorSRDAO();
    }

    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow(this::setPatientComboBox);
    }

    private void setPatientComboBox(Patient patientComboBox) {
        this.patientSComboBox.setValue(patientComboBox);
    }


}
