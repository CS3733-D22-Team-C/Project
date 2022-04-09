package edu.wpi.cs3733.D22.teamC.controller.location.map;

import com.jfoenix.svg.SVGGlyph;
import javafx.css.Styleable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

import java.awt.image.BufferedImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoPaneController implements Initializable {
    // String
    private final String locationIcon = "location-icon";
    private final String medicalEquipmentIcon = "medical-equipment-icon";
    private final String serviceRequestIcon = "service-equipment-icon";

    // Tabs
    @FXML private Tab locationTab;
    @FXML private Tab medicalEquipmentTab;
    @FXML private Tab serviceRequestTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTabIcon(locationTab, locationIcon);
        setTabIcon(medicalEquipmentTab, medicalEquipmentIcon);
        setTabIcon(serviceRequestTab, serviceRequestIcon);
    }

    public void setTabIcon(Tab tab, String styleClass) {
        SVGGlyph svgGlyph = new SVGGlyph();
        svgGlyph.getStyleClass().add(styleClass);
        svgGlyph.applyCss();
        tab.setGraphic(svgGlyph);
        tab.setText(null);
    }
}
