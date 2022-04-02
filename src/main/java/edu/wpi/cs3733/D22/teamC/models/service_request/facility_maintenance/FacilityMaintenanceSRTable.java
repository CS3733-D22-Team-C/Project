package edu.wpi.cs3733.D22.teamC.models.service_request.facility_maintenance;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class FacilityMaintenanceSRTable extends RecursiveTreeObject<FacilityMaintenanceSRTable> {

    StringProperty maintenanceType;
    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;

    public FacilityMaintenanceSRTable(String maintenanceType, String assigneeID,
                                   String location, String status, String priority){
        this.maintenanceType = new SimpleStringProperty(maintenanceType);
        this.assigneeID = new SimpleStringProperty(assigneeID);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
        this.priority = new SimpleStringProperty(priority);
    }

    public FacilityMaintenanceSRTable(FacilityMaintenanceSR facilityMaintenanceSR) {
        this.maintenanceType = new SimpleStringProperty(facilityMaintenanceSR.getMaintenanceType().toString());
        this.assigneeID = new SimpleStringProperty(facilityMaintenanceSR.getAssigneeID());
        this.location = new SimpleStringProperty(facilityMaintenanceSR.getLocation());
        this.status = new SimpleStringProperty(facilityMaintenanceSR.getStatus().toString());
        this.priority = new SimpleStringProperty(facilityMaintenanceSR.getPriority().toString());
    }

    public String getMaintenanceType() {
        return maintenanceType.get();
    }

    public StringProperty maintenanceTypeProperty() {
        return maintenanceType;
    }

    public void setMaintenanceType(String maintenanceType) {
        this.maintenanceType.set(maintenanceType);
    }

    public String getAssigneeID() {
        return assigneeID.get();
    }

    public StringProperty assigneeIDProperty() {
        return assigneeID;
    }

    public void setAssigneeID(String assigneeID) {
        this.assigneeID.set(assigneeID);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getPriority() {
        return priority.get();
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public static void createTableColumns(JFXTreeTableView<FacilityMaintenanceSRTable> table) {
        table.setColumnResizePolicy( TreeTableView.CONSTRAINED_RESIZE_POLICY );
        //Columns for table
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setMaxWidth(1f * Integer.MAX_VALUE * 20);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().maintenanceTypeProperty();
            }
        });

        // Sets columns
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol, typeCol);
    }
}
