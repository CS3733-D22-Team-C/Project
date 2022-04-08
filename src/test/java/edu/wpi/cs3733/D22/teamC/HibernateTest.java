package edu.wpi.cs3733.D22.teamC;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HibernateTest {
    
    private LocationDAO locationDAO;
	private EmployeeDAO employeeDAO;

	@BeforeEach
	void setUp() {
        SessionManager.getSession();
        locationDAO = new LocationDAO();
		employeeDAO = new EmployeeDAO();
    }

	@AfterEach
	void tearDown() {
        // Kill Session Factory after every test. Note: this will drop the tables!
        SessionManager.killSessionFactory();
    }

    //region Location Tests
	@Test
	void testInsertLocation() {
        // Insert a new object into the database
		Location newLoc = new Location();
        int insertedID = locationDAO.insert(newLoc);
        assertNotEquals(-1, insertedID);
	}

	@Test
	void testGetLocation() {
		// Insert a new object into the database
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setNodeType(Location.NodeType.STOR);
		newLoc.setLongName("YourMom69");
		newLoc.setX(1000);
		newLoc.setY(1000);
		int insertedID = locationDAO.insert(newLoc);
        assertNotEquals(-1, insertedID);

		// Retrieve the object
        Location retrievedLoc = locationDAO.getByID(insertedID);
		assertNotNull(retrievedLoc);

		// Verify attributes
		assertEquals(insertedID, retrievedLoc.getNodeID());
		assertEquals(newLoc.getBuilding(), retrievedLoc.getBuilding());
		assertEquals(newLoc.getNodeType(), retrievedLoc.getNodeType());
		assertEquals(newLoc.getLongName(), retrievedLoc.getLongName());
		assertEquals(newLoc.getX(), retrievedLoc.getX());
		assertEquals(newLoc.getY(), retrievedLoc.getY());
	}

    @Test
    void testGetLocations() {
        // Insert a new object into the database
        Location newLoc = new Location();
        newLoc.setBuilding("TOWER");
        newLoc.setNodeType(Location.NodeType.STOR);
        newLoc.setLongName("YourMom69");
        newLoc.setX(1000);
        newLoc.setY(1000);
        int insertedID = locationDAO.insert(newLoc);
        assertNotEquals(-1, insertedID);

        locationDAO.insert(new Location());
        locationDAO.insert(new Location());

        // Get All
        List<Location> getList = locationDAO.getAll();
        System.out.println(getList.size());
    }

	@Test
	void testUpdateLocation() {
		// Insert a new object into the database
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setNodeType(Location.NodeType.STOR);
		newLoc.setLongName("YourMom69");
		newLoc.setX(1000);
		newLoc.setY(1000);
		int insertedID = locationDAO.insert(newLoc);

		// Retrieve the object
		Location retrievedLoc = locationDAO.getByID(insertedID);
		assertNotNull(retrievedLoc);

		// Update attributes
		Location.NodeType newNodeType = Location.NodeType.LABS;
		String newLongName = "NotYourMom";
		String newShortName = "NYM";
		int newX = 500;
		int newY = 500;

		retrievedLoc.setNodeID(insertedID);
		retrievedLoc.setNodeType(newNodeType);
		retrievedLoc.setLongName(newLongName);
		retrievedLoc.setShortName(newShortName);
		retrievedLoc.setX(newX);
		retrievedLoc.setY(newY);
		boolean success = locationDAO.update(retrievedLoc);
        assertTrue(success);

		// Verify attributes
		assertEquals(newNodeType, retrievedLoc.getNodeType());
		assertEquals(newLongName, retrievedLoc.getLongName());
		assertEquals(newShortName, retrievedLoc.getShortName());
		assertEquals(newX, retrievedLoc.getX());
		assertEquals(newY, retrievedLoc.getY());
	}

	@Test
	void testDeleteLocation() {
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setLongName("deleteMe");

		int insertedID = locationDAO.insert(newLoc);
        assertNotEquals(-1, insertedID);

		boolean success = HibernateManager.deleteObj(newLoc);
        assertTrue(success);
	}
    //endregion

    @Test
    void testInsertSR() {
        // Insert a new object into the database
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;
        String description = "soft eng is spain without the s";

        SecuritySR insertSR = new SecuritySR();
        //ServiceRequest insertSR = new ServiceRequest();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setSecurityType(SecuritySR.SecurityType.Intruder);

        int insertedID = HibernateManager.insertObj(insertSR);
        assertNotEquals(-1, insertedID);
    }

	//region Employee Tests

	@Test
	void testInsertEmployee(){
		// Insert a new object into the database
		Employee newEmp = new Employee();
		int insertEmpID = employeeDAO.insert(newEmp);
		assertNotEquals(-1,insertEmpID);
	}

	@Test
	void testGetEmployees(){
		Employee emp = new Employee();
		emp.setFirstName("Vishnu Priya");
		emp.setLastName("Dendukuri");
		emp.setEmailID("vdendu****@wpi.edu");
		emp.setPhone("1321231312"); // TODO: we have to try to lmit phone but if international (how to handle?)
		emp.setAddress("100 institute");
		emp.setRole(Employee.Role.Doctor);
		emp.setAdmin(true);
		emp.setUsername("vdendu");
		emp.setPassword("vdendu"); // TODO: set password requirements
		int insertedEmpID = employeeDAO.insert(emp);
		assertNotEquals(-1, insertedEmpID);

		Employee retrievedEmp = employeeDAO.getByID(insertedEmpID);
		assertNotNull(retrievedEmp);

		assertEquals(insertedEmpID, retrievedEmp.getEmployeeID());
		assertEquals(emp.getFirstName(), retrievedEmp.getFirstName());
		assertEquals(emp.getLastName(), retrievedEmp.getLastName());
		assertEquals(emp.getEmailID(), retrievedEmp.getEmailID());
		assertEquals(emp.getPhone(), retrievedEmp.getPhone());
		assertEquals(emp.getAddress(), retrievedEmp.getAddress());
		assertEquals(emp.getRole(), retrievedEmp.getRole());
		assertEquals(emp.getAdmin(), retrievedEmp.getAdmin());
		assertEquals(emp.getUsername(), retrievedEmp.getUsername());
		assertEquals(emp.getPassword(), retrievedEmp.getPassword());
	}

	@Test
	void GetEmployees(){
		Employee emp = new Employee();
		emp.setFirstName("Vishnu Priya");
		emp.setLastName("Dendukuri");
		emp.setEmailID("vdendu****@wpi.edu");
		emp.setPhone("1321231312"); // TODO: we have to try to lmit phone but if international (how to handle?)
		emp.setAddress("100 institute");
		emp.setRole(Employee.Role.Doctor);
		emp.setAdmin(true);
		emp.setUsername("vdendu");
		emp.setPassword("vdendu"); // TODO: set password requirements
		int insertedEmpID = employeeDAO.insert(emp);
		assertNotEquals(-1, insertedEmpID);

		employeeDAO.insert(new Employee());
		employeeDAO.insert(new Employee());

		List<Employee> employeesList = employeeDAO.getAll();
		System.out.println(employeesList.size());
		assertEquals(3, employeesList.size());

	}

	@Test
	void testUpdateEmployee(){
		// Insert a new object into the database
		Employee emp = new Employee();

		emp.setFirstName("Vishnu Priya");
		emp.setLastName("Dendukuri");
		emp.setEmailID("vdendu****@wpi.edu");
		emp.setPhone("1321231312"); // TODO: we have to try to lmit phone but if international (how to handle?)
		emp.setAddress("100 institute");
		emp.setRole(Employee.Role.Doctor);
		emp.setAdmin(true);
		emp.setUsername("vdendu");
		emp.setPassword("vdendu"); // TODO: set password requirements
		int insertedEmpID = employeeDAO.insert(emp);
		assertNotEquals(-1, insertedEmpID);

		Employee retrievedEmp = employeeDAO.getByID(insertedEmpID);
		assertNotNull(retrievedEmp);
		String newFN = "Vishnu";
		String newLN = "SRK";
		String newEmail = "dasdsa@dasd.com"; // TODO: verifying email?
		Employee.Role newRole = Employee.Role.Nurse;
		String newPhone = "23131819332";
		String newAddress = "212 Institute";
		String newUsername = "vpd";
		String newPassword = "vpd@201002";
		Boolean newAdmin = false;

		retrievedEmp.setEmployeeID(insertedEmpID);
		retrievedEmp.setFirstName(newFN);
		retrievedEmp.setLastName(newLN);
		retrievedEmp.setEmailID(newEmail);
		retrievedEmp.setPhone(newPhone);
		retrievedEmp.setAddress(newAddress);
		retrievedEmp.setRole(newRole);
		retrievedEmp.setUsername(newUsername);
		retrievedEmp.setPassword(newPassword);


		boolean success = employeeDAO.update(retrievedEmp);
		assertTrue(success);

		// Verify attributes

		assertEquals(newFN, retrievedEmp.getFirstName());
		assertEquals(newLN, retrievedEmp.getLastName());
		assertEquals(newEmail, retrievedEmp.getEmailID());
		assertEquals(newPhone, retrievedEmp.getPhone());
		assertEquals(newAddress, retrievedEmp.getAddress());
		assertEquals(newRole, retrievedEmp.getRole());
		assertEquals(newAdmin, retrievedEmp.getAdmin());
		assertEquals(newUsername, retrievedEmp.getUsername());
		assertEquals(newPassword, retrievedEmp.getPassword());

	}

	@Test
	void testDeleteEmployee() {
		Employee newEmp = new Employee();
		newEmp.setFirstName("Grace");
		newEmp.setRole(Employee.Role.Doctor);
		newEmp.setAdmin(true);

		int insertedEmpID = employeeDAO.insert(newEmp);
		assertNotEquals(-1, insertedEmpID);

		boolean success = HibernateManager.deleteObj(newEmp);
		assertTrue(success);
	}


}
