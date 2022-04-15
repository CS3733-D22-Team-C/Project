package edu.wpi.cs3733.D22.teamC.fileio.csv.employee;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class EmployeeCSVWriter extends CSVWriter<Employee> {
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "ID",
                "FirstName",
                "LastName",
                "EmailID",
                "Phone",
                "Address",
                "Role",
                "IsAdmin",
                "Username",
                "Password"
        };
    }

    @Override
    protected String compileAttribute(Employee object, String header) {
        String output = "";
        switch (header){
            case "ID":
                output = object.getID();
                break;
            case "FirstName":
                output = object.getFirstName();
                break;
            case "LastName":
                output = object.getLastName();
                break;
            case "EmailID":
                output = object.getEmailID();
                break;
            case "Phone":
                output = object.getPhone();
                break;
            case "Address":
                output = object.getAddress();
                break;
            case "Role":
                output = object.getRole().toString();
                break;
            case "IsAdmin":
                output = Boolean.toString(object.getAdmin());
                break;
            case "Username":
                output = object.getUsername();
                break;
            case "Password":
                output = object.getPassword();
                break;

        }
        return output;
    }
}
