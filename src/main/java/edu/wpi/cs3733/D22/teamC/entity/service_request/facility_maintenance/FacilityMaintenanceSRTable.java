package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
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

    public FacilityMaintenanceSRTable(FacilityMaintenanceServiceRequest facilityMaintenanceSR) {
        this.maintenanceType = new SimpleStringProperty(facilityMaintenanceSR.getMaintenanceType());
        this.assigneeID = new SimpleStringProperty(facilityMaintenanceSR.getAssigneeID());
        this.location = new SimpleStringProperty(facilityMaintenanceSR.getLocation());
        this.status = new SimpleStringProperty(facilityMaintenanceSR.getStatus());
        this.priority = new SimpleStringProperty(facilityMaintenanceSR.getPriority());
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
        //Columns for table
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setPrefWidth(80);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setPrefWidth(80);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setPrefWidth(80);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FacilityMaintenanceSRTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<FacilityMaintenanceSRTable, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setPrefWidth(80);
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
