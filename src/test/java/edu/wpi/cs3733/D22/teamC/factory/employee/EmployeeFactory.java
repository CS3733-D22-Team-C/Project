package edu.wpi.cs3733.D22.teamC.factory.employee;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.factory.Factory;


import java.util.Random;

public class EmployeeFactory implements Factory<Employee> {
    private Random generator = new Random();

    public Employee create(){
        Employee employee = new Employee();

        String firstName = "Wilson";
        String lastName = "Wong";
        String email = "wwong2@wpi.edu";
        String phone  = "1321231312";
        String address = "100 institute road";
        Employee.Role role = Employee.Role.values()[generator.nextInt(Employee.Role.values().length)];
        Boolean isAdmin = generator.nextBoolean();
        String username = "wwong";
        String password = generateRandomString(8);

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmailID(email);
        employee.setPhone(phone);
        employee.setAddress(address);
        employee.setRole(role);
        employee.setAdmin(isAdmin);
        employee.setUsername(username);
        employee.setPassword(password);

        return employee;
    }

    public static String generateRandomString(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
