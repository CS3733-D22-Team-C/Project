package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTableDisplay;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class ServiceRequestCreateController<T extends ServiceRequest> implements Initializable {
    // Fields
    @FXML protected TextField assigneeID;
    @FXML protected JFXTextArea description;
    @FXML protected TextField location;

    // Dropdowns
    @FXML protected JFXComboBox<String> priority;
    @FXML protected JFXComboBox<String> status;

    // Table
    @FXML protected JFXTreeTableView table;

    // Variables
    protected ServiceRequestTableDisplay<T> tableDisplay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Priority dropdown
        for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
            priority.getItems().add(pri.toString());
        }

        // Status dropdown
        for (ServiceRequest.Status sta : ServiceRequest.Status.values()) {
            status.getItems().add(sta.toString());
        }
    }

    @FXML
    protected void clickGoBack(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    @FXML
    protected void clickReset(ActionEvent event) {
        // Clearing Fields
        assigneeID.clear();
        location.clear();
        description.clear();

        // Clearing Dropdowns
        priority.valueProperty().set(null);
        status.valueProperty().set(null);
    }
}
