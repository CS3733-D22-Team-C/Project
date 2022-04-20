package edu.wpi.cs3733.D22.teamC.controller.service_request.translator;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
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
    private SearchableComboBox<String> languageSComboBox;


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

        PatientDAO patientDAO = new PatientDAO();

        languageSComboBox.setValue(serviceRequest.getLanguage().toString());
        patientSComboBox.setValue(patientDAO.getByID(serviceRequest.getPatientID()));
        patientSComboBox.setPromptText(patientDAO.getByID(serviceRequest.getPatientID()).toString());
    }

    public boolean requiredFieldsPresent(){
        if(languageSComboBox.getValue() == null)
            return false;
        if(patientSComboBox.getValue() == null)
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(TranslatorSR serviceRequest){
        if(isEditMode){
            if(patientSComboBox.getValue() != null)
                serviceRequest.setPatientID(patientSComboBox.getValue().getID());
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
        new PatientSelectorWindow(patient -> this.setPatientComboBox(patient));
    }

    private void setPatientComboBox(Patient patientComboBox) {
        this.patientSComboBox.setValue(patientComboBox);
    }


}
