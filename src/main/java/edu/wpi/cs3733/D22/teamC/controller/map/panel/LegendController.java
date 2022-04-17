package edu.wpi.cs3733.D22.teamC.controller.map.panel;

import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.glyphfont.Glyph;

import java.net.URL;
import java.util.ResourceBundle;

public class LegendController implements Initializable {
    // FXML
    @FXML private MFXRectangleToggleNode confToggleNode;
    @FXML private MFXRectangleToggleNode deptToggleNode;
    @FXML private MFXRectangleToggleNode dirtToggleNode;
    @FXML private MFXRectangleToggleNode elevToggleNode;
    @FXML private MFXRectangleToggleNode exitToggleNode;
    @FXML private MFXRectangleToggleNode hallToggleNode;
    @FXML private MFXRectangleToggleNode infoToggleNode;
    @FXML private MFXRectangleToggleNode labsToggleNode;
    @FXML private MFXRectangleToggleNode patiToggleNode;
    @FXML private MFXRectangleToggleNode restToggleNode;
    @FXML private MFXRectangleToggleNode retlToggleNode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patiToggleNode.setGraphic( new Glyph("FontAwesome", "BEER"));
    }
}
