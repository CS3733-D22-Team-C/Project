package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class SanitationSRDAOImplTest {
    private DBManager testDBManager;
    private SanitationSRDAOImpl sanitationSRDAO;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeSanitationSRTable(true);

        // Setup testing SanitationSRDAOImpl
        sanitationSRDAO = new SanitationSRDAOImpl();
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
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));
    }

    /**
     * Test that insert Sanitation SR works.
     */
    @Test
    void testInsertSR() {
        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Sanitation;
        String description = "soft eng is spain without the s";
        SanitationSR.SanitationType sanitationType = SanitationSR.SanitationType.Biohazard;

        SanitationSR insertSR = new SanitationSR();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setSanitationType(sanitationType);

        int retrievedID = sanitationSRDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, sanitationSRDAO.getAllServiceRequests().size());

        // Cannot Insert SR Again
        assertEquals(-1, sanitationSRDAO.insertServiceRequest(insertSR));

        // Check that DB values are expected
        SanitationSR querySR = sanitationSRDAO.getServiceRequest(insertSR.getRequestID());
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
        assertEquals(sanitationType, querySR.getSanitationType());
    }

    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Sanitation;
        String description = "soft eng is spain without the s";
        SanitationSR.SanitationType sanitationType = SanitationSR.SanitationType.Daily_Cleaning;

        SanitationSR deleteSR = new SanitationSR();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setSanitationType(sanitationType);

        int retrievedID = sanitationSRDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, sanitationSRDAO.getAllServiceRequests().size());

        // Delete Sanitation from DB
        assertTrue(sanitationSRDAO.deleteServiceRequest(deleteSR));

        // Cannot Delete Sanitation Again
        assertFalse(sanitationSRDAO.deleteServiceRequest(deleteSR));

        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));
    }

    /**
     * Test that updateServiceRequest works.
     */
    @Test
    void testUpdateServiceRequest() {
        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Sanitation;
        String description = "soft eng is spain without the s";
        SanitationSR.SanitationType sanitationType = SanitationSR.SanitationType.General;

        SanitationSR updateSR = new SanitationSR();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setSanitationType(sanitationType);

        int retrievedID = sanitationSRDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, sanitationSRDAO.getAllServiceRequests().size());

        // Update Sanitation in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Sanitation;
        description = "help plz";
        sanitationType = SanitationSR.SanitationType.Biohazard;
        Timestamp modifiedTimestamp = new Timestamp(23098213);

        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setModifiedTimestamp(modifiedTimestamp);
        updateSR.setSanitationType(sanitationType);

        assertTrue(sanitationSRDAO.updateServiceRequest(updateSR));
        assertEquals(1, sanitationSRDAO.getAllServiceRequests().size());

        // Check that DB values are expected
        SanitationSR querySR = sanitationSRDAO.getServiceRequest(updateSR.getRequestID());
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
        assertEquals(sanitationType, querySR.getSanitationType());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());

        // Cannot Update Nonexistent Sanitation
        SanitationSR newSR = new SanitationSR();
        newSR.setRequestID(1234);
        assertFalse(sanitationSRDAO.updateServiceRequest(newSR));
    }
}
