package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.controls.JFXTextArea;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class FloorEditController {

    // Buttons
    @FXML private MFXButton cancelButton;
    @FXML private MFXButton confirmButton;
    @FXML private MFXButton incrementButton;
    @FXML private MFXButton decrementButton;

    // Text Areas
    @FXML private JFXTextArea  description;
    @FXML private MFXTextField image;
    @FXML private MFXTextField longName;
    @FXML private MFXTextField shortName;

    public void setup(){

    }

}

