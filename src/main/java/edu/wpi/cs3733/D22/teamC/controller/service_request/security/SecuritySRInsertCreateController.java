package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SecuritySRInsertCreateController implements InsertServiceRequestCreateController<SecuritySR>, Initializable {
    @FXML
    private JFXComboBox<String> securityType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (SecuritySR.SecurityType secType : SecuritySR.SecurityType.values()) {
            securityType.getItems().add(secType.toString());
        }
    }

    @Override
    public void clearFields() {
        securityType.valueProperty().setValue(null);
    }

    @Override
    public SecuritySR createServiceRequest() {
        SecuritySR sanitationSR = new SecuritySR();

        sanitationSR.setSecurityType(SecuritySR.SecurityType.valueOf(securityType.getValue()));

        return sanitationSR;
    }

    @Override
    public DAO<SecuritySR> createServiceRequestDAO() {
        return new SecuritySRDAO();
    }

    @Override
    public ServiceRequestTableDisplay<SecuritySR> setupTable(JFXTreeTableView<?> table) {
        return new ServiceRequestTableDisplay(table);
    }
    @Override
    public boolean requiredFieldsPresent(){
        if(securityType.getValue() == null)
            return false;
        return true;
    }


    public SecuritySR createNewServiceRequest(){
        return new SecuritySR();
    }
}
