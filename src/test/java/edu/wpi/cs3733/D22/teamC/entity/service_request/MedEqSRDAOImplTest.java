package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MedEqSRDAOImplTest {
    private DBManager testDBManager;
    private MedicalEquipmentSRDAOImpl medicalEquipmentSRDAO;
    
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize Medical_Equipment table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeMedicalEquipSRTable(true);
        
        // Setup testing medicalEquipmentSRDAOImpl
        medicalEquipmentSRDAO = new MedicalEquipmentSRDAOImpl();
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
        assertEquals(0, medicalEquipmentSRDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEquipmentSRDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that insertSR works.
     */
    @Test
    void testInsertSR() {
        // Check DB is empty
        assertEquals(0, medicalEquipmentSRDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEquipmentSRDAO.getServiceRequest(1234));



        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        int equipID = 356;
        
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

        int retrievedID = medicalEquipmentSRDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentSRDAO.getAllServiceRequests().size());

        // Cannot Insert SR Again
        assertEquals(-1, medicalEquipmentSRDAO.insertServiceRequest(insertSR));

        // Check that DB values are expected
        MedicalEquipmentSR querySR = medicalEquipmentSRDAO.getServiceRequest(insertSR.getRequestID());
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
        assertEquals(equipID, querySR.getEquipmentID());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicalEquipmentSRDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEquipmentSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        int equipID = 356;

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

        int retrievedID = medicalEquipmentSRDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentSRDAO.getAllServiceRequests().size());

        // Delete Location from DB
        assertTrue(medicalEquipmentSRDAO.deleteServiceRequest(deleteSR));

        // Cannot Delete Location Again
        assertFalse(medicalEquipmentSRDAO.deleteServiceRequest(deleteSR));

        // Check DB is empty
        assertEquals(0, medicalEquipmentSRDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEquipmentSRDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that updateServiceRequest works.
     */
    @Test
    void testUpdateServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicalEquipmentSRDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEquipmentSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        int equipID = 356;

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

        int retrievedID = medicalEquipmentSRDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentSRDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Medical_Equipment;
        description = "help plz";

        equipID = 356;


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
        updateSR.setEquipmentID(equipID);
        assertTrue(medicalEquipmentSRDAO.updateServiceRequest(updateSR));
        assertEquals(1, medicalEquipmentSRDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        MedicalEquipmentSR querySR = medicalEquipmentSRDAO.getServiceRequest(updateSR.getRequestID());
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

        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());

        // Cannot Update Nonexistent Location
        MedicalEquipmentSR newSR = new MedicalEquipmentSR();
        newSR.setRequestID(1234);
        assertFalse(medicalEquipmentSRDAO.updateServiceRequest(newSR));
    }
}
