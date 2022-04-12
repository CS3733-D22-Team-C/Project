package edu.wpi.cs3733.D22.teamC.controller.dashboard;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class DashboardTableDisplay extends ServiceRequestTableDisplay<ServiceRequest> {

    public DashboardTableDisplay(JFXTreeTableView table) {
        super(table);
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


}
