package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoPaneController implements Initializable {
    // String
    private final String locationIcon = "";
    private final String medicalEquipmentIcon = "";
    private final String serviceRequestIcon = "M336 64h-53.88C268.9 26.8 233.7 0 192 0S115.1 26.8 101.9 64H48C21.5 64 0 85.48 0 112v352C0 490.5 21.5 512 48 512h288c26.5 0 48-21.48 48-48v-352C384 85.48 362.5 64 336 64zM96 392c-13.25 0-24-10.75-24-24S82.75 344 96 344s24 10.75 24 24S109.3 392 96 392zM96 296c-13.25 0-24-10.75-24-24S82.75 248 96 248S120 258.8 120 272S109.3 296 96 296zM192 64c17.67 0 32 14.33 32 32c0 17.67-14.33 32-32 32S160 113.7 160 96C160 78.33 174.3 64 192 64zM304 384h-128C167.2 384 160 376.8 160 368C160 359.2 167.2 352 176 352h128c8.801 0 16 7.199 16 16C320 376.8 312.8 384 304 384zM304 288h-128C167.2 288 160 280.8 160 272C160 263.2 167.2 256 176 256h128C312.8 256 320 263.2 320 272C320 280.8 312.8 288 304 288z";

    // Tabs
    @FXML private Tab locationTab;
    @FXML private Tab medicalEquipmentTab;
    @FXML private Tab serviceRequestTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTabSVG(locationTab, serviceRequestIcon, Color.color(0, 0, 1));
        setTabSVG(medicalEquipmentTab, serviceRequestIcon, Color.color(0, 1, 0));
        setTabSVG(serviceRequestTab, serviceRequestIcon, Color.color(1, 0, 0));
    }

    public void setTabSVG(Tab tab, String svg, Color color) {
        SVGPath icon = new SVGPath();
        icon.setContent(svg);
        icon.setFill(color);
//        Region region = new Region();
//        region.setShape(icon);
//        region.setMinSize(75, 75);
//        region.setPrefSize(75, 75);
//        region.setMaxSize(75, 75);
        tab.setGraphic(icon);
        tab.setText(null);
    }
}
