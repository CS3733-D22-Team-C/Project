package edu.wpi.cs3733.D22.teamC.entity.employee;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

public class EmployeeDAO extends DAO<Employee> {

    @Override
    protected Class<Employee> classType() {
        return Employee.class;
    }
}
