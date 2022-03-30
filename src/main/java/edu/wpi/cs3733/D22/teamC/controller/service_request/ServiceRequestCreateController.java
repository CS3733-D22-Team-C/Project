package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceRequestCreateController implements Initializable {
    // Fields
    @FXML protected TextField assigneeID;
    @FXML protected JFXTextArea description;
    @FXML protected TextField location;

    // Dropdowns
    @FXML protected JFXComboBox<String> priority;
    @FXML protected JFXComboBox<String> status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Priority dropdown
        priority.getItems().add("Low");
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        // Status dropdown
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");
    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.BASE_VIEW_PATH);
    }

    @FXML
    void clickReset(ActionEvent event) {
        // Clearing Fields
        assigneeID.clear();
        location.clear();
        description.clear();

        // Clearing Dropdowns
        priority.valueProperty().set(null);
        status.valueProperty().set(null);
    }
}
