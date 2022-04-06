package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAO;

import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class FacilityMaintenanceSRDAOImplTest {

    private DBManager testDBManager;
    private FacilityMaintenanceSRDAO facilityDAO;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeSecuritySRTable(true);

        // Setup testing SecuritySRDAOImpl
        facilityDAO = new FacilityMaintenanceSRDAOImpl();
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
        assertEquals(0, facilityDAO.getAllServiceRequests().size());
        assertEquals(null, facilityDAO.getServiceRequest(1234));
    }

    /**
     * Test that insertLocation works.
     */
    @Test
    void testInsertSR() {
        // Check DB is empty
        assertEquals(0, facilityDAO.getAllServiceRequests().size());
        assertEquals(null, facilityDAO.getServiceRequest(1234));


        int creatorID = 1;
        int assigneeID = 2;
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;
        String description = "soft eng is spain without the s";
        FacilityMaintenanceSR.MaintenanceType maintenanceType = FacilityMaintenanceSR.MaintenanceType.Cleaning;

        FacilityMaintenanceSR insertSR = new FacilityMaintenanceSR();
       //insertSR.setCreatorID(creatorID);
       // insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setMaintenanceType(maintenanceType);

        int retrievedID = facilityDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, facilityDAO.getAllServiceRequests().size());

        // Cannot Insert SR Again
        assertEquals(-1, facilityDAO.insertServiceRequest(insertSR));

        // Check that DB values are expected
        FacilityMaintenanceSR querySR = facilityDAO.getServiceRequest(insertSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        //assertEquals(creatorID, querySR.getCreatorID());
        //assertEquals(assigneeID, querySR.getAssigneeID());
        assertEquals(locationID, querySR.getLocation());
        assertEquals(creationTimeStamp, querySR.getCreationTimestamp());
        assertEquals(status, querySR.getStatus());
        assertEquals(priority, querySR.getPriority());
        assertEquals(requestType, querySR.getRequestType());
        assertEquals(description, querySR.getDescription());
        assertEquals(maintenanceType, querySR.getMaintenanceType());
    }

    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, facilityDAO.getAllServiceRequests().size());
        assertEquals(null, facilityDAO.getServiceRequest(1234));

        // Insert SR into DB
        int creatorID = 1;
        int assigneeID = 2;
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;
        String description = "soft eng is spain without the s";
        FacilityMaintenanceSR.MaintenanceType maintenanceType = FacilityMaintenanceSR.MaintenanceType.Cleaning;

        FacilityMaintenanceSR deleteSR = new FacilityMaintenanceSR();
        //deleteSR.setCreatorID(creatorID);
        //deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setMaintenanceType(maintenanceType);

        int retrievedID = facilityDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, facilityDAO.getAllServiceRequests().size());

        // Delete Location from DB
        assertTrue(facilityDAO.deleteServiceRequest(deleteSR));

        // Cannot Delete Location Again
        assertFalse(facilityDAO.deleteServiceRequest(deleteSR));

        // Check DB is empty
        assertEquals(0, facilityDAO.getAllServiceRequests().size());
        assertEquals(null, facilityDAO.getServiceRequest(1234));
    }

    /**
     * Test that updateServiceRequest works.
     */
    @Test
    void testUpdateServiceRequest() {
        // Check DB is empty
        assertEquals(0, facilityDAO.getAllServiceRequests().size());
        assertEquals(null, facilityDAO.getServiceRequest(1234));

        // Insert SR into DB
        String creatorID = "1";
        String assigneeID = "2";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        FacilityMaintenanceSR.MaintenanceType maintenanceType = FacilityMaintenanceSR.MaintenanceType.Cleaning;

        FacilityMaintenanceSR updateSR = new FacilityMaintenanceSR();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setMaintenanceType(maintenanceType);

        int retrievedID = facilityDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, facilityDAO.getAllServiceRequests().size());

        // Update Location in DB
        creatorID = "1";
        assigneeID = "213";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Security;
        description = "help plz";
        maintenanceType = FacilityMaintenanceSR.MaintenanceType.Cleaning;
        String modifierID = "WillSmith";
        Timestamp modifiedTimestamp = new Timestamp(23098213);

        //updateSR.setCreatorID(creatorID);
        //updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setModifierID(modifierID);
        updateSR.setModifiedTimestamp(modifiedTimestamp);
        updateSR.setMaintenanceType(maintenanceType);
        assertTrue(facilityDAO.updateServiceRequest(updateSR));
        assertEquals(1, facilityDAO.getAllServiceRequests().size());

        // Check that DB values are expected
        FacilityMaintenanceSR querySR = facilityDAO.getServiceRequest(updateSR.getRequestID());
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
        assertEquals(maintenanceType, querySR.getMaintenanceType());
        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());

        // Cannot Update Nonexistent Location
        FacilityMaintenanceSR newSR = new FacilityMaintenanceSR();
        newSR.setRequestID(1234);
        assertFalse(facilityDAO.updateServiceRequest(newSR));
    }



}
