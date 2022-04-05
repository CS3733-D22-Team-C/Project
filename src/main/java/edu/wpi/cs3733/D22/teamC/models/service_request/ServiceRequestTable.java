package edu.wpi.cs3733.D22.teamC.models.service_request;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class ServiceRequestTable extends RecursiveTreeObject<ServiceRequestTable> {

    StringProperty assigneeID;
    StringProperty location;
    StringProperty status;
    StringProperty priority;
    IntegerProperty ID;
    StringProperty Type;

//    public securitySRTable(String securityType, String assigneeID,
//                           String location, String status, String priority){
//        this.securityType = new SimpleStringProperty(securityType);
//        this.assigneeID = new SimpleStringProperty(assigneeID);
//        this.location = new SimpleStringProperty(location);
//        this.status = new SimpleStringProperty(status);
//        this.priority = new SimpleStringProperty(priority);
//    }
    public ServiceRequestTable(){}

    public ServiceRequestTable(ServiceRequest serviceRequest) {
        this.assigneeID = new SimpleStringProperty(serviceRequest.getAssigneeID());
        this.location = new SimpleStringProperty(serviceRequest.getLocation());
        this.status = new SimpleStringProperty(serviceRequest.getStatus().toString());
        this.priority = new SimpleStringProperty(serviceRequest.getPriority().toString());
        this.ID = new SimpleIntegerProperty(serviceRequest.getRequestID());
        this.Type = new SimpleStringProperty(serviceRequest.getRequestType().toString());
    }


    public String getAssigneeID() {
        return assigneeID.get();
    }
    public StringProperty assigneeIDProperty() {return assigneeID;}
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
    public StringProperty statusProperty() {return status;}
    public void setStatus(String status) {
        this.status.set(status);
    }


    public String getPriority() {
        return priority.get();
    }
    public StringProperty priorityProperty() {
        return priority;
    }
    public void setPriority(String priority) { this.priority.set(priority);}

    public int getID() {
        return ID.get();
    }
    public IntegerProperty IDProperty() {
        return ID;
    }
    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getType() {
        return Type.get();
    }
    public StringProperty TypeProperty() {
        return Type;
    }
    public void setType(String Type) {
        this.Type.set(Type);
    }

    public static void createTableColumns(JFXTreeTableView<ServiceRequestTable> table) {
        table.setColumnResizePolicy( TreeTableView.CONSTRAINED_RESIZE_POLICY );
        //Columns for table
        JFXTreeTableColumn<ServiceRequestTable, String> priorityCol = new JFXTreeTableColumn<>("Priority");
        priorityCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);;
        priorityCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().priorityProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> assigneeCol = new JFXTreeTableColumn<>("Assignee");
        assigneeCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);;
        assigneeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().assigneeIDProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> statusCol = new JFXTreeTableColumn<>("Status");
        statusCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);;
        statusCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().statusProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> locationCol = new JFXTreeTableColumn<>("Location");
        locationCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);;
        locationCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().locationProperty();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, Integer> IDCol = new JFXTreeTableColumn<>("ID");
        IDCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);;
        IDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, Integer> param) {
                return param.getValue().getValue().IDProperty().asObject();
            }
        });
        JFXTreeTableColumn<ServiceRequestTable, String> TypeCol = new JFXTreeTableColumn<>("Type");
        TypeCol.setMaxWidth(1f * Integer.MAX_VALUE * 16.66);;
        TypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ServiceRequestTable, String> param) {
                return param.getValue().getValue().TypeProperty();
            }
        });

        // Sets columns
        table.getColumns().setAll(IDCol, TypeCol, assigneeCol, statusCol, locationCol, priorityCol);
    }
}
