package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.Employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

public class EmployeeCSVWriter extends CSVWriter<Employee> {

    /**
     * Manually define headers of attributes output to CSV.
     * @return The array of headers to be output to CSV.
     */
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "ID",
                "FirstName",
                "LastName",
                "Email",
                "Role",
                "IsAdmin"
        };
    }

    /**
     * Maps headers to a value to get from the object.
     * @param object The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(Employee object, String header) {
        String output = "";
        switch (header) {
            case "ID":
                output = Integer.toString(object.getEmployeeID());
                break;
            case "FirstName":
                output = object.getFirstName();
                break;
            case "LastName":
                output = object.getLastName();
                break;
            case "Email":
                output = object.getEmail();
                break;
            case "Role":
                output = String.valueOf(object.getRole());
                break;
            case "IsAdmin":
                output = String.valueOf(object.isAdmin());
                break;
            default:
                break;
        }
        return output;
    }

}
