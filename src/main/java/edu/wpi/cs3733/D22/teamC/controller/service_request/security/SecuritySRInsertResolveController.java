package edu.wpi.cs3733.D22.teamC.controller.service_request.security;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.security.Security;
import java.util.ResourceBundle;

public class SecuritySRInsertResolveController extends InsertServiceRequestResolveController<SecuritySR> implements Initializable {

    @FXML
    private JFXComboBox<String> securityType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sanitation Type dropdown
        for (SecuritySR.SecurityType secType : SecuritySR.SecurityType.values()) {
            securityType.getItems().add(secType.toString());
        }
    }

    @Override
    public void setup(BaseServiceRequestResolveController<SecuritySR> baseServiceRequestResolveController, SecuritySR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        securityType.setDisable(!isEditMode);
        securityType.setPromptText(serviceRequest.getSecurityType().toString());
    }

    public boolean requiredFieldsPresent() {
        if (securityType.getValue() == null && securityType.getPromptText().equals(""))
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(SecuritySR serviceRequest) {
        if (isEditMode) {
            if (securityType.getValue() != null)
                serviceRequest.setSecurityType(SecuritySR.SecurityType.valueOf(securityType.getValue()));
        }
    }

    @FXML
    public void statusUpdated() {
        super.onFieldUpdated();
    }

    public ServiceRequestDAO<SecuritySR> createServiceRequestDAO() {
        return new SecuritySRDAOImpl();
    }
}
