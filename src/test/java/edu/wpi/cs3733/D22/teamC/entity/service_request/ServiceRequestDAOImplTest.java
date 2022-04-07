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

        ServiceRequest sR = serviceRequestFactory.create(new ServiceRequest());
        sR.setRequestType(ServiceRequest.RequestType.Medical_Equipment);

        int retrievedID = serviceRequestDAO.insertServiceRequest(sR);
        sR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Cannot Insert Service Request Again
        assertEquals(-1, serviceRequestDAO.insertServiceRequest(sR));

        // Check that DB values are expected
        ServiceRequest querySR = serviceRequestDAO.getServiceRequest(sR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(sR.getCreatorID(), querySR.getCreatorID());
        assertEquals(sR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(sR.getLocation(), querySR.getLocation());
        assertEquals(sR.getCreationTimestamp(), querySR.getCreationTimestamp());
        assertEquals(sR.getStatus(), querySR.getStatus());
        assertEquals(sR.getPriority(), querySR.getPriority());
        assertEquals(sR.getRequestType(), querySR.getRequestType());
        assertEquals(sR.getDescription(), querySR.getDescription());
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
        ServiceRequest sR = serviceRequestFactory.create();
        sR.setRequestType(ServiceRequest.RequestType.Medical_Equipment);

        int retrievedID = serviceRequestDAO.insertServiceRequest(sR);
        sR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Delete SR from DB
        assertTrue(serviceRequestDAO.deleteServiceRequest(sR));

        // Cannot Delete SR Again
        assertFalse(serviceRequestDAO.deleteServiceRequest(sR));

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
        ServiceRequest sR = serviceRequestFactory.create();
        sR.setRequestType(ServiceRequest.RequestType.Medical_Equipment);
        Timestamp initialTS = sR.getCreationTimestamp();

        int retrievedID = serviceRequestDAO.insertServiceRequest(sR);
        sR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Update Service Request in DB
        // didn't update the requestType
        sR = serviceRequestFactory.create();
        sR.setRequestID(retrievedID);
        sR.setRequestType(ServiceRequest.RequestType.Medical_Equipment);

        assertTrue(serviceRequestDAO.updateServiceRequest(sR));
        assertEquals(1, serviceRequestDAO.getAllServiceRequests().size());

        // Check that DB values are expected
        ServiceRequest querySR = serviceRequestDAO.getServiceRequest(sR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(sR.getCreatorID(), querySR.getCreatorID());
        assertEquals(sR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(sR.getLocation(), querySR.getLocation());
   //    assertEquals(initialTS, querySR.getCreationTimestamp());
        assertEquals(sR.getStatus(), querySR.getStatus());
        assertEquals(sR.getPriority(), querySR.getPriority());
        assertEquals(sR.getRequestType(), querySR.getRequestType());
        assertEquals(sR.getDescription(), querySR.getDescription());
        assertEquals(sR.getModifierID(), querySR.getModifierID());
        assertEquals(sR.getModifiedTimestamp(), querySR.getModifiedTimestamp());
    
        // Cannot Update Nonexistent SR
        ServiceRequest newSR = new ServiceRequest(1234);
        assertFalse(serviceRequestDAO.updateServiceRequest(newSR));
    }
}