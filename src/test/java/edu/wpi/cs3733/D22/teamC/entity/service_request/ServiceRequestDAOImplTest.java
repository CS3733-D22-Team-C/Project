package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRequestDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private ServiceRequestDAOImpl serviceRequestDAO;
    private ServiceRequestFactory<ServiceRequest> serviceRequestFactory;
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize Service_Request table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
    
        // Setup testing impl
        serviceRequestDAO = new ServiceRequestDAOImpl();

        serviceRequestFactory = new ServiceRequestFactory<ServiceRequest>();

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
     * Test that an empty Location Table DB returns nothing for queries.
     */
    @Test
    void testEmptyQueryLocation() {
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));
    }

    /**
     * Test that insertLocation works.
     */
    @Test
    public void testInsert() {
        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));

        // Insert Location into DB
        String creatorID = "C1";
        String assigneeID = "A1";
        String location = "loc";
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.Low;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.valueOf("Medical_Equipment");
        String description = "Move the bed before noon today";

        ServiceRequest insertSR = new ServiceRequest();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(location);
        insertSR.setCreationTimestamp(creationTimestamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);

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
    }

    /**
     * Test that deleteLocation works.
     */
    @Test
    public void testDelete() {
        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));

        // Insert ServiceRequest into DB
        String creatorID = "C1";
        String assigneeID = "A1";
        String location = "loc";
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.Low;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.valueOf("Medical_Equipment");
        String description = "Move the bed before noon today";

        ServiceRequest deleteSR = new ServiceRequest();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(location);
        deleteSR.setCreationTimestamp(creationTimestamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);

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
    public void testUpdate() {
        // Check DB is empty
        assertEquals(0, serviceRequestDAO.getAllServiceRequests().size());
        assertEquals(null, serviceRequestDAO.getServiceRequest(1234));

        // Insert ServiceRequest into DB
        String creatorID = "C1";
        String assigneeID = "A1";
        String location = "loc";
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "Move the bed before noon today";

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
        String newCreatorID = "C2";
        String newAssigneeID = "A2";
        String newlocation = "new loc";
        Timestamp newCreationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status newStatus = ServiceRequest.Status.Blank;
        ServiceRequest.Priority newPriority = ServiceRequest.Priority.High;
        String newDescription = "Move the bed IMMEDIATELY";
        String modifierID = "WillSmith";
        Timestamp modifiedTimestamp = new Timestamp(23098213);

        updateSR.setCreatorID(newCreatorID);
        updateSR.setAssigneeID(newAssigneeID);
        updateSR.setLocation(newlocation);
        updateSR.setCreationTimestamp(newCreationTimestamp);
        updateSR.setStatus(newStatus);
        updateSR.setPriority(newPriority);
        updateSR.setDescription(newDescription);
        updateSR.setModifierID(modifierID);
        updateSR.setModifiedTimestamp(modifiedTimestamp);
        
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
        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());
    
        // Cannot Update Nonexistent SR
        ServiceRequest newSR = new ServiceRequest(1234);
        assertFalse(serviceRequestDAO.updateServiceRequest(newSR));
    }
}