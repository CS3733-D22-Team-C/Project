package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
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
    int done;
    int processing;
    int blank;

    private ObjectProperty orientation = new SimpleObjectProperty<>(Orientation.HORIZONTAL);
    private ObservableList<org.controlsfx.control.SegmentedBar.Segment> segmentObservableList =  FXCollections.observableArrayList();
    SegmentedBar.Segment blankSegment;
    SegmentedBar.Segment processingSegment;
    SegmentedBar.Segment doneSegment;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
    }

    public void preSetup(){
        done = 0;
        processing = 0;
        blank = 0;
        doneSegment = new SegmentedBar.Segment(0, "Done: ");
        processingSegment = new SegmentedBar.Segment(0, "Processing: ");
        blankSegment = new SegmentedBar.Segment(0, "Blank: ");

    }

    public void updateNumbers(ServiceRequest.Status status, boolean isAdd){
        if(isAdd) {
            if (status == ServiceRequest.Status.Blank)
                blank ++;
            if (status == ServiceRequest.Status.Processing)
                processing ++;
            if (status == ServiceRequest.Status.Done)
                done ++;
        }
        else{
            if (status == ServiceRequest.Status.Blank)
                blank --;
            if (status == ServiceRequest.Status.Processing)
                processing --;
            if (status == ServiceRequest.Status.Done)
                done --;
        }
    }
    public void updateBar(){

    }
    public void setup(boolean isOriginal){
        segmentBar.orientationProperty().bind(orientation);
        blankSegment.setText("Blank: " + blank);
        blankSegment.setValue(blank);
        processingSegment.setText("Processing: " + processing);
        processingSegment.setValue(processing);
        doneSegment.setText("Done: " + done);
        doneSegment.setValue(done);
        if(isOriginal)
            segmentBar.getSegments().addAll(blankSegment, processingSegment, doneSegment);

        segmentBar.setSegmentViewFactory(segment -> {
            SegmentedBar<org.controlsfx.control.SegmentedBar.Segment>.SegmentView view = segmentBar.new SegmentView((org.controlsfx.control.SegmentedBar.Segment)segment);
            String color = "";
            if(((SegmentedBar.Segment) segment).getText().contains("Blank"))
                color = "#000000";
            else if (((SegmentedBar.Segment) segment).getText().contains("Processing"))
                color = "#00C2A5";
            else
                color = "#069420";

            view.setStyle("-fx-background-color: " + color);
            return view ;
        });
    }


}
