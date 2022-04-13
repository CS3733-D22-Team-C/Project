package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRInsertResolveController extends InsertServiceRequestResolveController<FacilityMaintenanceSR> implements Initializable {

    @FXML
    private JFXComboBox<String> maintType;

    @Override
    public void updateServiceRequest(FacilityMaintenanceSR serviceRequest) {
        if(isEditMode)
        {
            if(maintType.getValue() != null) {
                serviceRequest.setMaintenanceType(FacilityMaintenanceSR.MaintenanceType.valueOf(maintType.getValue()));
            }
        }
    }

    @Override
    public DAO<FacilityMaintenanceSR> createServiceRequestDAO() {
        return new FacilityMaintenanceSRDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (FacilityMaintenanceSR.MaintenanceType m : FacilityMaintenanceSR.MaintenanceType.values()) {
            maintType.getItems().add(m.toString());
        }
    }

    @Override
    public void setup(BaseServiceRequestResolveController<FacilityMaintenanceSR> baseServiceRequestResolveController, FacilityMaintenanceSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        maintType.setDisable(!isEditMode);
        maintType.setPromptText(serviceRequest.getMaintenanceType().toString());
    }

    @Override
    public boolean requiredFieldsPresent() {
        if(maintType.getValue() == null && maintType.getPromptText().equals(""))
            return false;
        return true;
    }

    @Override
    protected void onFieldUpdated() {
        super.onFieldUpdated();
    }
}
