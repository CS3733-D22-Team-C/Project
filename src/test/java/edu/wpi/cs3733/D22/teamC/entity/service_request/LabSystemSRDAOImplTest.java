package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.factory.service_request.LabSystemSRFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabSystemSRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private LabSystemSRDAOImpl labSystemDAO;
    private LabSystemSRFactory labSystemSRFactory;
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeLabSystemSRTable(true);
        
        // Setup testing LabSystemSRDAOImpl
        labSystemDAO = new LabSystemSRDAOImpl();
        labSystemSRFactory = new LabSystemSRFactory();


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
        assertEquals(0, labSystemDAO.getAllServiceRequests().size());
        assertEquals(null, labSystemDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that insertLocation works.
     */
    @Test
    public void testInsert() {
        // Check DB is empty
        assertEquals(0, labSystemDAO.getAllServiceRequests().size());
        assertEquals(null, labSystemDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        LabSystemSR insertSR = labSystemSRFactory.create();
        int retrievedID = labSystemDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Cannot Insert SR Again
        assertEquals(-1, labSystemDAO.insertServiceRequest(insertSR));
        
        // Check that DB values are expected
        LabSystemSR querySR = labSystemDAO.getServiceRequest(insertSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(insertSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(insertSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(insertSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(insertSR.getLocation(), querySR.getLocation());
        assertEquals(insertSR.getCreationTimestamp(), querySR.getCreationTimestamp());
        assertEquals(insertSR.getStatus(), querySR.getStatus());
        assertEquals(insertSR.getPriority(), querySR.getPriority());
        assertEquals(insertSR.getRequestType(), querySR.getRequestType());
        assertEquals(insertSR.getDescription(), querySR.getDescription());
        assertEquals(insertSR.getLabType(), querySR.getLabType());
        assertEquals(insertSR.getPatientID(), querySR.getPatientID());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    public void testDelete() {
        // Check DB is empty
        assertEquals(0, labSystemDAO.getAllServiceRequests().size());
        assertEquals(null, labSystemDAO.getServiceRequest(1234));
        
        // Insert SR into DB
       LabSystemSR deleteSR = labSystemSRFactory.create();
        
        int retrievedID = labSystemDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Delete Location from DB
        assertTrue(labSystemDAO.deleteServiceRequest(deleteSR));
        
        // Cannot Delete Location Again
        assertFalse(labSystemDAO.deleteServiceRequest(deleteSR));
        
        // Check DB is empty
        assertEquals(0, labSystemDAO.getAllServiceRequests().size());
        assertEquals(null, labSystemDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that updateServiceRequest works.
     */
    @Test
    public void testUpdate() {
        // Check DB is empty
        assertEquals(0, labSystemDAO.getAllServiceRequests().size());
        assertEquals(null, labSystemDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        LabSystemSR updateSR = labSystemSRFactory.create();
        
        int retrievedID = labSystemDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        updateSR = labSystemSRFactory.create();
        updateSR.setRequestID(retrievedID);

        assertTrue(labSystemDAO.updateServiceRequest(updateSR));
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        LabSystemSR querySR = labSystemDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(updateSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(updateSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(updateSR.getLocation(), querySR.getLocation());
        //assertEquals(initialTS, querySR.getCreationTimestamp());
        assertEquals(updateSR.getStatus(), querySR.getStatus());
        assertEquals(updateSR.getPriority(), querySR.getPriority());
        assertEquals(updateSR.getRequestType(), querySR.getRequestType());
        assertEquals(updateSR.getDescription(), querySR.getDescription());
        assertEquals(updateSR.getLabType(), querySR.getLabType());
        assertEquals(updateSR.getPatientID(), querySR.getPatientID());
        assertEquals(updateSR.getModifierID(), querySR.getModifierID());
        assertEquals(updateSR.getModifiedTimestamp(), querySR.getModifiedTimestamp());
        
        // Cannot Update Nonexistent Location
        LabSystemSR newSR = new LabSystemSR();
        newSR.setRequestID(1234);
        assertFalse(labSystemDAO.updateServiceRequest(newSR));
    }
}
