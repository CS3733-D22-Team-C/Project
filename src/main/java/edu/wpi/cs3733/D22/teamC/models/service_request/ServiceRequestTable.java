package edu.wpi.cs3733.D22.teamC.models.service_request;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ServiceRequestTable<T extends ServiceRequest> extends TableDisplay<T, ServiceRequestTable<T>.ServiceRequestTableEntry> {
    protected class ServiceRequestTableEntry extends TableDisplayEntry {
        // Properties
        StringProperty assigneeID;
        StringProperty location;
        StringProperty status;
        StringProperty priority;
        StringProperty ID;
        StringProperty Type;

        public ServiceRequestTableEntry(T serviceRequest) {
            super(serviceRequest);

            this.assigneeID = new SimpleStringProperty(serviceRequest.getAssigneeID());
            this.location   = new SimpleStringProperty(serviceRequest.getLocation());
            this.status     = new SimpleStringProperty(serviceRequest.getStatus());
            this.priority   = new SimpleStringProperty(serviceRequest.getPriority());
            this.ID         = new SimpleStringProperty(serviceRequest.getRequestID());
            this.Type       = new SimpleStringProperty(serviceRequest.getRequestType());
        }
    }

    public ServiceRequestTable(JFXTreeTableView jfxTreeTableView) {
        super(jfxTreeTableView);

        // Insert Columns for Table
        addColumn(
                jfxTreeTableView,
                "Priority",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.priority;}
        );
    }

    public void addObject(T object) {
        addEntry(new ServiceRequestTableEntry(object));
    }
}
