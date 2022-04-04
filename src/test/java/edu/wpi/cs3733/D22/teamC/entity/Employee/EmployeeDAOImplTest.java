package edu.wpi.cs3733.D22.teamC.entity.Employee;

import edu.wpi.cs3733.D22.teamC.DBManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOImplTest {
    private DBManager testManager;
    private EmployeeDAOImpl employeeDAO;

    @BeforeEach
    void SetUp(){
        //Settign up e Employee DB and initializing the EMPLOYEE table
        testManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        //Initializes the table
        testManager.initializeEmployeeTable(true);

        //SetUp testing the EmploeeDAOImpl
        employeeDAO = new EmployeeDAOImpl();
    }

    @AfterEach
    void tearDown(){
        //shutdown the testing DB
        DBManager.shutdown();
    }

    /**
     * to test that an empty employee table TB returns nothing for queries
     */
    @Test
    void testEmptyQueryEmployee(){
        assertEquals(0, employeeDAO.getAllEmployees().size());
        assertEquals(null, employeeDAO.getEmployee(1001));
    }

    @Test
    void testInsertEmployee(){
        //check if the DB is empty
        assertEquals(0, employeeDAO.getAllEmployees().size());
        assertEquals(null, employeeDAO.getEmployee(1001));

        String firstName = "Vishnu";
        String lastName = "Dendukuri";
        String email = "visshn***@gmail.com";
        Employee.Role role = Employee.Role.Doctor;
        Boolean isAdmin = false;

        Employee insertEmp = new Employee();
        insertEmp.setFirstName(firstName);
        insertEmp.setLastName(lastName);
        insertEmp.setRole(role);
        insertEmp.setEmail(email);
        insertEmp.setAdmin(isAdmin);

        int retrievedID = employeeDAO.insertEmployee(insertEmp);
        insertEmp.setEmployeeID(retrievedID);

        assertNotEquals(-1, retrievedID);
        assertEquals(1, employeeDAO.getAllEmployees().size());

        //cannot insert the same employee again
        assertEquals(-1, employeeDAO.insertEmployee(insertEmp));

        //check that the DB values are expected
        Employee queryEmp = employeeDAO.getEmployee(insertEmp.getEmployeeID());
        assertNotNull(queryEmp);
        assertEquals(retrievedID, queryEmp.getEmployeeID());
        assertEquals(firstName, queryEmp.getFirstName());
        assertEquals(lastName, queryEmp.getLastName());
        assertEquals(email, queryEmp.getEmail());
        assertEquals(role, queryEmp.getRole());
        assertEquals(isAdmin, queryEmp.isAdmin());

    }

    @Test
    void testDeleteEmployee(){
        //check if the DB is empty
        assertEquals(0, employeeDAO.getAllEmployees().size());
        assertEquals(null, employeeDAO.getEmployee(1001));

        String firstName = "Vishnu";
        String lastName = "Dendukuri";
        String email = "visshn***@gmail.com";
        Employee.Role role = Employee.Role.IT;
        Boolean isAdmin = false;

        Employee deleteEmp = new Employee();
        deleteEmp.setFirstName(firstName);
        deleteEmp.setLastName(lastName);
        deleteEmp.setEmail(email);
        deleteEmp.setRole(role);
        deleteEmp.setAdmin(isAdmin);

        int retrievedID = employeeDAO.insertEmployee(deleteEmp);
        deleteEmp.setEmployeeID(retrievedID);

        assertNotEquals(-1, retrievedID);
        assertEquals(1, employeeDAO.getAllEmployees().size());

        //deletign Employee from the database
        assertTrue(employeeDAO.deleteEmployee(deleteEmp));

        // Making sure we cannot delete it again when it is not in DB
        assertFalse(employeeDAO.deleteEmployee(deleteEmp));

        //check if the DB is empty after the deletion
        assertEquals(0, employeeDAO.getAllEmployees().size());
        assertEquals(null, employeeDAO.getEmployee(1001));
    }

    @Test
    void testUpdateEmployee(){
        //check if the DB is empty
        assertEquals(0, employeeDAO.getAllEmployees().size());
        assertEquals(null, employeeDAO.getEmployee(1001));

        // insert Employee into DB
        String firstName = "Vishnu";
        String lastName = "Dendukuri";
        String email = "visshn***@gmail.com";
        Employee.Role role = Employee.Role.IT;
        Boolean isAdmin = false;

        Employee updateEmp = new Employee();
        updateEmp.setFirstName(firstName);
        updateEmp.setLastName(lastName);
        updateEmp.setEmail(email);
        updateEmp.setRole(role);
        updateEmp.setAdmin(isAdmin);

        int retrievedID = employeeDAO.insertEmployee(updateEmp);
        updateEmp.setEmployeeID(retrievedID);

        assertNotEquals(-1,retrievedID);
        assertEquals(1, employeeDAO.getAllEmployees().size());

        //updating the previous employee
        String newFirstName = "Priya";
        String newLastName = "Kalidindi";
        String newEmail = "pri***@gmail.com";
        Employee.Role newRole = Employee.Role.Doctor;
        Boolean newIsAdmin = true;

        updateEmp.setFirstName(newFirstName);
        updateEmp.setLastName(newLastName);
        updateEmp.setEmail(newEmail);
        updateEmp.setRole(newRole);
        updateEmp.setAdmin(newIsAdmin);

        assertTrue(employeeDAO.updateEmployee(updateEmp));
        assertEquals(1, employeeDAO.getAllEmployees().size());

        //check that the DB values are expected
        Employee queryEmp = employeeDAO.getEmployee(updateEmp.getEmployeeID());
        assertNotNull(queryEmp);
        assertEquals(retrievedID, queryEmp.getEmployeeID());
        assertEquals(newFirstName, queryEmp.getFirstName());
        assertEquals(newLastName, queryEmp.getLastName());
        assertEquals(newEmail, queryEmp.getEmail());
        assertEquals(newRole, queryEmp.getRole());
        assertEquals(newIsAdmin, queryEmp.isAdmin());

        //Cannot update NonExistent Employee
        Employee newEmp = new Employee(1001);
        assertFalse(employeeDAO.updateEmployee(newEmp));

    }
}
