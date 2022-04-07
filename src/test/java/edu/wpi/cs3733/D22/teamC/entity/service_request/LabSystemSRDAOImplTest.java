package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabSystemSRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private LabSystemSRDAOImpl labSystemDAO;
    
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeLabSystemSRTable(true);
        
        // Setup testing LabSystemSRDAOImpl
        labSystemDAO = new LabSystemSRDAOImpl();
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
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Lab_System;
        String description = "soft eng is spain without the s";
        LabSystemSR.LabType labType = LabSystemSR.LabType.Cat_Scan;
        String patientID = "JohnCena";
        
        LabSystemSR insertSR = new LabSystemSR();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setLabType(labType);
        insertSR.setPatientID(patientID);
        
        int retrievedID = labSystemDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Cannot Insert SR Again
        assertEquals(-1, labSystemDAO.insertServiceRequest(insertSR));
        
        // Check that DB values are expected
        LabSystemSR querySR = labSystemDAO.getServiceRequest(insertSR.getRequestID());
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
        assertEquals(labType, querySR.getLabType());
        assertEquals(patientID, querySR.getPatientID());
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
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        LabSystemSR.LabType labType = LabSystemSR.LabType.Cat_Scan;
        String patientID = "JohnCena";
        
        LabSystemSR deleteSR = new LabSystemSR();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setLabType(labType);
        deleteSR.setPatientID(patientID);
        
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
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        LabSystemSR.LabType labType = LabSystemSR.LabType.Cat_Scan;
        String patientID = "JohnCena";
        
        LabSystemSR updateSR = new LabSystemSR();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setLabType(labType);
        updateSR.setPatientID(patientID);
        
        int retrievedID = labSystemDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Medical_Equipment;
        description = "help plz";
        labType = LabSystemSR.LabType.Cat_Scan;
        patientID = "heDeadUh";
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

        updateSR.setLabType(labType);
        updateSR.setPatientID(patientID);
        assertTrue(labSystemDAO.updateServiceRequest(updateSR));
        assertEquals(1, labSystemDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        LabSystemSR querySR = labSystemDAO.getServiceRequest(updateSR.getRequestID());
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
        assertEquals(labType, querySR.getLabType());
        assertEquals(patientID, querySR.getPatientID());
        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());
        
        // Cannot Update Nonexistent Location
        LabSystemSR newSR = new LabSystemSR();
        newSR.setRequestID(1234);
        assertFalse(labSystemDAO.updateServiceRequest(newSR));
    }
}
