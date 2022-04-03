package edu.wpi.cs3733.D22.teamC.models.location;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class LocationTable extends RecursiveTreeObject<LocationTable> {
    IntegerProperty nodeID;
    StringProperty nodeType;
    StringProperty shortName;
    StringProperty longName;
    StringProperty floor;
    StringProperty building;
    IntegerProperty x;
    IntegerProperty y;

    public LocationTable(Location location) {
        this.nodeID    = new SimpleIntegerProperty(location.getNodeID());
        this.nodeType  = new SimpleStringProperty(location.getNodeType());
        this.shortName = new SimpleStringProperty(location.getShortName());
        this.longName  = new SimpleStringProperty(location.getLongName());
        this.floor     = new SimpleStringProperty(location.getFloor());
        this.building  = new SimpleStringProperty(location.getBuilding());
        this.x = new SimpleIntegerProperty(location.getX());
        this.y = new SimpleIntegerProperty(location.getY());
    }

    public static void createTableColumns(JFXTreeTableView<LocationTable> table) {
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        //Columns for table
        JFXTreeTableColumn<LocationTable, Integer> nodeIDCol = new JFXTreeTableColumn<>("Node ID");
        nodeIDCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        nodeIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<LocationTable, Integer> param) {
                return param.getValue().getValue().nodeID.asObject();
            }
        });
        JFXTreeTableColumn<LocationTable, String> nodeTypeCol = new JFXTreeTableColumn<>("Node Type");
        nodeTypeCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        nodeTypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().nodeType;
            }
        });
        JFXTreeTableColumn<LocationTable, String> shortNameCol = new JFXTreeTableColumn<>("Short Name");
        shortNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 13.0);
        shortNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().shortName;
            }
        });
        JFXTreeTableColumn<LocationTable, String> longNameCol = new JFXTreeTableColumn<>("Long Name");
        longNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        longNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().longName;
            }
        });
        JFXTreeTableColumn<LocationTable, String> floorCol = new JFXTreeTableColumn<>("Floor");
        floorCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        floorCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().floor;
            }
        });
        JFXTreeTableColumn<LocationTable, String> buildingCol = new JFXTreeTableColumn<>("Building");
        buildingCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        buildingCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().building;
            }
        });
        JFXTreeTableColumn<LocationTable, Integer> xCol = new JFXTreeTableColumn<>("X");
        xCol.setMaxWidth(1f * Integer.MAX_VALUE * 5.0);
        xCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<LocationTable, Integer> param) {
                return param.getValue().getValue().x.asObject();
            }
        });
        JFXTreeTableColumn<LocationTable, Integer> yCol = new JFXTreeTableColumn<>("Y");
        yCol.setMaxWidth(1f * Integer.MAX_VALUE * 5.0);
        yCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<LocationTable, Integer> param) {
                return param.getValue().getValue().y.asObject();
            }
        });

        // Set Columns
        table.getColumns().setAll(nodeIDCol, nodeTypeCol, shortNameCol, longNameCol, floorCol, buildingCol, xCol, yCol);
    }
}
