package edu.wpi.cs3733.D22.teamC.entity;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class LocationTable extends RecursiveTreeObject<LocationTable> {
    StringProperty nodeID;
    StringProperty nodeType;
    StringProperty shortName;
    StringProperty longName;
    StringProperty floor;
    StringProperty building;
    StringProperty x;
    StringProperty y;

    public LocationTable(String nodeID, String nodeType, String shortName,
                         String longName, String floor, String building,
                         String x, String y){
        this.nodeID     = new SimpleStringProperty(nodeID);
        this.nodeType   = new SimpleStringProperty(nodeType);
        this.shortName  = new SimpleStringProperty(shortName);
        this.longName   = new SimpleStringProperty(longName);
        this.floor      = new SimpleStringProperty(floor);
        this.building   = new SimpleStringProperty(building);
        this.x          = new SimpleStringProperty(x);
        this.y          = new SimpleStringProperty(y);
    }

    public LocationTable(Location location) {
        this.nodeID    = new SimpleStringProperty(location.getNodeID());
        this.nodeType  = new SimpleStringProperty(location.getNodeType());
        this.shortName = new SimpleStringProperty(location.getShortName());
        this.longName  = new SimpleStringProperty(location.getLongName());
        this.floor     = new SimpleStringProperty(location.getFloor());
        this.building  = new SimpleStringProperty(location.getBuilding());
    }

    public String getNodeID() {
        return nodeID.get();
    }

    public StringProperty nodeIDProperty() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID.set(nodeID);
    }

    public String getNodeType() {
        return nodeType.get();
    }

    public StringProperty nodeTypeProperty() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType.set(nodeType);
    }

    public String getShortName() {
        return shortName.get();
    }

    public StringProperty shortNameProperty() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName.set(shortName);
    }

    public String getLongName() {
        return longName.get();
    }

    public StringProperty longNameProperty() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName.set(longName);
    }

    public String getFloor() {
        return floor.get();
    }

    public StringProperty floorProperty() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor.set(floor);
    }

    public String getBuilding() {
        return building.get();
    }

    public StringProperty buildingProperty() {
        return building;
    }

    public void setBuilding(String building) {
        this.building.set(building);
    }

    public String getX() {
        return x.get();
    }

    public StringProperty xProperty() {
        return x;
    }

    public void setX(String x) {
        this.x.set(x);
    }

    public String getY() {
        return y.get();
    }

    public StringProperty yProperty() {
        return y;
    }

    public void setY(String y) {
        this.y.set(y);
    }

    public static void createTableColumns(JFXTreeTableView<LocationTable> table) {
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        //Columns for table
        JFXTreeTableColumn<LocationTable, String> nodeIDCol = new JFXTreeTableColumn<>("Node ID");
        nodeIDCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        nodeIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().nodeIDProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> nodeTypeCol = new JFXTreeTableColumn<>("Node Type");
        nodeTypeCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        nodeTypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().nodeTypeProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> shortNameCol = new JFXTreeTableColumn<>("Short Name");
        shortNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 13.0);
        shortNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().shortNameProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> longNameCol = new JFXTreeTableColumn<>("Long Name");
        longNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        longNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().longNameProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> floorCol = new JFXTreeTableColumn<>("Floor");
        floorCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        floorCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().floorProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> buildingCol = new JFXTreeTableColumn<>("Building");
        buildingCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        buildingCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().buildingProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> xCol = new JFXTreeTableColumn<>("X");
        xCol.setMaxWidth(1f * Integer.MAX_VALUE * 5.0);
        xCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().xProperty();
            }
        });
        JFXTreeTableColumn<LocationTable, String> yCol = new JFXTreeTableColumn<>("Y");
        yCol.setMaxWidth(1f * Integer.MAX_VALUE * 5.0);
        yCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().yProperty();
            }
        });

        // Set Columns
        table.getColumns().setAll(nodeIDCol, nodeTypeCol, shortNameCol, longNameCol, floorCol, buildingCol, xCol, yCol);
    }
}
