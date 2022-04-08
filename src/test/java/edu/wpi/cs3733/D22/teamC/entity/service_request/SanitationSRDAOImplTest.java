package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.factory.service_request.SanitationSRFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class SanitationSRDAOImplTest implements IDAOImplTest {
    private DBManager testDBManager;
    private SanitationSRDAOImpl sanitationSRDAO;
    private SanitationSRFactory sanitationSRFactory;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize SR table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeServiceRequestTable(true);
        testDBManager.initializeSanitationSRTable(true);

        // Setup testing SanitationSRDAOImpl
        sanitationSRDAO = new SanitationSRDAOImpl();

        sanitationSRFactory = new SanitationSRFactory();
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

    @Test
    public void testGetAll() {
        //TODO
    }

    @Test
    public void testGetByID() {
        //TODO
    }

    /**
     * Test that insert Sanitation SR works.
     */
    @Test
    public void testInsert() {
        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        SanitationSR insertSR = sanitationSRFactory.create();

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
        assertEquals(insertSR.getCreatorID(), querySR.getCreatorID());
        assertEquals(insertSR.getAssigneeID(), querySR.getAssigneeID());
        assertEquals(insertSR.getLocation(), querySR.getLocation());
        assertEquals(insertSR.getCreationTimestamp(), querySR.getCreationTimestamp());
        assertEquals(insertSR.getStatus(), querySR.getStatus());
        assertEquals(insertSR.getPriority(), querySR.getPriority());
        assertEquals(insertSR.getRequestType(), querySR.getRequestType());
        assertEquals(insertSR.getDescription(), querySR.getDescription());
        assertEquals(insertSR.getSanitationType(), querySR.getSanitationType());
    }

    /**
     * Test that deleteServiceRequest works.
     */
    @Test
    public void testDelete() {
        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        SanitationSR deleteSR = sanitationSRFactory.create();

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
    public void testUpdate() {
        // Check DB is empty
        assertEquals(0, sanitationSRDAO.getAllServiceRequests().size());
        assertEquals(null, sanitationSRDAO.getServiceRequest(1234));

        // Insert SR into DB
        SanitationSR updateSR = sanitationSRFactory.create();
        Timestamp initialTS = updateSR.getCreationTimestamp();

        int retrievedID = sanitationSRDAO.insertServiceRequest(updateSR);
        updateSR.setRequestID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, sanitationSRDAO.getAllServiceRequests().size());

        // Update Sanitation in DB
        updateSR = sanitationSRFactory.create();
        updateSR.setRequestID(retrievedID);

        assertTrue(sanitationSRDAO.updateServiceRequest(updateSR));
        assertEquals(1, sanitationSRDAO.getAllServiceRequests().size());

        // Check that DB values are expected
        SanitationSR querySR = sanitationSRDAO.getServiceRequest(updateSR.getRequestID());
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
        assertEquals(updateSR.getSanitationType(), querySR.getSanitationType());
        assertEquals(updateSR.getModifierID(), querySR.getModifierID());
        assertEquals(updateSR.getModifiedTimestamp(), querySR.getModifiedTimestamp());

        // Cannot Update Nonexistent Sanitation
        SanitationSR newSR = new SanitationSR();
        newSR.setRequestID(1234);
        assertFalse(sanitationSRDAO.updateServiceRequest(newSR));
    }
}
