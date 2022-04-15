package edu.wpi.cs3733.D22.teamC.controller.service_request.delivery_system;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.delivery_system.DeliverySystemSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DeliverySystemSRInsertCreateController implements InsertServiceRequestCreateController<DeliverySystemSR>, Initializable {
    @FXML
    private JFXComboBox<String> deliveryType;
    @FXML
    private TextField patientID;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // DeliveryType dropdown
        for (DeliverySystemSR.DeliveryType labs : DeliverySystemSR.DeliveryType.values()) {
            deliveryType.getItems().add(labs.toString());
        }
    }
    
    @Override
    public void clearFields() {
        deliveryType.valueProperty().setValue(null);
        patientID.setText(null);
    }
    
    @Override
    public DeliverySystemSR createServiceRequest() {
        DeliverySystemSR deliverySR = new DeliverySystemSR();
    
        deliverySR.setDeliveryType(DeliverySystemSR.DeliveryType.valueOf(deliveryType.getValue()));
        deliverySR.setPatientID(patientID.getText());
        
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
        if(patientID.getText().equals(""))
            return false;
        return true;
    }
    
    public DeliverySystemSR createNewServiceRequest() {
        return new DeliverySystemSR();
    }

}
