package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAOImpl;
import edu.wpi.cs3733.D22.teamC.factory.service_request.SecuritySRFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class SecuritySRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private SecuritySRDAO securityDAO;
    private SecuritySRFactory securitySRFactory;
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeSecuritySRTable(true);
        
        // Setup testing SecuritySRDAOImpl
        securityDAO = new SecuritySRDAOImpl();

        securitySRFactory = new SecuritySRFactory();
    }
    
    @AfterEach
    void tearDown() {
        // Shutdown testing database
        DBManager.shutdown();
    }

    @Test
    public void testGetAll() {
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
        SecuritySR insertSR = securitySRFactory.create();
        
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
        assertEquals(insertSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(insertSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(insertSR.getLocation(), querySR.getLocation());
        assertEquals(insertSR.getCreationTimestamp(), querySR.getCreationTimestamp());
        assertEquals(insertSR.getStatus(), querySR.getStatus());
        assertEquals(insertSR.getPriority(), querySR.getPriority());
        assertEquals(insertSR.getRequestType(), querySR.getRequestType());
        assertEquals(insertSR.getDescription(), querySR.getDescription());
        assertEquals(insertSR.getSecurityType(), querySR.getSecurityType());
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
        SecuritySR deleteSR = securitySRFactory.create();
        
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
        SecuritySR updateSR = securitySRFactory.create();
        Timestamp initialTS = updateSR.getCreationTimestamp();
        int retrievedID = securityDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, securityDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        updateSR = securitySRFactory.create();
        updateSR.setRequestID(retrievedID);

        assertTrue(securityDAO.updateServiceRequest(updateSR));
        assertEquals(1, securityDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        SecuritySR querySR = securityDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(updateSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(updateSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(updateSR.getLocation(), querySR.getLocation());
  //      assertEquals(initialTS, querySR.getCreationTimestamp());
        assertEquals(updateSR.getStatus(), querySR.getStatus());
        assertEquals(updateSR.getPriority(), querySR.getPriority());
        assertEquals(updateSR.getRequestType(), querySR.getRequestType());
        assertEquals(updateSR.getDescription(), querySR.getDescription());
        assertEquals(updateSR.getSecurityType(), querySR.getSecurityType());
        assertEquals(updateSR.getModifierID(), querySR.getModifierID());
        assertEquals(updateSR.getModifiedTimestamp(), querySR.getModifiedTimestamp());
        
        // Cannot Update Nonexistent Location
        SecuritySR newSR = new SecuritySR();
        newSR.setRequestID(1234);
        assertFalse(securityDAO.updateServiceRequest(newSR));
    }
}
