package edu.wpi.cs3733.D22.teamC.models.Employee;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.generic.TableDisplay;
import javafx.beans.property.*;

public class EmployeeTableDisplay extends TableDisplay<Employee> {
    public class EmployeeTableEntry extends TableDisplayEntry {
        // Properties
        private IntegerProperty employeeID;
        private StringProperty  firstName;
        private StringProperty  lastName;
        private StringProperty  email;
        private StringProperty  role;
        private BooleanProperty isAdmin;

        public EmployeeTableEntry(Employee employee) {
            super(employee);

            employeeID  = new SimpleIntegerProperty(employee.getEmployeeID());
            firstName   = new SimpleStringProperty(employee.getFirstName());
            lastName    = new SimpleStringProperty(employee.getLastName());
            email       = new SimpleStringProperty(employee.getEmail());
            role        = new SimpleStringProperty(employee.getRole().toString());
            isAdmin     = new SimpleBooleanProperty(employee.getIsAdmin());
        }
    }

    public EmployeeTableDisplay(JFXTreeTableView table) {
        super(table);
    }

    @Override
    public void addObject(Employee object) {
        addEntry(new EmployeeTableEntry(object));
    }

    @Override
    protected void setColumns(JFXTreeTableView table) {
        // Insert Columns for Table
        addColumn(
                table,
                "Employee ID",
                1f * Integer.MAX_VALUE * 20.0,
                (EmployeeTableEntry entry) -> {return entry.employeeID.asObject();}
        );

        addColumn(
                table,
                "First Name",
                1f * Integer.MAX_VALUE * 20.0,
                (EmployeeTableEntry entry) -> {return entry.firstName;}
        );

        addColumn(
                table,
                "Last Name",
                1f * Integer.MAX_VALUE * 20.0,
                (EmployeeTableEntry entry) -> {return entry.lastName;}
        );

        addColumn(
                table,
                "Email",
                1f * Integer.MAX_VALUE * 20.0,
                (EmployeeTableEntry entry) -> {return entry.email;}
        );

        addColumn(
                table,
                "Role",
                1f * Integer.MAX_VALUE * 20.0,
                (EmployeeTableEntry entry) -> {return entry.role;}
        );

        // TODO: "We will worry about that later" - Nick Frangie, April 5th, 2022, 10:42p.m.
        /*addColumn(
                table,
                "Admin",
                1f * Integer.MAX_VALUE * 16.66,
                (EmployeeTableEntry entry) -> {return entry.isAdmin;}
        );*/
    }
}
