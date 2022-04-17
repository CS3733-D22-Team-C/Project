package edu.wpi.cs3733.D22.teamC.controller.service_request.translator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.translator.TranslatorSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.SearchableComboBox;

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

    public void setPatient(Patient patient){
        patientSComboBox.setValue(patient);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Language dropdown
        for (TranslatorSR.Language language : TranslatorSR.Language.values()) {
            languageSComboBox.getItems().add(language.toString());
        }
        //Patient ComboBox
        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientSComboBox, Patient::getFirstName);

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
        TSR.setPatientID(patientSComboBox.getId());
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
    public void goToPatientTable(){
        new PatientSelectorWindow(patient -> this.setPatient(patient));
    }

    @Override
    public boolean requiredFieldsPresent() {
        if(patientSComboBox.getValue() == null){
            return false;
        }
        if(languageSComboBox.getValue() == null){
            return false;
        }
        return true;
    }

}