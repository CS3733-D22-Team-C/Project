package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class LabSystemSRTable extends RecursiveTreeObject<LabSystemSRTable> {
    StringProperty labType;
    StringProperty patientID;

    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;

    public LabSystemSRTable(String labType, String patientID,
                            String assigneeID, String location,
                            String status, String priority) {
        this.labType    = new SimpleStringProperty(labType);
        this.patientID  = new SimpleStringProperty(patientID);
        this.assigneeID = new SimpleStringProperty(assigneeID);
        this.location   = new SimpleStringProperty(location);
        this.status     = new SimpleStringProperty(status);
        this.priority   = new SimpleStringProperty(priority);
    }

    public LabSystemSRTable(LabSystemServiceRequest labSystemSR){
        this.labType    = new SimpleStringProperty(labSystemSR.getLabType());
        this.patientID  = new SimpleStringProperty(labSystemSR.getPatientID());
        this.assigneeID = new SimpleStringProperty(labSystemSR.getAssigneeID());
        this.location   = new SimpleStringProperty(labSystemSR.getLocation());
        this.status     = new SimpleStringProperty(labSystemSR.getStatus());
        this.priority   = new SimpleStringProperty(labSystemSR.getPriority());
    }

    public String getLabType() {
        return labType.get();
    }

    public StringProperty labTypeProperty() {
        return labType;
    }

    public void setLabType(String labType) {
        this.labType.set(labType);
    }

    public String getPatientID() {
        return patientID.get();
    }

    public StringProperty patientIDProperty() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID.set(patientID);
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

    public static void createTableColumns(JFXTreeTableView<LabSystemSRTable> table) {
        //Columns for table
        JFXTreeTableColumn<LabSystemSRTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setPrefWidth(80);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LabSystemSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LabSystemSRTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<LabSystemSRTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setPrefWidth(80);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LabSystemSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LabSystemSRTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<LabSystemSRTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LabSystemSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LabSystemSRTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<LabSystemSRTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setPrefWidth(80);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LabSystemSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LabSystemSRTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<LabSystemSRTable, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setPrefWidth(80);
        typeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LabSystemSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LabSystemSRTable, String> param) {
                return param.getValue().getValue().labTypeProperty();
            }
        });
        JFXTreeTableColumn<LabSystemSRTable, String> patientIDCol = new JFXTreeTableColumn<>("EquipID");
        patientIDCol.setPrefWidth(80);
        patientIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LabSystemSRTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LabSystemSRTable, String> param) {
                return param.getValue().getValue().patientIDProperty();
            }
        });

        // Sets columns
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol, typeCol, patientIDCol);
    }
}