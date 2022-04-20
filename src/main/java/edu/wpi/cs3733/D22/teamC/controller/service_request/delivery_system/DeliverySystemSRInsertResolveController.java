package edu.wpi.cs3733.D22.teamC.controller.service_request.delivery_system;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
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

public class DeliverySystemSRInsertResolveController extends InsertServiceRequestResolveController<DeliverySystemSR> implements Initializable {
    @FXML
    private JFXComboBox<String> deliveryType;
    
    @FXML
    private SearchableComboBox<Patient> patientSComboBox;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // DeliveryType dropdown
        for (DeliverySystemSR.DeliveryType labs : DeliverySystemSR.DeliveryType.values()) {
            deliveryType.getItems().add(labs.toString());
        }
    
        PatientDAO patientDAO = new PatientDAO();
        List<Patient> patients = patientDAO.getAll();
        ComponentWrapper.initializeComboBox(patientSComboBox, Patient::toString);
        patientSComboBox.getItems().setAll(patients);
    }
    
    @Override
    public void setup(BaseServiceRequestResolveController<DeliverySystemSR> baseServiceRequestResolveController, DeliverySystemSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        deliveryType.setDisable(!isEditMode);
        patientSComboBox.setEditable(isEditMode);
        patientSComboBox.setDisable(!isEditMode);
    
        PatientDAO patientDAO = new PatientDAO();
        Patient currPatient = patientDAO.getByID(serviceRequest.getPatientID());
        
        deliveryType.setPromptText(serviceRequest.getDeliveryType().toString());
        patientSComboBox.setValue(currPatient);
        patientSComboBox.setPromptText(currPatient.toString());
    }
    
    public boolean requiredFieldsPresent(){
        if(deliveryType.getValue() == null && deliveryType.getPromptText().equals(""))
            return false;
        if(patientSComboBox.getValue() == null)
            return false;
        return true;
    }
    
    @Override
    public void updateServiceRequest(DeliverySystemSR serviceRequest){
        if(isEditMode){
            if(deliveryType.getValue() != null)
                serviceRequest.setDeliveryType(DeliverySystemSR.DeliveryType.valueOf(deliveryType.getValue()));
            if(patientSComboBox.getValue() != null)
                serviceRequest.setPatientID(patientSComboBox.getValue().getID());
        }
    }
    
    @FXML
    public void statusUpdated(){
        super.onFieldUpdated();
    }
    
    public DAO<DeliverySystemSR> createServiceRequestDAO() {
        return new DeliverySystemSRDAO();
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
