package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MedEqSRDAOImplTest {
    private DBManager testDBManager;
    private MedicalEquipmentSRDAOImpl medicalEqDAO;
    
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize LOCATION table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeMedicalEquipSRTable(true);
        
        // Setup testing medicalEqDAOImpl
        medicalEqDAO = new MedicalEquipmentSRDAOImpl();
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

        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest("Test000"));
    }
    
    /**
     * Test that updateServiceRequest works.
     */
    @Test
    void testUpdateServiceRequest() {
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
    
        ServiceRequest updateSR = new MedicalEquipmentServiceRequest();
        updateSR.setRequestID(requestID);
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        ((MedicalEquipmentServiceRequest) updateSR).setEquipmentID(equipID);
        ((MedicalEquipmentServiceRequest) updateSR).setEquipmentType(equipType);
    
        assertTrue(medicalEqDAO.insertServiceRequest(updateSR));
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = "Done";
        priority = "idk man";
        requestType = "Medical Equipment";
        description = "help plz";
        equipID = "BED003";
        equipType = "Bed";
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        ((MedicalEquipmentServiceRequest) updateSR).setEquipmentID(equipID);
        ((MedicalEquipmentServiceRequest) updateSR).setEquipmentType(equipType);
        assertTrue(medicalEqDAO.updateServiceRequest(updateSR));
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
    
        // Check that DB values are expected
        MedicalEquipmentServiceRequest querySR = medicalEqDAO.getServiceRequest(updateSR.getRequestID());
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
        
        // Cannot Update Nonexistent Location
        ServiceRequest newSR = new MedicalEquipmentServiceRequest();
        newSR.setRequestID("Test001");
        assertFalse(medicalEqDAO.updateServiceRequest(newSR));
    }
}
