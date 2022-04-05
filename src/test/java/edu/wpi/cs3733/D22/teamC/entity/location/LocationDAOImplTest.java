package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationDAOImplTest {
    private DBManager testDBManager;
    private LocationDAOImpl locationDAO;
    private Floor floor;

    @BeforeEach
    void setUp() {
        // Setup testing database and initialize LOCATION table
        testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);

        //Initialize tables
        testDBManager.initializeFloorTable(true);
        testDBManager.initializeLocationTable(true);

        // Setup testing LocationDAOImpl
        locationDAO = new LocationDAOImpl();

        // add a floor
        FloorDAO floorDAO = new FloorDAOImpl();
        floor = floorDAO.getFloor(floorDAO.insertFloor(new Floor()));
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
        int floorID = floor.getFloorID();
        String building = "Building";
        Location.NodeType nodeType = Location.NodeType.DEPT;
        String longName = "LongName";
        String shortName = "shortName";
        int x = 10;
        int y = 20;
        Location insertLocation = new Location(floorID, building, nodeType, longName, shortName, x, y);
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
        assertEquals(floorID, queryLocation.getFloor());
        assertEquals(building, queryLocation.getBuilding());
        assertEquals(nodeType, queryLocation.getNodeType());
        assertEquals(longName, queryLocation.getLongName());
        assertEquals(shortName, queryLocation.getShortName());
        assertEquals(x, queryLocation.getX());
        assertEquals(y, queryLocation.getY());

        // Cannot insert with bad FloorID
        Location badInsertLocation = new Location(floorID + 1, building, nodeType, longName, shortName, x, y);
        assertEquals(-1, locationDAO.insertLocation(badInsertLocation));
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
        int floorID = floor.getFloorID();
        String building = "Building";
        Location.NodeType nodeType = Location.NodeType.HALL;
        String longName = "LongName";
        String shortName = "shortName";
        int x = 10;
        int y = 20;
        Location deleteLocation = new Location(floorID, building, nodeType, longName, shortName, x, y);
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
        int floorID = floor.getFloorID();
        String building = "Building";
        Location.NodeType nodeType = Location.NodeType.SERV;
        String longName = "LongName";
        String shortName = "shortName";
        int x = 10;
        int y = 20;
        Location updateLocation = new Location(floorID, building, nodeType, longName, shortName, x, y);
        int retrievedID = locationDAO.insertLocation(updateLocation);
        updateLocation.setNodeID(retrievedID);
        assertNotEquals(-1, retrievedID);
        assertEquals(1, locationDAO.getAllLocations().size());

        // Update Location in DB
        String newBuilding = "Building2";
        Location.NodeType newNodeType = Location.NodeType.CONF;
        String newLongName = "LongName2";
        String newShortName = "shortName2";
        int newX = 100;
        int newY = 200;
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