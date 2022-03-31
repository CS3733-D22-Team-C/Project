package edu.wpi.cs3733.D22.teamC.models.service_request;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.medicine_delivery.MedicineDeliverySRTable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class ServiceRequestTable<T extends ServiceRequest> {

    protected class ServiceRequestTableEntry extends RecursiveTreeObject<ServiceRequestTableEntry> {
        // Properties
        StringProperty assigneeID;
        StringProperty location;
        StringProperty status;
        StringProperty priority;
        StringProperty ID;
        StringProperty Type;

        public ServiceRequestTableEntry(T serviceRequest) {
            this.assigneeID = new SimpleStringProperty(serviceRequest.getAssigneeID());
            this.location   = new SimpleStringProperty(serviceRequest.getLocation());
            this.status     = new SimpleStringProperty(serviceRequest.getStatus());
            this.priority   = new SimpleStringProperty(serviceRequest.getPriority());
            this.ID         = new SimpleStringProperty(serviceRequest.getRequestID());
            this.Type       = new SimpleStringProperty(serviceRequest.getRequestType());
        }
    }

    // Variables
    ObservableList<ServiceRequestTableEntry> data = FXCollections.observableArrayList();

    public ServiceRequestTable(JFXTreeTableView jfxTreeTableView) {
        // Table Setup
        TreeItem<ServiceRequestTableEntry> root = new RecursiveTreeItem<ServiceRequestTableEntry>(data, RecursiveTreeObject::getChildren);
        jfxTreeTableView.setRoot(root);
        jfxTreeTableView.setShowRoot(false);
        jfxTreeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

        // Columns for table
        createTableColumnString(
                jfxTreeTableView,
                "Priority",
                1f * Integer.MAX_VALUE * 16.66,
                param -> {return param.getValue().getValue().priority;}
        );
    }

    public void addEntry(T serviceRequest) {
        ServiceRequestTableEntry entry = new ServiceRequestTableEntry(serviceRequest);
        data.add(entry);
    }

    protected void createTableColumnString(
            JFXTreeTableView<ServiceRequestTableEntry> table,
            String header,
            double maxWidth,
            Callback<TreeTableColumn.CellDataFeatures<ServiceRequestTableEntry, String>, ObservableValue<String>> callback
    ) {
        JFXTreeTableColumn<ServiceRequestTableEntry, String> col = new JFXTreeTableColumn<>(header);
        col.setMaxWidth(maxWidth);
        col.setCellValueFactory(callback);
        table.getColumns().add(col);
    }
}
