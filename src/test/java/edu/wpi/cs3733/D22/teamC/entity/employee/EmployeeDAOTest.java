package edu.wpi.cs3733.D22.teamC.entity.employee;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.employee.EmployeeFactory;

public class EmployeeDAOTest extends DAOTest<Employee> {
    public EmployeeDAOTest() {
        super(new EmployeeFactory(), new EmployeeDAO());
    }
}
