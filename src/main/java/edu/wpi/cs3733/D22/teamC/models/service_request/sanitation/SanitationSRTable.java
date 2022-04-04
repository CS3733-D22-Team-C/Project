package edu.wpi.cs3733.D22.teamC.models.service_request.sanitation;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class SanitationSRTable extends RecursiveTreeObject<SanitationSRTable> {

    // Columns
    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;
    StringProperty sanitationType;

    // Constructor
    public SanitationSRTable(SanitationSR sanitationSR) {
        this.assigneeID = new SimpleStringProperty(sanitationSR.getAssigneeID());
        this.location = new SimpleStringProperty(sanitationSR.getLocation());
        this.status = new SimpleStringProperty(sanitationSR.getStatus().toString());
        this.priority = new SimpleStringProperty(sanitationSR.getPriority().toString());
        this.sanitationType = new SimpleStringProperty(sanitationSR.getSanitationType().toString());
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

    public String getSanitationType() {
        return sanitationType.get();
    }

    public StringProperty sanitationTypeProperty() {
        return sanitationType;
    }

    public void setSanitationType(String sanitationType) {
        this.sanitationType.set(sanitationType);
    }

    public static void createTableColumns(JFXTreeTableView<SanitationSRTable> table) {
        // Constrain column sizes to the size of the table
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        //Columns for table
        JFXTreeTableColumn<SanitationSRTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanitationSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanitationSRTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<SanitationSRTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanitationSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanitationSRTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<SanitationSRTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanitationSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanitationSRTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<SanitationSRTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanitationSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanitationSRTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<SanitationSRTable, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<SanitationSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<SanitationSRTable, String> param) {
                return param.getValue().getValue().sanitationType;
            }
        });


        // Sets columns
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol, typeCol);
    }
}
