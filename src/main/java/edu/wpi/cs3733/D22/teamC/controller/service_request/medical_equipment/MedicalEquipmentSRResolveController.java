package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MedicalEquipmentSRResolveController {

    // Dropdowns
    @FXML private JFXComboBox<?> assigneeID;
    @FXML private JFXComboBox<?> equipmentID;
    @FXML private JFXComboBox<?> equipmentType;
    @FXML private JFXComboBox<?> equipmentLocation;
    @FXML private JFXComboBox<?> priority;
    @FXML private JFXComboBox<?> status;

    //Buttons
    @FXML private JFXButton confirmButton;
    @FXML private JFXButton goBackButton;

    // Labels
        // DB Labels
    @FXML private Label createdBy;
    @FXML private Label creationTime;
    @FXML private Label lastUpdated;
    @FXML private Label updatedBy;
    @FXML private Label requestID;
        // Status Labels
    @FXML private Label firstStatus;
    @FXML private Label secondStatus;

    // Description
    @FXML private JFXTextArea description;

    @FXML
    void clickConfirm(ActionEvent event) {

    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(App.HOME_PATH);
    }

}
