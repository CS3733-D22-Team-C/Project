package edu.wpi.cs3733.D22.teamC.controller.map.panel;

import com.jfoenix.svg.SVGGlyph;
import edu.wpi.cs3733.D22.teamC.controller.map.MapController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.fileio.svg.SVGParser;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import org.controlsfx.control.PopOver;
import org.controlsfx.glyphfont.Glyph;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    // Variables
    private MFXRectangleToggleNode[] toggleNodes = new MFXRectangleToggleNode[Location.NodeType.values().length];

    // References
    private MapController mapController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Symbols
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

        // Initialize Toggle Node List
        toggleNodes = new MFXRectangleToggleNode[] {
            patiToggleNode,
            storToggleNode,
            dirtToggleNode,
            hallToggleNode,
            elevToggleNode,
            restToggleNode,
            staiToggleNode,
            deptToggleNode,
            labsToggleNode,
            infoToggleNode,
            confToggleNode,
            exitToggleNode,
            retlToggleNode,
            servToggleNode
        };
    }

    public void setup(MapController mapController) {
        this.mapController = mapController;
    }

    private void initToggle(MFXRectangleToggleNode toggleNode, String node) {
        SVGParser svgParser = new SVGParser();
        String content = svgParser.getPath("static/icons/" + node.toUpperCase(Locale.ROOT) + ".svg");
        SVGGlyph glyph = new SVGGlyph(content);
        glyph.getStyleClass().add(node.toLowerCase(Locale.ROOT) + "-icon");
        toggleNode.setGraphic(glyph);
        toggleNode.setSelected(true);
    }

    //#region FXML Events
        @FXML
        private void onToggleNodePressed(ActionEvent event) {
            boolean[] filteredNodeTypes = new boolean[Location.NodeType.values().length];
            for (int i = 0; i < filteredNodeTypes.length; i++){
                filteredNodeTypes[i] = toggleNodes[i].isSelected();
            }

            mapController.getMapViewController().getLocationManager().changeFilters(filteredNodeTypes);
        }
    //#endregion
}
