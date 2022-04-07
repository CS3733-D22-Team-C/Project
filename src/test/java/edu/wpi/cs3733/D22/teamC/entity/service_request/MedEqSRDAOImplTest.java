package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MedEqSRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private MedicalEquipmentSRDAOImpl medicalEqDAO;
    private MedicalEquipmentSRFactory medicalEquipmentSRFactory;
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize LOCATION table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeMedicalEquipSRTable(true);
        
        // Setup testing medicalEqDAOImpl
        medicalEqDAO = new MedicalEquipmentSRDAOImpl();
        medicalEquipmentSRFactory = new MedicalEquipmentSRFactory();
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

    @Test
    public void getAll() {
        //TODO
    }

    @Test
    public void testGetByID() {
        //TODO
    }
    
    /**
     * Test that insertLocation works.
     */
    @Test
    public void testInsert() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        MedicalEquipmentSR insertSR = medicalEquipmentSRFactory.create();

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
        assertEquals(insertSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(insertSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(insertSR.getLocation(), querySR.getLocation());
        assertEquals(insertSR.getCreationTimestamp(), querySR.getCreationTimestamp());
        assertEquals(insertSR.getStatus(), querySR.getStatus());
        assertEquals(insertSR.getPriority(), querySR.getPriority());
        assertEquals(insertSR.getRequestType(), querySR.getRequestType());
        assertEquals(insertSR.getDescription(), querySR.getDescription());
        assertEquals(insertSR.getEquipmentID(), querySR.getEquipmentID());
        assertEquals(insertSR.getEquipmentType(), querySR.getEquipmentType());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    public void testDelete() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
    
        // Insert SR into DB
        MedicalEquipmentSR deleteSR = medicalEquipmentSRFactory.create();

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
    public void testUpdate() {
        // Check DB is empty
        assertEquals(0, medicalEqDAO.getAllServiceRequests().size());
        assertEquals(null, medicalEqDAO.getServiceRequest(1234));
    
        // Insert SR into DB
        MedicalEquipmentSR updateSR = medicalEquipmentSRFactory.create();

        int retrievedID = medicalEqDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
        
        // Update Location in DB
        updateSR = medicalEquipmentSRFactory.create();
        updateSR.setRequestID(retrievedID);
        assertTrue(medicalEqDAO.updateServiceRequest(updateSR));
        assertEquals(1, medicalEqDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        MedicalEquipmentSR querySR = medicalEqDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(updateSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(updateSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(updateSR.getLocation(), querySR.getLocation());
        //assertEquals(initialTS, querySR.getCreationTimestamp());
        assertEquals(updateSR.getStatus(), querySR.getStatus());
        assertEquals(updateSR.getPriority(), querySR.getPriority());
        assertEquals(updateSR.getRequestType(), querySR.getRequestType());
        assertEquals(updateSR.getDescription(), querySR.getDescription());
        assertEquals(updateSR.getEquipmentID(), querySR.getEquipmentID());
        assertEquals(updateSR.getEquipmentType(), querySR.getEquipmentType());
        assertEquals(updateSR.getModifierID(), querySR.getModifierID());
        assertEquals(updateSR.getModifiedTimestamp(), querySR.getModifiedTimestamp());

        // Cannot Update Nonexistent Location
        MedicalEquipmentSR newSR = new MedicalEquipmentSR();
        newSR.setRequestID(1234);
        assertFalse(medicalEqDAO.updateServiceRequest(newSR));
    }
}
