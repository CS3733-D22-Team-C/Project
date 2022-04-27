package edu.wpi.cs3733.D22.teamC.controller.profile;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;

public class DashboardAssignedTableDisplay extends ServiceRequestTableDisplay<ServiceRequest> {

    public DashboardAssignedTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Type",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.type;}
        );

        addColumn(
                table,
                "Status",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.status;}
        );

        addColumn(
                table,
                "Time Submitted",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.createTime;}
        );

        addColumn(
                table,
                "Modified Time",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.modifiedTime;}
        );

        addColumn(
                table,
                "Location",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.location;}
        );
    }
}
