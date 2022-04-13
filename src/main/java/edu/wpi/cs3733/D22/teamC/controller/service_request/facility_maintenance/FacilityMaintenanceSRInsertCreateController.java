package edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance.FacilityMaintenanceSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FacilityMaintenanceSRInsertCreateController implements InsertServiceRequestCreateController<FacilityMaintenanceSR>, Initializable {

    //Fields:
    @FXML
    private JFXComboBox<String> maintType;


    @Override
    public void clearFields() {
        maintType.valueProperty().setValue(null);
    }

    @Override
    public FacilityMaintenanceSR createServiceRequest() {
        FacilityMaintenanceSR fMSR = new FacilityMaintenanceSR();
        fMSR.setMaintenanceType(FacilityMaintenanceSR.MaintenanceType.valueOf(maintType.getValue()));
        return fMSR;
    }

    @Override
    public DAO<FacilityMaintenanceSR> createServiceRequestDAO() {
        return new FacilityMaintenanceSRDAO();
    }

    public FacilityMaintenanceSR createNewServiceRequest() {
        return new FacilityMaintenanceSR();
    }

    @Override
    public ServiceRequestTableDisplay<FacilityMaintenanceSR> setupTable(JFXTreeTableView<?> table) {
        return new FacilityMaintenanceSRTableDisplay(table);
    }

    @Override
    public boolean requiredFieldsPresent() {
        if(maintType.getValue() == null)
            return false;
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (FacilityMaintenanceSR.MaintenanceType m : FacilityMaintenanceSR.MaintenanceType.values()) {
            maintType.getItems().add(m.toString());
        }
    }
}
