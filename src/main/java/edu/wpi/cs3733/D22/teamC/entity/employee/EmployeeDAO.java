package edu.wpi.cs3733.D22.teamC.entity.employee;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

import java.util.List;

public class EmployeeDAO extends DAO<Employee> {

    @Override
    protected Class<Employee> classType() {
        return Employee.class;
    }

    protected Employee getEmployeeByUsername(String username) {
        List<Employee> employee = HibernateManager.filterQuery("select u from " + classType().getName() + " u where a.username = '" + username + "'");
        if (employee.size() == 1) {
            return employee.get(0);
        }
        else {
            return null;
        }
    }
}
