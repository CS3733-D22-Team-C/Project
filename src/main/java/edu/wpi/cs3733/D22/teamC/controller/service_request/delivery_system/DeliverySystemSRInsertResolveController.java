package edu.wpi.cs3733.D22.teamC.controller.service_request.delivery_system;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DeliverySystemSRInsertResolveController extends InsertServiceRequestResolveController<DeliverySystemSR> implements Initializable {
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
    public void setup(BaseServiceRequestResolveController<DeliverySystemSR> baseServiceRequestResolveController, DeliverySystemSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        deliveryType.setDisable(!isEditMode);
        patientID.setEditable(isEditMode);
    
        deliveryType.setPromptText(serviceRequest.getDeliveryType().toString());
        patientID.setText(serviceRequest.getPatientID());
    }
    
    public boolean requiredFieldsPresent(){
        if(deliveryType.getValue() == null && deliveryType.getPromptText().equals(""))
            return false;
        return !patientID.getText().equals("");
    }
    
    @Override
    public void updateServiceRequest(DeliverySystemSR serviceRequest){
        if(isEditMode){
            if(deliveryType.getValue() != null)
                serviceRequest.setDeliveryType(DeliverySystemSR.DeliveryType.valueOf(deliveryType.getValue()));
            serviceRequest.setPatientID(patientID.getText());
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
    
}
