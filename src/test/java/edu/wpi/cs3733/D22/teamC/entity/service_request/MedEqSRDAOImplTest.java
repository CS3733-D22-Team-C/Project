package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedEqServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MedEqSRDAOImplTest {
    private DBManager testDBManager;
    private MedEqServiceRequestDAOImpl medicalEqDAO;
    
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize LOCATION table
        testDBManager = new DBManager(DBManager.TESTING_DATABASE_NAME, true);
        testDBManager.connectDatabase();
        testDBManager.initializeSRTable();
        testDBManager.initializeMedicalEquipSRTable();
        DBManager.setInstance(testDBManager);
        
        // Setup testing medicalEqDAOImpl
        medicalEqDAO = new MedEqServiceRequestDAOImpl();
    }
    
    @AfterEach
    void tearDown() {
        // Shutdown testing database
        DBManager.shutdown();
    }
    
    /**
     * Test that an empty Service Request Table DB returns nothing for queries.
     */
    @Test
    void testEmptyQuerySR() {
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest("Test000"));
    }
    
    /**
     * Test that insertLocation works.
     */
    @Test
    void testInsertSR() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest("Test000"));
        
        // Insert SR into DB
        String requestID = "Test000";
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        String status = "Not Done";
        String priority = "High";
        String requestType = "Medical Equipment";
        String description = "soft eng is spain without the s";
        String equipID = "BED003";
        String equipType = "Bed";
        
        ServiceRequest insertSR = new MedicalEquipmentServiceRequest();
        insertSR.setRequestID(requestID);
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        ((MedicalEquipmentServiceRequest) insertSR).setEquipmentID(equipID);
        ((MedicalEquipmentServiceRequest) insertSR).setEquipmentType(equipType);
        
        assertTrue(medicalEqDAO.insertServiceRequest(insertSR));
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
        
        // Cannot Insert SR Again
        assertFalse(medicalEqDAO.insertServiceRequest(insertSR));
        
        // Check that DB values are expected
        MedicalEquipmentServiceRequest querySR = medicalEqDAO.getServiceRequest(insertSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(requestID, querySR.getRequestID());
        assertEquals(creatorID, querySR.getCreatorID());
        assertEquals(assigneeID, querySR.getAssigneeID());
        assertEquals(locationID, querySR.getLocation());
        assertEquals(status, querySR.getStatus());
        assertEquals(priority, querySR.getPriority());
        assertEquals(requestType, querySR.getRequestType());
        assertEquals(description, querySR.getDescription());
        assertEquals(equipID, querySR.getEquipmentID());
        assertEquals(equipType, querySR.getEquipmentType());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest("Test000"));
    
        // Insert SR into DB
        String requestID = "Test000";
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        String status = "Not Done";
        String priority = "High";
        String requestType = "Medical Equipment";
        String description = "soft eng is spain without the s";
        String equipID = "BED003";
        String equipType = "Bed";
    
        ServiceRequest deleteSR = new MedicalEquipmentServiceRequest();
        deleteSR.setRequestID(requestID);
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        ((MedicalEquipmentServiceRequest) deleteSR).setEquipmentID(equipID);
        ((MedicalEquipmentServiceRequest) deleteSR).setEquipmentType(equipType);
        
        assertTrue(medicalEqDAO.insertServiceRequest(deleteSR));
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
        
        // Delete Location from DB
        assertTrue(medicalEqDAO.deleteServiceRequest(deleteSR));
        
        // Cannot Delete Location Again
        assertFalse(medicalEqDAO.deleteServiceRequest(deleteSR));
    }
}
