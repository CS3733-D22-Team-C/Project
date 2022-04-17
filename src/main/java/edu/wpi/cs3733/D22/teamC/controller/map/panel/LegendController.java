package edu.wpi.cs3733.D22.teamC.controller.map.panel;

import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.net.URL;
import java.util.Locale;
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
    @FXML private MFXRectangleToggleNode servToggleNode;
    @FXML private MFXRectangleToggleNode staiToggleNode;
    @FXML private MFXRectangleToggleNode storToggleNode;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initToggle(confToggleNode, "conf");
        initToggle(deptToggleNode, "dept");
        initToggle(dirtToggleNode, "dirt");
        initToggle(elevToggleNode, "elev");
        initToggle(exitToggleNode, "exit");
        initToggle(hallToggleNode, "hall");
        initToggle(infoToggleNode, "info");
        initToggle(labsToggleNode, "labs");
        initToggle(patiToggleNode, "pati");
        initToggle(restToggleNode, "rest");
        initToggle(retlToggleNode, "retl");
        initToggle(servToggleNode, "serv");
        initToggle(staiToggleNode, "stai");
        initToggle(storToggleNode, "stor");
    }

    private void initToggle(MFXRectangleToggleNode toggleNode, String node) {
        SVGParser svgParser = new SVGParser();
        String content = svgParser.getPath("static/icons/" + node.toUpperCase(Locale.ROOT) + ".svg");
        SVGGlyph glyph = new SVGGlyph(content);
        glyph.getStyleClass().add(node.toLowerCase(Locale.ROOT) + "-icon");
        toggleNode.setGraphic(glyph);
        toggleNode.setSelected(true);
    }
}
