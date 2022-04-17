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
        SVGGlyph confGlyph = new SVGGlyph(confContent);
        confGlyph.getStyleClass().add("conf-icon");
        confToggleNode.setGraphic(confGlyph);
        String deptContent = svgParser.getPath("static/icons/DEPT.svg");
        SVGGlyph deptGlyph = new SVGGlyph(deptContent);
        deptGlyph.getStyleClass().add("dept-icon");
        deptToggleNode.setGraphic(deptGlyph);
        String dirtContent = svgParser.getPath("static/icons/DIRT.svg");
        SVGGlyph dirtGlyph = new SVGGlyph(dirtContent);
        dirtGlyph.getStyleClass().add("dirt-icon");
        dirtToggleNode.setGraphic(dirtGlyph);
        String elevContent = svgParser.getPath("static/icons/ELEV.svg");
        SVGGlyph elevGlyph = new SVGGlyph(elevContent);
        elevGlyph.getStyleClass().add("elev-icon");
        elevToggleNode.setGraphic(elevGlyph);
        String exitContent = svgParser.getPath("static/icons/EXIT.svg");
        SVGGlyph exitGlyph = new SVGGlyph(exitContent);
        exitGlyph.getStyleClass().add("exit-icon");
        exitToggleNode.setGraphic(exitGlyph);
        String hallContent = svgParser.getPath("static/icons/HALL.svg");
        SVGGlyph hallGlyph = new SVGGlyph(hallContent);
        hallGlyph.getStyleClass().add("hall-icon");
        hallToggleNode.setGraphic(hallGlyph);
        String infoContent = svgParser.getPath("static/icons/INFO.svg");
        SVGGlyph infoGlyph = new SVGGlyph(infoContent);
        infoGlyph.getStyleClass().add("info-icon");
        infoToggleNode.setGraphic(infoGlyph);
        String labsContent = svgParser.getPath("static/icons/LABS.svg");
        SVGGlyph labsGlyph = new SVGGlyph(labsContent);
        labsGlyph.getStyleClass().add("labs-icon");
        labsToggleNode.setGraphic(labsGlyph);
        String patiContent = svgParser.getPath("static/icons/PATI.svg");
        SVGGlyph patiGlyph = new SVGGlyph(patiContent);
        patiGlyph.getStyleClass().add("pati-icon");
        patiToggleNode.setGraphic(patiGlyph);
        String restContent = svgParser.getPath("static/icons/REST.svg");
        SVGGlyph restGlyph = new SVGGlyph(restContent);
        restGlyph.getStyleClass().add("rest-icon");
        restToggleNode.setGraphic(restGlyph);
        String retlContent = svgParser.getPath("static/icons/RETL.svg");
        SVGGlyph retlGlyph = new SVGGlyph(retlContent);
        retlGlyph.getStyleClass().add("retl-icon");
        retlToggleNode.setGraphic(retlGlyph);
        String servContent = svgParser.getPath("static/icons/SERV.svg");
        SVGGlyph servGlyph = new SVGGlyph(servContent);
        servGlyph.getStyleClass().add("serv-icon");
        servToggleNode.setGraphic(servGlyph);
        String staiContent = svgParser.getPath("static/icons/STAI.svg");
        SVGGlyph staiGlyph = new SVGGlyph(staiContent);
        staiGlyph.getStyleClass().add("stai-icon");
        staiToggleNode.setGraphic(staiGlyph);
        String storContent = svgParser.getPath("static/icons/STOR.svg");
        SVGGlyph storGlyph = new SVGGlyph(storContent);
        storGlyph.getStyleClass().add("stor-icon");
        storToggleNode.setGraphic(storGlyph);
    }
}
