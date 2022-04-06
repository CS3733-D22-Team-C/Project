package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.Employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

public class EmployeeCSVReader extends CSVReader<Employee>{

    /**
     * Maps Employee (header, value) pairs to a value to change for the object.
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Employee modified with the value at the corresponding attribute.
     */
    @Override
    protected Employee parseAttribute(Employee object, String header, String value) {
        switch (header) {
            case "ID":
                object.setEmployeeID(Integer.parseInt(value));
                break;
            case "FirstName":
                object.setFirstName(value);
                break;
            case "LastName":
                object.setLastName(value);
                break;
            case "Email":
                object.setEmail(value);
                break;
            case "Role":
                object.setRole(Employee.Role.valueOf(value));
                break;
            case "IsAdmin":
                object.setAdmin(Boolean.parseBoolean(value));
                break;
            default:
                break;
        }
        return object;
    }

    /**
     * Wrapper for Location constructor.
     * @return Newly created Location Object.
     */
    @Override
    protected Employee createObject() {
        return new Employee();
    }
}
