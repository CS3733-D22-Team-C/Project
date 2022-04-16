package edu.wpi.cs3733.D22.teamC.controller.service_request.delivery_system;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.delivery_system.DeliverySystemSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.utils.ComponentWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.SearchableComboBox;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class DeliverySystemSRInsertCreateController implements InsertServiceRequestCreateController<DeliverySystemSR>, Initializable {
    @FXML
    private JFXComboBox<String> deliveryType;
    
    @FXML
    private SearchableComboBox<Patient> patientComboBox;
    
    @FXML
    private JFXButton patientTableButton;
    
    @FXML
    public void goToPatientTable(ActionEvent event) {
        new PatientSelectorWindow(patient -> this.setPatientComboBox(patient));
    }
    
    public void setPatientComboBox(Patient patientComboBox) {
        this.patientComboBox.setValue(patientComboBox);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // DeliveryType dropdown
        for (DeliverySystemSR.DeliveryType labs : DeliverySystemSR.DeliveryType.values()) {
            deliveryType.getItems().add(labs.toString());
        }
        patientComboBox.getItems().setAll(new PatientDAO().getAll());
    }
    
    @Override
    public void clearFields() {
        deliveryType.valueProperty().setValue(null);
        patientComboBox.setValue(null);
    }
    
    @Override
    public DeliverySystemSR createServiceRequest() {
        DeliverySystemSR deliverySR = new DeliverySystemSR();
    
        deliverySR.setDeliveryType(DeliverySystemSR.DeliveryType.valueOf(deliveryType.getValue()));
        deliverySR.setPatientID(patientComboBox.getValue().getID());
        
        return deliverySR;
    }
    
    @Override
    public DAO<DeliverySystemSR> createServiceRequestDAO() {
        return new DeliverySystemSRDAO();
    }
    
    @Override
    public ServiceRequestTableDisplay<DeliverySystemSR> setupTable(JFXTreeTableView<?> table) {
        return new DeliverySystemSRTableDisplay(table);
    }
    
    @Override
    public boolean requiredFieldsPresent(){
        if(deliveryType.getValue() == null)
            return false;
        return patientComboBox.getValue() != null;
    }
    
    public DeliverySystemSR createNewServiceRequest() {
        return new DeliverySystemSR();
    }

}