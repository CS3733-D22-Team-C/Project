package edu.wpi.cs3733.D22.teamC.controller.service_request.translator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.translator.TranslatorSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TranslatorSRInsertCreateController implements InsertServiceRequestCreateController<TranslatorSR>, Initializable {


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
        
        //Patient ComboBox
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();
        ComponentWrapper.initializeComboBox(patientSComboBox,Patient::toString);
        patientSComboBox.getItems().setAll(patients);
    }


    @Override
    public void clearFields() {
        languageSComboBox.valueProperty().setValue(null);
        patientSComboBox.valueProperty().setValue(null);
    }

    @Override
    public TranslatorSR createServiceRequest() {
        TranslatorSR TSR = new TranslatorSR();
        TSR.setLanguage(TranslatorSR.Language.valueOf(languageSComboBox.getValue()));
        TSR.setPatient(patientSComboBox.getValue());
        return TSR;
    }

    @Override
    public DAO<TranslatorSR> createServiceRequestDAO() {
        return new TranslatorSRDAO();
    }

    @Override
    public TranslatorSR createNewServiceRequest() {
        return new TranslatorSR();
    }

    @Override
    public ServiceRequestTableDisplay<TranslatorSR> setupTable(JFXTreeTableView<?> table) {
        return new TranslatorSRTableDisplay(table);
    }

    @FXML
    void goToPatientTable(ActionEvent event) throws IOException {
        new PatientSelectorWindow(this::setPatientComboBox);
    }

    private void setPatientComboBox(Patient patientComboBox) {
        this.patientSComboBox.setValue(patientComboBox);
    }

    @Override
    public boolean requiredFieldsPresent() {
        if(patientSComboBox.getValue() == null){
            return false;
        }
        return languageSComboBox.getValue() != null;
    }

}