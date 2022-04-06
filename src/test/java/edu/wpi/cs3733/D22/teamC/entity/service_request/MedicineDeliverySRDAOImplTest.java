package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class MedicineDeliverySRDAOImplTest {
    private DBManager testDBManager;
    private MedicineDeliverySRDAO medicineDeliveryDAO;
    
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeMedicineDeliverySRTable(true);
        
        // Setup testing MedicineDeliveryDAOImpl
        medicineDeliveryDAO = new MedicineDeliverySRDAOImpl();
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
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that insertSR works.
     */
    @Test
    void testInsertSR() {
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Lab_System;
        String description = "soft eng is spain without the s";
        String medicine = "methamphetamine HCL";
        String dosage = "18mg oral";
        String patientID = "JohnCena";
        
        MedicineDeliverySR insertSR = new MedicineDeliverySR();
        insertSR.setCreatorID(creatorID);
        insertSR.setAssigneeID(assigneeID);
        insertSR.setLocation(locationID);
        insertSR.setCreationTimestamp(creationTimeStamp);
        insertSR.setStatus(status);
        insertSR.setPriority(priority);
        insertSR.setRequestType(requestType);
        insertSR.setDescription(description);
        insertSR.setMedicine(medicine);
        insertSR.setDosage(dosage);
        insertSR.setPatientID(patientID);
        
        int retrievedID = medicineDeliveryDAO.insertServiceRequest(insertSR);
        insertSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicineDeliveryDAO.getAllServiceRequests().size());
        
        // Cannot Insert SR Again
        assertEquals(-1, medicineDeliveryDAO.insertServiceRequest(insertSR));
        
        // Check that DB values are expected
        MedicineDeliverySR querySR = medicineDeliveryDAO.getServiceRequest(insertSR.getRequestID());
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
        assertEquals(medicine, querySR.getMedicine());
        assertEquals(dosage, querySR.getDosage());
        assertEquals(patientID, querySR.getPatientID());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    void testDeleteServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Blank;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        String medicine = "methamphetamine HCL";
        String dosage = "18mg oral";
        String patientID = "JohnCena";
        
        MedicineDeliverySR deleteSR = new MedicineDeliverySR();
        deleteSR.setCreatorID(creatorID);
        deleteSR.setAssigneeID(assigneeID);
        deleteSR.setLocation(locationID);
        deleteSR.setCreationTimestamp(creationTimeStamp);
        deleteSR.setStatus(status);
        deleteSR.setPriority(priority);
        deleteSR.setRequestType(requestType);
        deleteSR.setDescription(description);
        deleteSR.setMedicine(medicine);
        deleteSR.setDosage(dosage);
        deleteSR.setPatientID(patientID);
        
        int retrievedID = medicineDeliveryDAO.insertServiceRequest(deleteSR);
        deleteSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicineDeliveryDAO.getAllServiceRequests().size());
        
        // Delete MedicineDeliverySR from DB
        assertTrue(medicineDeliveryDAO.deleteServiceRequest(deleteSR));
        
        // Cannot Delete MedicineDeliverySR Again
        assertFalse(medicineDeliveryDAO.deleteServiceRequest(deleteSR));
        
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
    }
    
    /**
     * Test that updateServiceRequest works.
     */
    @Test
    void testUpdateServiceRequest() {
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        String creatorID = "bshin100";
        String assigneeID = "nick1234";
        String locationID = "FOISIE";
        Timestamp creationTimeStamp = new Timestamp(694201234);
        ServiceRequest.Status status = ServiceRequest.Status.Processing;
        ServiceRequest.Priority priority = ServiceRequest.Priority.High;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Medical_Equipment;
        String description = "soft eng is spain without the s";
        String medicine = "methamphetamine HCL";
        String dosage = "18mg oral";
        String patientID = "JohnCena";
        
        MedicineDeliverySR updateSR = new MedicineDeliverySR();
        updateSR.setCreatorID(creatorID);
        updateSR.setAssigneeID(assigneeID);
        updateSR.setLocation(locationID);
        updateSR.setCreationTimestamp(creationTimeStamp);
        updateSR.setStatus(status);
        updateSR.setPriority(priority);
        updateSR.setRequestType(requestType);
        updateSR.setDescription(description);
        updateSR.setMedicine(medicine);
        updateSR.setDosage(dosage);
        updateSR.setPatientID(patientID);
        
        int retrievedID = medicineDeliveryDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicineDeliveryDAO.getAllServiceRequests().size());
        
        // Update MedicineDeliverySR in DB
        creatorID = "bshin100";
        assigneeID = "nick1234";
        locationID = "SMARTWORLD";
        status = ServiceRequest.Status.Done;
        priority = ServiceRequest.Priority.High;
        requestType = ServiceRequest.RequestType.Medical_Equipment;
        description = "help plz";
        medicine = "methamphetamine HCL";
        dosage = "100mg intravenous";
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
        
        updateSR.setMedicine(medicine);
        updateSR.setDosage(dosage);
        updateSR.setPatientID(patientID);
        assertTrue(medicineDeliveryDAO.updateServiceRequest(updateSR));
        assertEquals(1, medicineDeliveryDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        MedicineDeliverySR querySR = medicineDeliveryDAO.getServiceRequest(updateSR.getRequestID());
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
        assertEquals(medicine, querySR.getMedicine());
        assertEquals(dosage, querySR.getDosage());
        assertEquals(patientID, querySR.getPatientID());
        assertEquals(modifierID, querySR.getModifierID());
        assertEquals(modifiedTimestamp, querySR.getModifiedTimestamp());
        
        // Cannot Update Nonexistent MedicineDeliverySR
        MedicineDeliverySR newSR = new MedicineDeliverySR();
        newSR.setRequestID(1234);
        assertFalse(medicineDeliveryDAO.updateServiceRequest(newSR));
    }
}
