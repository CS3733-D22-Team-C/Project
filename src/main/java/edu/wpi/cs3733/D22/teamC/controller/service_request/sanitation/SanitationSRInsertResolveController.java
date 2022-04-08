package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SanitationSRInsertResolveController extends InsertServiceRequestResolveController<SanitationSR> implements Initializable {


    @FXML
    private JFXComboBox<String> sanitationType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Sanitation Type dropdown
        for (SanitationSR.SanitationType sanType : SanitationSR.SanitationType.values()) {
            sanitationType.getItems().add(sanType.toString());
        }
    }

    @Override
    public void setup(BaseServiceRequestResolveController<SanitationSR> baseServiceRequestResolveController, SanitationSR serviceRequest, boolean isEditMode) {
        super.setup(baseServiceRequestResolveController, serviceRequest, isEditMode);
        sanitationType.setDisable(!isEditMode);
        sanitationType.setPromptText(serviceRequest.getSanitationType().toString());
    }

    public boolean requiredFieldsPresent() {
        if (sanitationType.getValue() == null && sanitationType.getPromptText().equals(""))
            return false;
        return true;
    }

    @Override
    public void updateServiceRequest(SanitationSR serviceRequest) {
        if (isEditMode) {
            if (sanitationType.getValue() != null)
                serviceRequest.setSanitationType(SanitationSR.SanitationType.valueOf(sanitationType.getValue()));
        }
    }

    @FXML
    public void statusUpdated() {
        super.onFieldUpdated();
    }

    public DAO<SanitationSR> createServiceRequestDAO() {
        return new SanitationSRDAO();
    }
}
