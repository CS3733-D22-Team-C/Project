package edu.wpi.cs3733.D22.teamC.controller.location;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationSelectController implements Initializable {

    @FXML private JFXTreeTableView<?> table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Constrain column sizes to the size of the table
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        //Columns for table
        TreeTableColumn nodeID = new TreeTableColumn("Node ID");
        nodeID.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        TreeTableColumn nodeType = new TreeTableColumn("Node Type");
        nodeType.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        TreeTableColumn shortName = new TreeTableColumn("Short Name");
        shortName.setMaxWidth(1f * Integer.MAX_VALUE * 13.0);
        TreeTableColumn longName = new TreeTableColumn("Long Name");
        longName.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        TreeTableColumn floor = new TreeTableColumn("Floor");
        floor.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        TreeTableColumn building = new TreeTableColumn("Building");
        building.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        TreeTableColumn x = new TreeTableColumn("X");
        x.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        TreeTableColumn y = new TreeTableColumn("Y");
        y.setMaxWidth(1f * Integer.MAX_VALUE * 5);
        table.getColumns().addAll(nodeID, nodeType, shortName, longName, floor, building, x, y);
    }
}
