package edu.wpi.cs3733.D22.teamC.controller.general;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class ServiceRequestTable extends RecursiveTreeObject<ServiceRequestTable> {

    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;
    StringProperty type;

//    public securitySRTable(String securityType, String assigneeID,
//                           String location, String status, String priority){
//        this.securityType = new SimpleStringProperty(securityType);
//        this.assigneeID = new SimpleStringProperty(assigneeID);
//        this.location = new SimpleStringProperty(location);
//        this.status = new SimpleStringProperty(status);
//        this.priority = new SimpleStringProperty(priority);
//    }

    public ServiceRequestTable(ServiceRequest serviceRequest) {
        this.assigneeID = new SimpleStringProperty(serviceRequest.getAssigneeID());
        this.location = new SimpleStringProperty(serviceRequest.getLocation());
        this.status = new SimpleStringProperty(serviceRequest.getStatus());
        this.priority = new SimpleStringProperty(serviceRequest.getPriority());
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
    public void setLocation(String location) { this.location.set(location);}


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


    public static void createTableColumns(JFXTreeTableView<ServiceRequestTable> table) {
        //Columns for table
        JFXTreeTableColumn<ServiceRequestTable, String> IDCol = new JFXTreeTableColumn<>("Priority");
        IDCol.setPrefWidth(80);
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setPrefWidth(80);
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setPrefWidth(80);
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setPrefWidth(80);
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().locationProperty();
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
        table.getColumns().setAll(IDCol, assigneeCol, statusCol, locationCol);
    }
}
