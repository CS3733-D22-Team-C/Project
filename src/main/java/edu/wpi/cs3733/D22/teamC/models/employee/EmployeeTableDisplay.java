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

            employeeID   = new SimpleStringProperty((employee.getEmployeeID()));
            firstName   = new SimpleStringProperty(employee.getFirstName());
            lastName   = new SimpleStringProperty(employee.getLastName());
            emailID   = new SimpleStringProperty(employee.getEmailID());
            phone   = new SimpleStringProperty(employee.getPhone());
            address   = new SimpleStringProperty(employee.getAddress());
            role   = new SimpleStringProperty(employee.getRole().toString());
            username   = new SimpleStringProperty(employee.getUsername());
            address   = new SimpleStringProperty(employee.getAddress());
            isAdmin   = new SimpleBooleanProperty(employee.getAdmin());
        }
    }

    public EmployeeTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(Employee object) {
        addEntry(new EmployeeTableDisplay.EmployeeTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Last Name",
                1f * Integer.MAX_VALUE * 25,
                (EmployeeTableDisplay.EmployeeTableEntry entry) -> {return entry.lastName;}
        );

        addColumn(
                table,
                "First Name",
                1f * Integer.MAX_VALUE * 25,
                (EmployeeTableDisplay.EmployeeTableEntry entry) -> {return entry.firstName;}
        );

        addColumn(
                table,
                "Username",
                1f * Integer.MAX_VALUE * 25,
                (EmployeeTableDisplay.EmployeeTableEntry entry) -> {return entry.username;}
        );

        addColumn(
                table,
                "Role",
                1f * Integer.MAX_VALUE * 25,
                (EmployeeTableDisplay.EmployeeTableEntry entry) -> {return entry.role;}
        );



    }
}
