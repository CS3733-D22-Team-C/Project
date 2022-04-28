package edu.wpi.cs3733.D22.teamC.models.service_request;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.*;

public class ServiceRequestTableDisplay<T extends ServiceRequest> extends TableDisplay<T> {
    public class ServiceRequestTableEntry extends TableDisplayEntry {
        // Properties
        public StringProperty number;
        public StringProperty type;
        public StringProperty assigneeID;
        public StringProperty location;
        public StringProperty status;
        public StringProperty priority;
        public StringProperty createTime;
        public StringProperty modifiedTime;

        public ServiceRequestTableEntry(T serviceRequest) {
            super(serviceRequest);

            this.number       = new SimpleStringProperty(serviceRequest.toString());
            this.type         = new SimpleStringProperty(serviceRequest.getRequestType().toString());
            this.assigneeID   = new SimpleStringProperty(serviceRequest.getAssignee() == null ? "" : serviceRequest.getAssignee().getLastName() + ", " + serviceRequest.getAssignee().getFirstName());
            this.location     = new SimpleStringProperty(serviceRequest.getLocation() == null ? "" : serviceRequest.getLocation().getShortName());
            this.status       = new SimpleStringProperty(serviceRequest.getStatus().toString());
            this.priority     = new SimpleStringProperty(serviceRequest.getPriority().toString());
            this.createTime   = new SimpleStringProperty(serviceRequest.getCreationTimestamp().toString());
            this.modifiedTime = new SimpleStringProperty(serviceRequest.getModifiedTimestamp().toString());
        }

        @Override
        public void RefreshEntry() {
            number.setValue(object.toString());
            type.setValue(object.getRequestType().toString());
            assigneeID.setValue(object.getAssignee() == null ? "" : object.getAssignee().getLastName() + ", " + object.getAssignee().getFirstName());
            location.setValue(object.getLocation() == null ? "" : object.getLocation().getShortName());
            status.setValue(object.getStatus().toString());
            priority.setValue(object.getPriority().toString());
            createTime.setValue(object.getCreationTimestamp().toString());
            modifiedTime.setValue(object.getModifiedTimestamp().toString());
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
                "Number",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.number;}
        );

        addColumn(
                table,
                "Type",
                1f * Integer.MAX_VALUE * 20,
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
                "Number",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.number;}
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
