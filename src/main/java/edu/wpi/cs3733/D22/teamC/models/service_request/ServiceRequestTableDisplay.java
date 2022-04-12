package edu.wpi.cs3733.D22.teamC.models.service_request;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ServiceRequestTableDisplay<T extends ServiceRequest> extends TableDisplay<T> {
    public class ServiceRequestTableEntry extends TableDisplayEntry {
        // Properties
        public StringProperty id;
        public StringProperty type;
        public StringProperty assigneeID;
        public StringProperty location;
        public StringProperty status;
        public StringProperty priority;

        public ServiceRequestTableEntry(T serviceRequest) {
            super(serviceRequest);

            this.id         = new SimpleStringProperty(serviceRequest.getRequestID());
            this.type       = new SimpleStringProperty(serviceRequest.getRequestType().toString());
            this.assigneeID = new SimpleStringProperty((serviceRequest.getAssignee() == null) ? "" : serviceRequest.getAssignee().getLastName() + ", " + serviceRequest.getAssignee().getFirstName());
            this.location   = new SimpleStringProperty(serviceRequest.getLocation());
            this.status     = new SimpleStringProperty(serviceRequest.getStatus().toString());
            this.priority   = new SimpleStringProperty(serviceRequest.getPriority().toString());
        }
    }

    public ServiceRequestTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(T object) {
        addEntry(new ServiceRequestTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "ID",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.id;}
        );

        addColumn(
                table,
                "Type",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.type;}
        );

        addColumn(
                table,
                "Assignee",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.assigneeID;}
        );

        addColumn(
                table,
                "Location",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.location;}
        );

        addColumn(
                table,
                "Status",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.status;}
        );

        addColumn(
                table,
                "Priority",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.priority;}
        );
    }

    /**
     * Sets a partial sequence of consistent columns, useful as a base for other Service Request types.
     * @param table
     */
    protected void setPartialColumns(JFXTreeTableView table) {
        addColumn(
                table,
                "ID",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.id;}
        );

        addColumn(
                table,
                "Assignee",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.assigneeID;}
        );

        addColumn(
                table,
                "Location",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.location;}
        );

        addColumn(
                table,
                "Status",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.status;}
        );

        addColumn(
                table,
                "Priority",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.priority;}
        );
    }
}
