package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MedicalEquipmentController {
    @FXML private TextField AssigneID;

    @FXML private JFXTextArea Description;

    @FXML private JFXComboBox<?> Location;

    @FXML private JFXComboBox<?> Priority;

    @FXML private JFXComboBox<?> Status;

    @FXML private JFXComboBox<?> equipType;

    @FXML private JFXButton goBackButton;

    @FXML private JFXButton resetButton;

    @FXML private JFXButton submitButton;

    @FXML
    void clickGoBack(ActionEvent event) {

    }

    @FXML
    void clickReset(ActionEvent event) {

    }

    @FXML
    void clickSubmit(ActionEvent event) {

    }

}
