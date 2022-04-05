package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedEquipDAOImplTest {
    private DBManager testDBManager;
    private MedicalEquipmentDAOImpl medicalEquipmentDAO;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize Medical_Equipment table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeMedicalEquipmentTable(true);
        testDBManager.initializeLocationTable(true);

        // Setup testing LocationDAOImpl
        medicalEquipmentDAO = new MedicalEquipmentDAOImpl();
    }

    @AfterEach
    void tearDown() {
        // Shutdown testing database
        DBManager.shutdown();
    }

    /**
     * Test that an empty Medical_Equipment Table DB returns nothing for queries.
     */
    @Test
    void testEmptyQueryLocation() {
        assertEquals(0, medicalEquipmentDAO.getMedicalEquipments().size());
        assertEquals(null, medicalEquipmentDAO.getMedicalEquipment(1234));
    }

    /**
     * Test that insertEquipment works.
     */
    @Test
    void testInsertEquipment() {
        // Check DB is empty
        assertEquals(0, medicalEquipmentDAO.getMedicalEquipments().size());
        assertEquals(null, medicalEquipmentDAO.getMedicalEquipment(1234));

        // Insert Equipment into DB
        int locationID = 1234;
        MedicalEquipment.EquipmentType equipmentType = MedicalEquipment.EquipmentType.Bed;
        MedicalEquipment.EquipmentStatus equipmentStatus = MedicalEquipment.EquipmentStatus.Dirty;
        MedicalEquipment insertEquipment = new MedicalEquipment(locationID, equipmentType, equipmentStatus);
        int retrievedID = medicalEquipmentDAO.insertMedicalEquipment(insertEquipment);
        insertEquipment.setEquipID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentDAO.getMedicalEquipments().size());

        // Cannot Insert Equation Again
        assertEquals(-1, medicalEquipmentDAO.insertMedicalEquipment(insertEquipment));

        // Check that DB values are expected
        MedicalEquipment queryLocation = medicalEquipmentDAO.getMedicalEquipment(insertEquipment.getEquipID());
        assertNotNull(queryLocation);
        assertEquals(retrievedID, queryLocation.getEquipID());
        assertEquals(equipmentStatus, queryLocation.getEquipmentStatus().toString());
        assertEquals(equipmentType, queryLocation.getEquipmentType().toString());
        assertEquals(locationID, queryLocation.getLocationID());
    }

    /**
     * Test that deleteEquipment works.
     */
    @Test
    void testDeleteLocation() {
        // Check DB is empty
        assertEquals(0, medicalEquipmentDAO.getMedicalEquipments().size());
        assertEquals(null, medicalEquipmentDAO.getMedicalEquipment(1234));


        // Insert Equipment into DB
        int locationID = 1234;
        MedicalEquipment.EquipmentType equipmentType = MedicalEquipment.EquipmentType.Recliner;
        MedicalEquipment.EquipmentStatus equipmentStatus = MedicalEquipment.EquipmentStatus.Unavailable;
        MedicalEquipment deleteEquipment = new MedicalEquipment(locationID, equipmentType, equipmentStatus);
        int retrievedID = medicalEquipmentDAO.insertMedicalEquipment(deleteEquipment);
        deleteEquipment.setEquipID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentDAO.getMedicalEquipments().size());

        // Delete Equipment from DB
        assertTrue(medicalEquipmentDAO.deleteMedicalEquipment(deleteEquipment));

        // Cannot Delete Equipment Again
        assertFalse(medicalEquipmentDAO.deleteMedicalEquipment(deleteEquipment));

        // Check DB is empty
        assertEquals(0, medicalEquipmentDAO.getMedicalEquipments().size());
        assertEquals(null, medicalEquipmentDAO.getMedicalEquipment(1234));
    }

    /**
     * Test that updateEquipment works.
     */
    @Test
    void testUpdateEquipment() {
        // Check DB is empty
        assertEquals(0, medicalEquipmentDAO.getMedicalEquipments().size());
        assertEquals(null, medicalEquipmentDAO.getMedicalEquipment(1234));


        // Insert Equipment into DB
        int locationID = 1234;
        MedicalEquipment.EquipmentType equipmentType = MedicalEquipment.EquipmentType.Infusion_Pump;
        MedicalEquipment.EquipmentStatus equipmentStatus = MedicalEquipment.EquipmentStatus.Available;
        MedicalEquipment updateEquipment = new MedicalEquipment(locationID, equipmentType, equipmentStatus);
        int retrievedID = medicalEquipmentDAO.insertMedicalEquipment(updateEquipment);
        updateEquipment.setEquipID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentDAO.getMedicalEquipments().size());

        // Update Equipment in DB
        int newLocationID = 3456;
        MedicalEquipment.EquipmentType newEquipmentType = MedicalEquipment.EquipmentType.Portable_X_Ray;
        MedicalEquipment.EquipmentStatus newEquipmentStatus = MedicalEquipment.EquipmentStatus.Unavailable;
        updateEquipment.setLocationID(newLocationID);
        updateEquipment.setEquipmentType(newEquipmentType);
        updateEquipment.setEquipmentStatus(newEquipmentStatus);
        assertTrue(medicalEquipmentDAO.updateMedicalEquipment(updateEquipment));
        assertEquals(1, medicalEquipmentDAO.getMedicalEquipments().size());

        // Check that DB values are expected
        MedicalEquipment queryLocation = medicalEquipmentDAO.getMedicalEquipment(updateEquipment.getEquipID());
        assertNotNull(queryLocation);
        assertEquals(retrievedID, queryLocation.getEquipID());
        assertEquals(newLocationID, queryLocation.getLocationID());
        assertEquals(newEquipmentType, queryLocation.getEquipmentType());
        assertEquals(newEquipmentStatus, queryLocation.getEquipmentStatus());

        // Cannot Update Nonexistent Equipment
        MedicalEquipment newEquipment = new MedicalEquipment(1234);
        assertFalse(medicalEquipmentDAO.updateMedicalEquipment(newEquipment));
    }
}