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

public class DashboardCreatedTableDisplay extends ServiceRequestTableDisplay<ServiceRequest> {

    public DashboardCreatedTableDisplay(JFXTreeTableView table) {
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
                "Assignee ID",
                1f * Integer.MAX_VALUE * 16.66,
                (ServiceRequestTableEntry entry) -> {return entry.assigneeID;}
        );
    }


}
