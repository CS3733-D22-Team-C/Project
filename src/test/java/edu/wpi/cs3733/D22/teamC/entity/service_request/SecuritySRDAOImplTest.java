package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class SecuritySRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private SecuritySRDAO securityDAO;
    
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeSecuritySRTable(true);
        
        // Setup testing SecuritySRDAOImpl
        securityDAO = new SecuritySRDAOImpl();
    }
    
    @AfterEach
    void tearDown() {
        // Shutdown testing database
        DBManager.shutdown();
    }

    @Test
    public void getAll() {
        //TODO
    }

    @Test
    public void testGetByID() {
        //TODO
    }
    
    /**
     * Test that an empty Service Request Table DB returns nothing for queries.
     */
    @Test
    void testEmptyQuerySR() {
        assertEquals(0, securityDAO.getAllServiceRequests().size());
        assertEquals(null, securityDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that insertLocation works.
     */
    @Test
    public void testInsert() {
        // Check DB is empty
        assertEquals(0, securityDAO.getAllServiceRequests().size());
        assertEquals(null, securityDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;
        String description = "soft eng is spain without the s";
        SecuritySR.SecurityType securityType = SecuritySR.SecurityType.Intruder;
        
        SecuritySR insertSR = new SecuritySR();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setSecurityType(securityType);
        
        int retrievedID = securityDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, securityDAO.getAllServiceRequests().size());
        
        // Cannot Insert SR Again
        assertEquals(-1, securityDAO.insertServiceRequest(insertSR));
        
        // Check that DB values are expected
        SecuritySR querySR = securityDAO.getServiceRequest(insertSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(creatorID, querySR.getCreatorID());
        assertEquals(assigneeID, querySR.getAssigneeID());
        assertEquals(locationID, querySR.getLocation());
        assertEquals(creationTimeStamp, querySR.getCreationTimestamp());
        assertEquals(status, querySR.getStatus());
        assertEquals(priority, querySR.getPriority());
        assertEquals(requestType, querySR.getRequestType());
        assertEquals(description, querySR.getDescription());
        assertEquals(securityType, querySR.getSecurityType());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    public void testDelete() {
        // Check DB is empty
        assertEquals(0, securityDAO.getAllServiceRequests().size());
        assertEquals(null, securityDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;
        String description = "soft eng is spain without the s";
        SecuritySR.SecurityType securityType = SecuritySR.SecurityType.Intruder;
        
        SecuritySR deleteSR = new SecuritySR();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setSecurityType(securityType);
        
        int retrievedID = securityDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, securityDAO.getAllServiceRequests().size());
        
        // Delete Location from DB
        assertTrue(securityDAO.deleteServiceRequest(deleteSR));
        
        // Cannot Delete Location Again
        assertFalse(securityDAO.deleteServiceRequest(deleteSR));
        
        // Check DB is empty
        assertEquals(0, securityDAO.getAllServiceRequests().size());
        assertEquals(null, securityDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that updateServiceRequest works.
     */
    @Test
    public void testUpdate() {
        // Check DB is empty
        assertEquals(0, securityDAO.getAllServiceRequests().size());
        assertEquals(null, securityDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        SecuritySR.SecurityType securityType = SecuritySR.SecurityType.Intruder;
        
        SecuritySR updateSR = new SecuritySR();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setSecurityType(securityType);
        
        int retrievedID = securityDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, securityDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Security;
        description = "help plz";
        securityType = SecuritySR.SecurityType.Intruder;
        String modifierID = "WillSmith";
        Timestamp modifiedTimestamp = new Timestamp(23098213);
        
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setModifierID(modifierID);
        updateSR.setModifiedTimestamp(modifiedTimestamp);
        updateSR.setSecurityType(securityType);
        assertTrue(securityDAO.updateServiceRequest(updateSR));
        assertEquals(1, securityDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        SecuritySR querySR = securityDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(creatorID, querySR.getCreatorID());
        assertEquals(assigneeID, querySR.getAssigneeID());
        assertEquals(locationID, querySR.getLocation());
        assertEquals(creationTimeStamp, querySR.getCreationTimestamp());
        assertEquals(status, querySR.getStatus());
        assertEquals(priority, querySR.getPriority());
        assertEquals(requestType, querySR.getRequestType());
        assertEquals(description, querySR.getDescription());
        assertEquals(securityType, querySR.getSecurityType());
        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());
        
        // Cannot Update Nonexistent Location
        SecuritySR newSR = new SecuritySR();
        newSR.setRequestID(1234);
        assertFalse(securityDAO.updateServiceRequest(newSR));
    }
}
