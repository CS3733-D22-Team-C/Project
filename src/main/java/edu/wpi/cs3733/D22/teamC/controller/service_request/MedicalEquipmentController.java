package edu.wpi.cs3733.D22.teamC.controller.service_request;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentController implements Initializable {
    @FXML private TextField AssigneID;

    @FXML private JFXTextArea Description;

    @FXML private JFXComboBox<?> Location;

    @FXML private JFXComboBox<String> Priority;

    @FXML private JFXComboBox<String> Status;

    @FXML private JFXComboBox<String> equipType;

    @FXML private JFXButton goBackButton;

    @FXML private JFXButton resetButton;

    @FXML private JFXButton submitButton;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        //For equipment type drop down
        equipType.getItems().add("Bed (20)");
        equipType.getItems().add("Recliners (6)");
        equipType.getItems().add("Portable X-Ray");
        equipType.getItems().add("Infusion Pumps (30)");

        //For priority dropdown
        Priority.getItems().add("Low");
        Priority.getItems().add("Medium");
        Priority.getItems().add("High");

        //For status dropdown
        Status.getItems().add("Blank");
        Status.getItems().add("Processing");
        Status.getItems().add("Done");

    }
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
