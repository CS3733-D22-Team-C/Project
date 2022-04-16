package edu.wpi.cs3733.D22.teamC.fileio.csv.employee;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

public class EmployeeCSVReader extends CSVReader<Employee> {

    /**
     * Maps Location (header, value) pairs to a value to change for the object.
     * @param object The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected Employee parseAttribute(Employee object, String header, String value) {
        switch (header) {
            case "ID":
                object.setID(value);
                break;
            case "FirstName":
                object.setFirstName(value);
                break;
            case "LastName":
                object.setLastName(value);
                break;
            case "EmailID":
                object.setEmailID(value);
                break;
            case "Phone":
                object.setPhone(value);
                break;
            case "Address":
                object.setAddress(value);
                break;
            case "Role":
                object.setRole(Employee.Role.valueOf(value));
                break;
            case "IsAdmin":
                object.setAdmin(Boolean.valueOf(value));
                break;
            case "Username":
                object.setUsername(value);
                break;
            case "Password":
                object.setPassword(value);
                break;
        }
        return object;
    }

    @Override
    protected Employee createObject() {
        return new Employee();
    }
}
