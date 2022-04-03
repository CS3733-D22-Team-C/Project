package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
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
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that insertLocation works.
     */
    @Test
    void testInsertSR() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        String equipID = "BED003";
        MedicalEquipmentSR.EquipmentType equipType = MedicalEquipmentSR.EquipmentType.Bed;
        
        MedicalEquipmentSR insertSR = new MedicalEquipmentSR();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setEquipmentID(equipID);
        insertSR.setEquipmentType(equipType);

        int retrievedID = medicalEqDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());

        // Cannot Insert SR Again
        assertEquals(-1, medicalEqDAO.insertServiceRequest(insertSR));

        // Check that DB values are expected
        MedicalEquipmentSR querySR = medicalEqDAO.getServiceRequest(insertSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(creatorID, querySR.getCreatorID());
        assertEquals(assigneeID, querySR.getAssigneeID());
        assertEquals(locationID, querySR.getLocation());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
    
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        String equipID = "BED003";
        MedicalEquipmentSR.EquipmentType equipType = MedicalEquipmentSR.EquipmentType.Bed;
    
        MedicalEquipmentSR deleteSR = new MedicalEquipmentSR();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setEquipmentID(equipID);
        deleteSR.setEquipmentType(equipType);

        int retrievedID = medicalEqDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());

        // Delete Location from DB
        assertTrue(medicalEqDAO.deleteServiceRequest(deleteSR));

        // Cannot Delete Location Again
        assertFalse(medicalEqDAO.deleteServiceRequest(deleteSR));

        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that updateServiceRequest works.
     */
    @Test
    void testUpdateServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
    
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        String equipID = "BED003";
        MedicalEquipmentSR.EquipmentType equipType = MedicalEquipmentSR.EquipmentType.Bed;
    
        MedicalEquipmentSR updateSR = new MedicalEquipmentSR();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setEquipmentID(equipID);
        updateSR.setEquipmentType(equipType);

        int retrievedID = medicalEqDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Medical_Equipment;
        description = "help plz";
        equipID = "BED003";
        equipType = MedicalEquipmentSR.EquipmentType.Bed;

        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setEquipmentID(equipID);
        updateSR.setEquipmentType(equipType);
        assertTrue(medicalEqDAO.updateServiceRequest(updateSR));
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());

        // Check that DB values are expected
        MedicalEquipmentSR querySR = medicalEqDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
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
        MedicalEquipmentSR newSR = new MedicalEquipmentSR();
        newSR.setRequestID(1234);
        assertFalse(medicalEqDAO.updateServiceRequest(newSR));
    }
}
