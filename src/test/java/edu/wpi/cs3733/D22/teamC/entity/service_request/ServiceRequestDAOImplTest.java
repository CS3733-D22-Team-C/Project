package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRequestDAOImplTest {
    private DBManager testDBManager;
    private ServiceRequestDAOImpl serviceRequestDAO;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize LOCATION table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeEmployeeTable(true);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeMedicalEquipSRTable(true);
    
        // Setup testing LocationDAOImpl
        serviceRequestDAO = new ServiceRequestDAOImpl();
    }

    @AfterEach
    void tearDown() {
        // Shutdown testing database
        DBManager.shutdown();
    }

    /**
     * Test that an empty Location Table DB returns nothing for queries.
     */
    @Test
    void testEmptyQueryServiceRequest() {
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));
    }

    /**
     * Test that insertLocation works.
     */
    @Test
    void testInsertServiceRequest() {
        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));

        // Insert Location into DB
        int creatorID = 1;
        int assigneeID = 2;
        String location = "loc";
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.Low;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.valueOf("Medical_Equipment");
        String description = "Move the bed before noon today";
        String modifierID = "123";
        Timestamp creationTimestampModifier = new Timestamp(System.currentTimeMillis());

        ServiceRequest insertSR = new ServiceRequest();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(location);
        insertSR.setCreationTimestamp(creationTimestamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setModifierID(modifierID);
        insertSR.setModifiedTimestamp(creationTimestampModifier);

        int retrievedID = serviceRequestDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Cannot Insert Service Request Again
        assertEquals(-1, serviceRequestDAO.insertServiceRequest(insertSR));

        // Check that DB values are expected
        ServiceRequest querySR = serviceRequestDAO.getServiceRequest(insertSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(creatorID, querySR.getCreatorID());
        assertEquals(assigneeID, querySR.getAssigneeID());
        assertEquals(location, querySR.getLocation());
        assertEquals(creationTimestamp, querySR.getCreationTimestamp());
        assertEquals(status, querySR.getStatus());
        assertEquals(priority, querySR.getPriority());
        assertEquals(requestType, querySR.getRequestType());
        assertEquals(description, querySR.getDescription());
        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(creationTimestampModifier, querySR.getModifiedTimestamp());

    }

    /**
     * Test that deleteLocation works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));

        // Insert ServiceRequest into DB
        int creatorID = 1;
        int assigneeID = 2;
        String location = "loc";
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.Low;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.valueOf("Medical_Equipment");
        String description = "Move the bed before noon today";
        String modifierID = "123";
        Timestamp creationTimestampModifier = new Timestamp(System.currentTimeMillis());

        ServiceRequest deleteSR = new ServiceRequest();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(location);
        deleteSR.setCreationTimestamp(creationTimestamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setModifierID(modifierID);
        deleteSR.setModifiedTimestamp(creationTimestampModifier);

        int retrievedID = serviceRequestDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Delete SR from DB
        assertTrue(serviceRequestDAO.deleteServiceRequest(deleteSR));

        // Cannot Delete SR Again
        assertFalse(serviceRequestDAO.deleteServiceRequest(deleteSR));

        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));
    }

    /**
     * Test that updateLocation works.
     */
    @Test
    void testUpdateServiceRequest() {
        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));

        // Insert ServiceRequest into DB
        int creatorID = 1;
        int assigneeID = 2;
        String location = "loc";
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "Move the bed before noon today";
        String modifierID = "123";
        Timestamp creationTimestampModifier = new Timestamp(System.currentTimeMillis());

        ServiceRequest updateSR = new ServiceRequest();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(location);
        updateSR.setCreationTimestamp(creationTimestamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);

        int retrievedID = serviceRequestDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Update Location in DB
        // didn't update the requestType

        int newCreatorID = 1;
        int newAssigneeID = 1;

        String newlocation = "new loc";
        Timestamp newCreationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status newStatus = ServiceRequest.Status.Blank;
        ServiceRequest.Priority newPriority = ServiceRequest.Priority.High;
        String newDescription = "Move the bed IMMEDIATELY";
        String newModifierID = "WillSmith";
        Timestamp newModifiedTimestamp = new Timestamp(23098213);

        updateSR.setCreatorID(newCreatorID);
        updateSR.setAssigneeID(newAssigneeID);
        updateSR.setLocation(newlocation);
        updateSR.setCreationTimestamp(newCreationTimestamp);
        updateSR.setStatus(newStatus);
        updateSR.setPriority(newPriority);
        updateSR.setDescription(newDescription);
        updateSR.setModifierID(newModifierID);
        updateSR.setModifiedTimestamp(newModifiedTimestamp);
        
        assertTrue(serviceRequestDAO.updateServiceRequest(updateSR));
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Check that DB values are expected
        ServiceRequest querySR = serviceRequestDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(newCreatorID, querySR.getCreatorID());
        assertEquals(newAssigneeID, querySR.getAssigneeID());
        assertEquals(newlocation, querySR.getLocation());
        assertEquals(newCreationTimestamp, querySR.getCreationTimestamp());
        assertEquals(newStatus, querySR.getStatus());
        assertEquals(newPriority, querySR.getPriority());
        assertEquals(requestType, querySR.getRequestType());
        assertEquals(newDescription, querySR.getDescription());
        assertEquals(newModifierID, querySR.getModifierID());
        assertEquals(newModifiedTimestamp, querySR.getModifiedTimestamp());
    
        // Cannot Update Nonexistent SR
        ServiceRequest newSR = new ServiceRequest(1234);
        assertFalse(serviceRequestDAO.updateServiceRequest(newSR));
    }
}