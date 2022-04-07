package edu.wpi.cs3733.D22.teamC.controller.service_request.sanitation;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.sanitation.SanitationSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SanitationSRInsertCreateController implements InsertServiceRequestCreateController<SanitationSR>, Initializable {
    @FXML
    private JFXComboBox<String> sanitationType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (SanitationSR.SanitationType sanType : SanitationSR.SanitationType.values()) {
            sanitationType.getItems().add(sanType.toString());
        }
    }

    @Override
    public void clearFields() {
        sanitationType.valueProperty().setValue(null);
    }

    @Override
    public SanitationSR createServiceRequest() {
        SanitationSR sanitationSR = new SanitationSR();

        sanitationSR.setSanitationType(SanitationSR.SanitationType.valueOf(sanitationType.getValue()));

        return sanitationSR;
    }

    @Override
    public ServiceRequestDAO<SanitationSR> createServiceRequestDAO() {
        return new SanitationSRDAOImpl();
    }

    @Override
    public ServiceRequestTableDisplay<SanitationSR> setupTable(JFXTreeTableView<?> table) {
        return new SanitationSRTableDisplay(table);
    }
}
