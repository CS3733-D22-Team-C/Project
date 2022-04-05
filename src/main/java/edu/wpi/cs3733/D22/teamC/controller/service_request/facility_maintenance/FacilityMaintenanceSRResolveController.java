package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.ServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRResolveController extends ServiceRequestResolveController {

    //Dropdowns:
    @FXML private JFXComboBox<String> maintType;

    @Override
    public void setup(ServiceRequest serviceRequest, boolean isEditMode) {
        super.setup(serviceRequest, isEditMode);
        //Need the FacilityMaintenanceDAOImpl to complete this function
    }

    @Override
    public void clickConfirm(ActionEvent event) {
        //Need access to the FacilityMaintenanceDAOImpl to complete this function
        super.clickConfirm(event);
    }

    @FXML
    void maintTypeChanged(MouseEvent mouseEvent) {

    }

    @Override
    protected boolean requiredFieldsPresent() {
        if(maintType.getValue() == null && maintType.getPromptText().equals(""))
            return false;
        return super.requiredFieldsPresent();
    }

    @FXML
    void statusUpdated(ActionEvent event)
    {
        //Only in edit mode
        if(!isEditMode)
            return;
        if(requiredFieldsPresent()){
            secondStatus.setText("processing");
        }
        else
            secondStatus.setText("blank");
    }

    //Just for textfields
    //on key pressed (in scenebuilder)
    @FXML
    void statusUpdatedKeyEvent(KeyEvent event) {
        statusUpdated(null);
    }
}
