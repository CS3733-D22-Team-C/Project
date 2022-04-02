package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.general.ServiceRequestResolveController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRResolveController extends ServiceRequestResolveController {

    // Dropdowns
    @FXML private JFXComboBox<?> equipmentID;
    @FXML private JFXComboBox<?> equipmentType;
    @FXML private JFXComboBox<?> equipmentLocation;


    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Sets status at bottom
        firstStatus.setText("processing");
        secondStatus.setText("resolve");

        equipmentID.setPromptText("Hellow");
    }

    @FXML
    public void clickConfirm(ActionEvent event) {
        App.instance.setView(App.HOME_PATH);
    }

}
