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
        SVGParser svgParser = new SVGParser();

        String confContent = svgParser.getPath("static/icons/CONF.svg");
        confToggleNode.setGraphic(new SVGGlyph(confContent));
        String deptContent = svgParser.getPath("static/icons/DEPT.svg");
        deptToggleNode.setGraphic(new SVGGlyph(deptContent));
        String dirtContent = svgParser.getPath("static/icons/DIRT.svg");
        dirtToggleNode.setGraphic(new SVGGlyph(dirtContent));
        String elevContent = svgParser.getPath("static/icons/ELEV.svg");
        elevToggleNode.setGraphic(new SVGGlyph(elevContent));
        String exitContent = svgParser.getPath("static/icons/EXIT.svg");
        exitToggleNode.setGraphic(new SVGGlyph(exitContent));
        String hallContent = svgParser.getPath("static/icons/HALL.svg");
        hallToggleNode.setGraphic(new SVGGlyph(hallContent));
        String infoContent = svgParser.getPath("static/icons/INFO.svg");
        infoToggleNode.setGraphic(new SVGGlyph(infoContent));
        String labsContent = svgParser.getPath("static/icons/LABS.svg");
        labsToggleNode.setGraphic(new SVGGlyph(labsContent));
        String patiContent = svgParser.getPath("static/icons/PATI.svg");
        patiToggleNode.setGraphic(new SVGGlyph(patiContent));
        String restContent = svgParser.getPath("static/icons/REST.svg");
        restToggleNode.setGraphic(new SVGGlyph(restContent));
        String retlContent = svgParser.getPath("static/icons/RETL.svg");
        retlToggleNode.setGraphic(new SVGGlyph(retlContent));
        String servContent = svgParser.getPath("static/icons/SERV.svg");
        servToggleNode.setGraphic(new SVGGlyph(servContent));
        String staiContent = svgParser.getPath("static/icons/STAI.svg");
        staiToggleNode.setGraphic(new SVGGlyph(staiContent));
        String storContent = svgParser.getPath("static/icons/STOR.svg");
        storToggleNode.setGraphic(new SVGGlyph(storContent));
    }
}
