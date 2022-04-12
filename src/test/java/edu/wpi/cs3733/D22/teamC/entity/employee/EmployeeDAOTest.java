package edu.wpi.cs3733.D22.teamC.entity.employee;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.employee.EmployeeFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EmployeeDAOTest extends DAOTest<Employee> {
    public EmployeeDAOTest() {
        super(new EmployeeFactory(), new EmployeeDAO());
    }

    @Test
    void testGetByUsername() {
        Employee employee = factory.create();

        String id = dao.insert(employee);

        assertNotNull(id);

        assertEquals(employee, ((EmployeeDAO) dao).getEmployeeByUsername(employee.getUsername()));

    }
}
