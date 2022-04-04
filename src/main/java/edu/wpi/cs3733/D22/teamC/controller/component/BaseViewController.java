package edu.wpi.cs3733.D22.teamC.controller.component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseViewController implements Initializable {
    @FXML
    BorderPane baseView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        baseView.setMaxWidth(Double.MAX_VALUE);
        baseView.setMaxHeight(Double.MAX_VALUE);
    }
}
