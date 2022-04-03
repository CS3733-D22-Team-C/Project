package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.DBManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationDAOImplTest {
    private DBManager testDBManager;
    private LocationDAOImpl locationDAO;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize LOCATION table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);
        testDBManager.initializeLocationTable(true);

        // Setup testing LocationDAOImpl
        locationDAO = new LocationDAOImpl();
    }

    @AfterEach
    void tearDown() {
        // Shutdown testing database
        DBManager.shutdown();
    }

    /**
     * Test that an empty Location Table DB returns nothing for queries.
     */
    @Test
    void testEmptyQueryLocation() {
        assertEquals(0, locationDAO.getAllLocations().size());
        assertEquals(null, locationDAO.getLocation(1234));
    }

    /**
     * Test that insertLocation works.
     */
    @Test
    void testInsertLocation() {
        // Check DB is empty
        assertEquals(0, locationDAO.getAllLocations().size());
        assertEquals(null, locationDAO.getLocation(1234));

        // Insert Location into DB
        String floor = "L1";
        String building = "Building";
        Location.NodeType nodeType = Location.NodeType.DEPT;
        String longName = "LongName";
        String shortName = "ShortName";
        int x = 10;
        int y = 20;
        Location insertLocation = new Location(floor, building, nodeType, longName, shortName, x, y);
        int retrievedID = locationDAO.insertLocation(insertLocation);
        insertLocation.setNodeID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, locationDAO.getAllLocations().size());

        // Cannot Insert Location Again
        assertEquals(-1, locationDAO.insertLocation(insertLocation));

        // Check that DB values are expected
        Location queryLocation = locationDAO.getLocation(insertLocation.getNodeID());
        assertNotNull(queryLocation);
        assertEquals(retrievedID, queryLocation.getNodeID());
        assertEquals(floor, queryLocation.getFloor());
        assertEquals(building, queryLocation.getBuilding());
        assertEquals(nodeType, queryLocation.getNodeType());
        assertEquals(longName, queryLocation.getLongName());
        assertEquals(shortName, queryLocation.getShortName());
        assertEquals(x, queryLocation.getX());
        assertEquals(y, queryLocation.getY());
    }

    /**
     * Test that deleteLocation works.
     */
    @Test
    void testDeleteLocation() {
        // Check DB is empty
        assertEquals(0, locationDAO.getAllLocations().size());
        assertEquals(null, locationDAO.getLocation(1234));

        // Insert Location into DB
        String floor = "L1";
        String building = "Building";
        Location.NodeType nodeType = Location.NodeType.HALL;
        String longName = "LongName";
        String shortName = "ShortName";
        int x = 10;
        int y = 20;
        Location deleteLocation = new Location(floor, building, nodeType, longName, shortName, x, y);
        int retrievedID = locationDAO.insertLocation(deleteLocation);
        deleteLocation.setNodeID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, locationDAO.getAllLocations().size());

        // Delete Location from DB
        assertTrue(locationDAO.deleteLocation(deleteLocation));

        // Cannot Delete Location Again
        assertFalse(locationDAO.deleteLocation(deleteLocation));

        // Check DB is empty
        assertEquals(0, locationDAO.getAllLocations().size());
        assertEquals(null, locationDAO.getLocation(1234));
    }

    /**
     * Test that updateLocation works.
     */
    @Test
    void testUpdateLocation() {
        // Check DB is empty
        assertEquals(0, locationDAO.getAllLocations().size());
        assertEquals(null, locationDAO.getLocation(1234));

        // Insert Location into DB
        String floor = "L1";
        String building = "Building";
        Location.NodeType nodeType = Location.NodeType.SERV;
        String longName = "LongName";
        String shortName = "ShortName";
        int x = 10;
        int y = 20;
        Location updateLocation = new Location(floor, building, nodeType, longName, shortName, x, y);
        int retrievedID = locationDAO.insertLocation(updateLocation);
        updateLocation.setNodeID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, locationDAO.getAllLocations().size());

        // Update Location in DB
        String newFloor = "L2";
        String newBuilding = "Building2";
        Location.NodeType newNodeType = Location.NodeType.CONF;
        String newLongName = "LongName2";
        String newShortName = "shortName2";
        int newX = 100;
        int newY = 200;
        updateLocation.setFloor(newFloor);
        updateLocation.setBuilding(newBuilding);
        updateLocation.setNodeType(newNodeType);
        updateLocation.setLongName(newLongName);
        updateLocation.setShortName(newShortName);
        updateLocation.setX(newX);
        updateLocation.setY(newY);
        assertTrue(locationDAO.updateLocation(updateLocation));
        assertEquals(1, locationDAO.getAllLocations().size());

        // Check that DB values are expected
        Location queryLocation = locationDAO.getLocation(updateLocation.getNodeID());
        assertNotNull(queryLocation);
        assertEquals(retrievedID, queryLocation.getNodeID());
        assertEquals(newFloor, queryLocation.getFloor());
        assertEquals(newBuilding, queryLocation.getBuilding());
        assertEquals(newNodeType, queryLocation.getNodeType());
        assertEquals(newLongName, queryLocation.getLongName());
        assertEquals(newShortName, queryLocation.getShortName());
        assertEquals(newX, queryLocation.getX());
        assertEquals(newY, queryLocation.getY());

        // Cannot Update Nonexistent Location
        Location newLocation = new Location(1234);
        assertFalse(locationDAO.updateLocation(newLocation));
    }
}
