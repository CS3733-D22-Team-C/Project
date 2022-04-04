package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        for (ServiceRequest.Priority pri : ServiceRequest.Priority.values()) {
            priority.getItems().add(pri.toString());
        }

        // Status dropdown
        for (ServiceRequest.Status sta : ServiceRequest.Status.values()) {
            status.getItems().add(sta.toString());
        }

        //Restrict certain TextFields to only have numeric input
        assigneeID.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    assigneeID.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        location.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    location.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
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
