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
        nodeID.setPrefWidth(80);
        TreeTableColumn nodeType = new TreeTableColumn("Node Type");
        nodeType.setPrefWidth(80);
        TreeTableColumn shortName = new TreeTableColumn("Short Name");
        shortName.setPrefWidth(80);
        TreeTableColumn longName = new TreeTableColumn("Long Name");
        longName.setPrefWidth(80);
        TreeTableColumn floor = new TreeTableColumn("Floor");
        floor.setPrefWidth(80);
        TreeTableColumn building = new TreeTableColumn("Building");
        building.setPrefWidth(80);
        TreeTableColumn x = new TreeTableColumn("X");
        x.setPrefWidth(80);
        TreeTableColumn y = new TreeTableColumn("Y");
        y.setPrefWidth(80);
        table.getColumns().addAll(nodeID, nodeType, shortName, longName, floor, building, x, y);

    }

}
