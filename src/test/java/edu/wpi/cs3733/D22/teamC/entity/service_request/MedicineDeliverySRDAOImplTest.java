package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery.MedicineDeliverySRDAOImpl;
import edu.wpi.cs3733.D22.teamC.factory.service_request.MedicineDeliverySRFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicineDeliverySRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private MedicineDeliverySRDAO medicineDeliveryDAO;
    private MedicineDeliverySRFactory medicineDeliverySRFactory;
    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeMedicineDeliverySRTable(true);
        
        // Setup testing MedicineDeliveryDAOImpl
        medicineDeliveryDAO = new MedicineDeliverySRDAOImpl();

        medicineDeliverySRFactory = new MedicineDeliverySRFactory();
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

    @Test
    public void testGetAll() {
        //TODO
    }

    @Test
    public void testGetByID() {
        //TODO
    }
    
    /**
     * Test that insertSR works.
     */
    @Test
    public void testInsert() {
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        MedicineDeliverySR insertSR = medicineDeliverySRFactory.create();
        
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
        assertEquals(insertSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(insertSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(insertSR.getLocation(), querySR.getLocation());
        assertEquals(insertSR.getCreationTimestamp(), querySR.getCreationTimestamp());
        assertEquals(insertSR.getStatus(), querySR.getStatus());
        assertEquals(insertSR.getPriority(), querySR.getPriority());
        assertEquals(insertSR.getRequestType(), querySR.getRequestType());
        assertEquals(insertSR.getDescription(), querySR.getDescription());
        assertEquals(insertSR.getMedicine(), querySR.getMedicine());
        assertEquals(insertSR.getDosage(), querySR.getDosage());
        assertEquals(insertSR.getPatientID(), querySR.getPatientID());
    }
    
    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    public void testDelete() {
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
        
        // Insert SR into DB
       MedicineDeliverySR deleteSR = medicineDeliverySRFactory.create();
        
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
    public void testUpdate() {
        // Check DB is empty
        assertEquals(0, medicineDeliveryDAO.getAllServiceRequests().size());
        assertEquals(null, medicineDeliveryDAO.getServiceRequest(1234));
        
        // Insert SR into DB
        MedicineDeliverySR updateSR = medicineDeliverySRFactory.create();
        int retrievedID = medicineDeliveryDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicineDeliveryDAO.getAllServiceRequests().size());
        
        // Update MedicineDeliverySR in DB
        updateSR = medicineDeliverySRFactory.create();
        updateSR.setRequestID(retrievedID);

        assertTrue(medicineDeliveryDAO.updateServiceRequest(updateSR));
        assertEquals(1, medicineDeliveryDAO.getAllServiceRequests().size());
        
        // Check that DB values are expected
        MedicineDeliverySR querySR = medicineDeliveryDAO.getServiceRequest(updateSR.getRequestID());
        assertNotNull(querySR);
        assertEquals(retrievedID, querySR.getRequestID());
        assertEquals(updateSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(updateSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(updateSR.getLocation(), querySR.getLocation());
        //assertEquals(initialTS, querySR.getCreationTimestamp());
        assertEquals(updateSR.getStatus(), querySR.getStatus());
        assertEquals(updateSR.getPriority(), querySR.getPriority());
        assertEquals(updateSR.getRequestType(), querySR.getRequestType());
        assertEquals(updateSR.getDescription(), querySR.getDescription());
        assertEquals(updateSR.getMedicine(), querySR.getMedicine());
        assertEquals(updateSR.getDosage(), querySR.getDosage());
        assertEquals(updateSR.getPatientID(), querySR.getPatientID());
        assertEquals(updateSR.getModifierID(), querySR.getModifierID());
        assertEquals(updateSR.getModifiedTimestamp(), querySR.getModifiedTimestamp());
        
        // Cannot Update Nonexistent MedicineDeliverySR
        MedicineDeliverySR newSR = new MedicineDeliverySR();
        newSR.setRequestID(1234);
        assertFalse(medicineDeliveryDAO.updateServiceRequest(newSR));
    }
}
