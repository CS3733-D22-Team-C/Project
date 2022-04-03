package edu.wpi.cs3733.D22.teamC.controller.general;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTable;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceRequestResolveController implements Initializable {

    // Dropdowns
    @FXML protected JFXComboBox<?> priority;
    @FXML protected JFXComboBox<?> status;
    @FXML protected JFXComboBox<?> assigneeID;
    @FXML private JFXComboBox<?> hospitalLocation;


    // Labels
        // DB Labels
    @FXML protected Label createdBy;
    @FXML protected Label creationTime;
    @FXML protected Label lastUpdated;
    @FXML protected Label updatedBy;
    @FXML protected Label requestID;
        // Status Labels
    @FXML protected Label firstStatus;
    @FXML protected Label secondStatus;

    // Description
    @FXML protected JFXTextArea description;

    //Buttons
    @FXML protected JFXButton confirmButton;
    @FXML protected JFXButton goBackButton;


    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Get data from row
        ServiceRequestTable srt = receiveData();

        //Setting fields from ServiceRequestTable
        priority.setPromptText(srt.getPriority());
        status.setPromptText(srt.getStatus());
        assigneeID.setPromptText(srt.getAssigneeID());
        hospitalLocation.setPromptText(srt.getLocation());
        requestID.setText(String.valueOf(srt.getID()));
    }


    @FXML
    public void clickConfirm(ActionEvent event) {

    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
    }

    protected ServiceRequestTable receiveData() {
        ServiceRequestSingleton holder = ServiceRequestSingleton.INSTANCE;
        ServiceRequestTable u = holder.getServiceRequestTable();
        String p = u.getPriority();
        System.out.println(p);
        return u;

    }


}
