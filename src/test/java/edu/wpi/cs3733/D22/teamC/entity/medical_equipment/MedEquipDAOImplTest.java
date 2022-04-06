package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedEquipDAOImplTest {
    private DBManager testDBManager;
    private MedicalEquipmentDAOImpl medicalEquipmentDAO;
    private LocationDAOImpl locationDAO;
    private Floor floor;
    private Location location;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize Medical_Equipment table
        //Initialize tables
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeFloorTable(true);
        testDBManager.initializeLocationTable(true);
        testDBManager.initializeMedicalEquipmentTable(true);

        // Setup testing LocationDAOImpl
        locationDAO = new LocationDAOImpl();

        // add a floor
        FloorDAO floorDAO = new FloorDAOImpl();
        floor = floorDAO.getFloor(floorDAO.insertFloor(new Floor()));
        Location dummyLoc = new Location();
        dummyLoc.setFloor(floor.getFloorID());
        location = locationDAO.getLocation(locationDAO.insertLocation(dummyLoc));

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
    void testEmptyQueryEquipment() {
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
        MedicalEquipment.EquipmentType equipmentType = MedicalEquipment.EquipmentType.Bed;
        MedicalEquipment.EquipmentStatus equipmentStatus = MedicalEquipment.EquipmentStatus.Dirty;
        MedicalEquipment insertEquipment = new MedicalEquipment();
        insertEquipment.setEquipmentType(equipmentType);
        insertEquipment.setEquipmentStatus(equipmentStatus);

        int retrievedID = medicalEquipmentDAO.insertMedicalEquipment(insertEquipment);

        insertEquipment.setEquipID(retrievedID);
        insertEquipment.setLocationID(location.getNodeID());
        assertNotEquals(-1, retrievedID);
        assertEquals(1, medicalEquipmentDAO.getMedicalEquipments().size());

        // Cannot Insert Equation Again
        assertEquals(-1, medicalEquipmentDAO.insertMedicalEquipment(insertEquipment));

        // Check that DB values are expected
        MedicalEquipment queryLocation = medicalEquipmentDAO.getMedicalEquipment(insertEquipment.getEquipID());
        assertNotNull(queryLocation);
        assertEquals(retrievedID, queryLocation.getEquipID());
        assertEquals(equipmentStatus, queryLocation.getEquipmentStatus());
        assertEquals(equipmentType, queryLocation.getEquipmentType());
        assertEquals(location.getNodeID(), queryLocation.getLocationID());
    }

    /**
     * Test that deleteEquipment works.
     */
    @Test
    void testDeleteMedicalEquipment() {
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
    @Test
    void testEquipmentAtLocation() {
        assertEquals(0, medicalEquipmentDAO.getMedicalEquipments().size());
        assertEquals(null, medicalEquipmentDAO.getMedicalEquipment(1234));

        //Insert Equipments into DB
        int locationID = 1234;

        MedicalEquipment.EquipmentType equipmentType = MedicalEquipment.EquipmentType.Infusion_Pump;
        MedicalEquipment.EquipmentStatus equipmentStatus = MedicalEquipment.EquipmentStatus.Available;

        MedicalEquipment.EquipmentType equipmentType2 = MedicalEquipment.EquipmentType.Portable_X_Ray;
        MedicalEquipment.EquipmentStatus equipmentStatus2 = MedicalEquipment.EquipmentStatus.Unavailable;

        MedicalEquipment.EquipmentType equipmentType3 = MedicalEquipment.EquipmentType.Bed;
        MedicalEquipment.EquipmentStatus equipmentStatus3 = MedicalEquipment.EquipmentStatus.Dirty;

        MedicalEquipment equipment1 = new MedicalEquipment(locationID, equipmentType, equipmentStatus);
        MedicalEquipment equipment2 = new MedicalEquipment(locationID, equipmentType2, equipmentStatus2);
        MedicalEquipment equipment3 = new MedicalEquipment(locationID, equipmentType3, equipmentStatus3);

        //this equipment will NOT be in the queried list
        MedicalEquipment equipment4 = new MedicalEquipment(12345, equipmentType, equipmentStatus);

        medicalEquipmentDAO.insertMedicalEquipment(equipment1);
        medicalEquipmentDAO.insertMedicalEquipment(equipment2);
        medicalEquipmentDAO.insertMedicalEquipment(equipment3);
        medicalEquipmentDAO.insertMedicalEquipment(equipment4);


        List<MedicalEquipment> queriedList = medicalEquipmentDAO.getMedicalEquipmentAtLocation(locationID);

        assertEquals(3, queriedList.size());

        assertEquals(equipment1.getLocationID(), queriedList.get(0).getLocationID());
        assertEquals(equipment1.getEquipmentType(), queriedList.get(0).getEquipmentType());
        assertEquals(equipment1.getEquipmentStatus(), queriedList.get(0).getEquipmentStatus());

        assertEquals(equipment2.getLocationID(), queriedList.get(1).getLocationID());
        assertEquals(equipment2.getEquipmentType(), queriedList.get(1).getEquipmentType());
        assertEquals(equipment2.getEquipmentStatus(), queriedList.get(1).getEquipmentStatus());

        assertEquals(equipment3.getLocationID(), queriedList.get(2).getLocationID());
        assertEquals(equipment3.getEquipmentType(), queriedList.get(2).getEquipmentType());
        assertEquals(equipment3.getEquipmentStatus(), queriedList.get(2).getEquipmentStatus());
    }
}