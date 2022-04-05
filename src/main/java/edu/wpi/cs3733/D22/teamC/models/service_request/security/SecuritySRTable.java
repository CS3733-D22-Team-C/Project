package edu.wpi.cs3733.D22.teamC.models.service_request.security;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class SecuritySRTable extends RecursiveTreeObject<SecuritySRTable> {

    StringProperty securityType;

    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;

//    public securitySRTable(String securityType, String assigneeID,
//                           String location, String status, String priority){
//        this.securityType = new SimpleStringProperty(securityType);
//        this.assigneeID = new SimpleStringProperty(assigneeID);
//        this.location = new SimpleStringProperty(location);
//        this.status = new SimpleStringProperty(status);
//        this.priority = new SimpleStringProperty(priority);
//    }

    public SecuritySRTable(SecuritySR securitySR) {
        this.securityType =  new SimpleStringProperty(securitySR.getSecurityType().toString());
        this.assigneeID = new SimpleStringProperty(securitySR.getAssigneeID());
        this.location = new SimpleStringProperty(securitySR.getLocation());
        this.status = new SimpleStringProperty(securitySR.getStatus().toString());
        this.priority = new SimpleStringProperty(securitySR.getPriority().toString());
    }



    public String getSecurityType() {
        return securityType.get();
    }

    public StringProperty securityTypeProperty() {
        return securityType;
    }

    public void setSecurityTypeType(String securityType) {
        this.securityType.set(securityType);
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

    public static void createTableColumns(JFXTreeTableView<SecuritySRTable> table) {
        //Columns for table
        JFXTreeTableColumn<SecuritySRTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setPrefWidth(80);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SecuritySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SecuritySRTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<SecuritySRTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setPrefWidth(80);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SecuritySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SecuritySRTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<SecuritySRTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SecuritySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SecuritySRTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<SecuritySRTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setPrefWidth(80);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SecuritySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SecuritySRTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<SecuritySRTable, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setPrefWidth(80);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SecuritySRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SecuritySRTable, String> param) {
                return param.getValue().getValue().securityTypeProperty();
            }
        });
//        JFXTreeTableColumn<securitySRTable, String> typeIDCol = new JFXTreeTableColumn<>("EquipID");
//        typeIDCol.setPrefWidth(80);
//        typeIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<securitySRTable, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<securitySRTable, String> param) {
//                return param.getValue().getValue().equipmentIDProperty();
//            }
//        });

        // Sets columns
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol, typeCol);
    }
}
