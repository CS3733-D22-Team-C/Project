package edu.wpi.cs3733.D22.teamC.controller.service_request;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.SegmentedBar;

import javax.swing.text.Segment;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class SegmentBarController implements Initializable {

    @FXML private SegmentedBar segmentBar;

    private ObjectProperty orientation = new SimpleObjectProperty<>(Orientation.HORIZONTAL);
    private ObservableList<org.controlsfx.control.SegmentedBar.Segment> segmentObservableList =  FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resources) {
        segmentBar.orientationProperty().bind(orientation);
        segmentBar.getSegments().addAll(
                new SegmentedBar.Segment(10, "10"),
                new SegmentedBar.Segment(90, "90"),
                new SegmentedBar.Segment(20, "dd")
        );

        segmentBar.setSegmentViewFactory(segment -> {
            SegmentedBar<org.controlsfx.control.SegmentedBar.Segment>.SegmentView view = segmentBar.new SegmentView((org.controlsfx.control.SegmentedBar.Segment)segment);
            String color = "";
            if(((SegmentedBar.Segment) segment).getValue() == 90)
                 color = "#000000";
            else if (((SegmentedBar.Segment) segment).getValue() == 10)
                 color = "#00C2A5";
            else
                color = "#FFFFFF";

            view.setStyle("-fx-background-color: " + color);
            return view ;
        });

    }


}
