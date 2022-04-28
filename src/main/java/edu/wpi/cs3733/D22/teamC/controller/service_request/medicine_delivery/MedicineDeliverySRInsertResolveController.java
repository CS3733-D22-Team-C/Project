package edu.wpi.cs3733.D22.teamC.controller.service_request.medicine_delivery;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MedicineDeliverySRInsertResolveController extends InsertServiceRequestResolveController<MedicineDeliverySR> implements Initializable {
    @FXML private TextField dosage;
    @FXML private TextField medicine;
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

        //Query DB
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();

        ComponentWrapper.initializeComboBox(patientSComboBox, Patient::toString);

        patientSComboBox.getItems().setAll(patients);
    }

    @Override
    public void setup(BaseServiceRequestResolveController<MedicineDeliverySR> baseServiceRequestResolveController, MedicineDeliverySR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        dosage.setDisable(!isEditMode);
        medicine.setDisable(!isEditMode);
        patientSComboBox.setDisable(!isEditMode);
        patientButton.setDisable(!isEditMode);
        
        dosage.setText(serviceRequest.getDosage());
        medicine.setText(serviceRequest.getMedicine());
        patientSComboBox.setValue(serviceRequest.getPatient());
        patientSComboBox.setPromptText(serviceRequest.getPatient().toString());;
    }

    public boolean requiredFieldsPresent(){
        if(dosage.getText().equals(""))
            return false;
        if(medicine.getText().equals(""))
            return false;
        return patientSComboBox.getValue() != null;
    }

    @Override
    public void updateServiceRequest(MedicineDeliverySR serviceRequest){
        if(isEditMode){
                serviceRequest.setMedicine(medicine.getText());
                serviceRequest.setDosage(dosage.getText());
                if(patientSComboBox.getValue() != null)
                    serviceRequest.setPatient(patientSComboBox.getValue());
        }
    }

    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }

    public DAO<MedicineDeliverySR> createServiceRequestDAO() {
        return new MedicineDeliverySRDAO();
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
