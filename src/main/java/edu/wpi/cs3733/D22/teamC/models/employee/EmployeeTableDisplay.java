package edu.wpi.cs3733.D22.teamC.models.employee;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import edu.wpi.cs3733.D22.teamC.models.location.LocationTableDisplay;
import javafx.beans.property.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

public class EmployeeTableDisplay extends TableDisplay<Employee> {
    public class EmployeeTableEntry extends TableDisplayEntry {
        // Properties
        private StringProperty employeeID;
        private StringProperty firstName;
        private StringProperty lastName;
        private StringProperty emailID;
        private StringProperty phone;
        private StringProperty address;
        private StringProperty role;
        private StringProperty username;
        private StringProperty password;
        private BooleanProperty isAdmin;



        public EmployeeTableEntry(Employee employee) {
            super(employee);

            employeeID   = new SimpleStringProperty((employee.getNodeID()));
            firstName   = new SimpleStringProperty(employee.getFloor());
            lastName   = new SimpleStringProperty(employee.getBuilding());
            emailID   = new SimpleStringProperty(employee.getNodeType().toString());
            phone   = new SimpleStringProperty(employee.getLongName());
            address   = new SimpleStringProperty(employee.getShortName());
            role   = new SimpleIntegerProperty(employee.getX());
            username   = new SimpleIntegerProperty(employee.getY());
            address   = new SimpleStringProperty(employee.getShortName());
            address   = new SimpleStringProperty(employee.getShortName());
        }
    }

    public LocationTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(Location object) {
        addEntry(new LocationTableDisplay.LocationTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Location ID",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.id;}
        );

        addColumn(
                table,
                "Floor",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.floor;}
        );

        addColumn(
                table,
                "Building",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.building;}
        );

        addColumn(
                table,
                "Node Type",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.nodeType;}
        );

        addColumn(
                table,
                "Long Name",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.longName;}
        );

        addColumn(
                table,
                "Short Name",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.shortName;}
        );

        addColumn(
                table,
                "X",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.x;}
        );

        addColumn(
                table,
                "Y",
                1f * Integer.MAX_VALUE * 16.66,
                (LocationTableDisplay.LocationTableEntry entry) -> {return entry.y;}
        );
    }
}
