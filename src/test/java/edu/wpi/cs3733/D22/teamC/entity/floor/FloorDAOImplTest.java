package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.IDAOImplTest;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorDAOImplTest implements IDAOImplTest {
     private DBManager testDBManager;
     private FloorDAOImpl floorDAO;
     LocationDAOImpl locationDAO = new LocationDAOImpl();

     @BeforeEach
     void SetUp() {
         //Setting up each Floor DB and initializing the FLOOR table
         testDBManager = DBManager.startup(DBManager.TESTING_DATABASE_NAME);

         //Initialize tables
         testDBManager.initializeFloorTable(true);
         testDBManager.initializeLocationTable(true);

         //SetUp testing the FloorDAOImpl
         floorDAO = new FloorDAOImpl();
     }

     @AfterEach
     void tearDown() {
         //shutdown the testing DB
         DBManager.shutdown();
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
      * to test that an empty floor table TB returns nothing for queries
      */
     @Test
     void testEmptyQueryFloor() {
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));
     }

     @Test
     public void testInsert() {
         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";

         Floor insertFlo = new Floor();
         insertFlo.setOrder(order);
         insertFlo.setLongName(longName);
         insertFlo.setShortName(shortName);


         int retrievedID = floorDAO.insertFloor(insertFlo);
         insertFlo.setFloorID(retrievedID);

         assertNotEquals(-1, retrievedID);
         assertEquals(1, floorDAO.getAllFloors().size());

         //cannot insert the same floor again
         assertEquals(-1, floorDAO.insertFloor(insertFlo));

         //check that the DB values are expected
         Floor queryFlo = floorDAO.getFloor(insertFlo.getFloorID());
         assertNotNull(queryFlo);
         assertEquals(retrievedID, queryFlo.getFloorID());
         assertEquals(order, queryFlo.getOrder());
         assertEquals(longName, queryFlo.getLongName());
         assertEquals(shortName, queryFlo.getShortName());

     }

     @Test
     public void testDelete() {
         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";

         Floor deleteFlo = new Floor();
         deleteFlo.setOrder(order);
         deleteFlo.setLongName(longName);
         deleteFlo.setShortName(shortName);

         int retrievedID = floorDAO.insertFloor(deleteFlo);
         deleteFlo.setFloorID(retrievedID);

         assertNotEquals(-1, retrievedID);
         assertEquals(1, floorDAO.getAllFloors().size());

         //deleting Floor from the database
         assertTrue(floorDAO.deleteFloor(deleteFlo));

         // Making sure we cannot delete it again when it is not in DB
         assertFalse(floorDAO.deleteFloor(deleteFlo));

         //check if the DB is empty after the deletion
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));
     }

     @Test
     public void testUpdate() {
         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         // insert Floor into DB
         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";

         Floor updateFloo = new Floor();
         updateFloo.setOrder(order);
         updateFloo.setLongName(longName);
         updateFloo.setShortName(shortName);

         int retrievedID = floorDAO.insertFloor(updateFloo);
         updateFloo.setFloorID(retrievedID);

         assertNotEquals(-1, retrievedID);
         assertEquals(1, floorDAO.getAllFloors().size());

         //updating the previous floor
         int newOrder = 7;
         String newLongName = "TinyBabyRoom";
         String newShortName = "TinyRoom";


         updateFloo.setOrder(newOrder);
         updateFloo.setLongName(newLongName);
         updateFloo.setShortName(newShortName);

         assertTrue(floorDAO.updateFloor(updateFloo));
         assertEquals(1, floorDAO.getAllFloors().size());

         //check that the DB values are expected
         Floor queryFlo = floorDAO.getFloor(updateFloo.getFloorID());
         assertNotNull(queryFlo);
         assertEquals(retrievedID, queryFlo.getFloorID());
         assertEquals(newOrder, queryFlo.getOrder());
         assertEquals(newLongName, queryFlo.getLongName());
         assertEquals(newShortName, queryFlo.getShortName());

         //Cannot update NonExistent Floor
         Floor newFloo = new Floor(1001);
         assertFalse(floorDAO.updateFloor(newFloo));

     }

     @Test
     public void testGetAllLocations() {
         // Setup testing LocationDAOImpl
         locationDAO = new LocationDAOImpl();

         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         // Check DB is empty
         assertEquals(0, locationDAO.getAllLocations().size());
         assertNull(locationDAO.getLocation(1234));

         // Insert Location into DB
         Floor testFloor = floorDAO.getFloor(floorDAO.insertFloor(new Floor()));
         String building = "Building";
         Location.NodeType nodeType = Location.NodeType.DEPT;
         String longName = "LongName";
         String shortName = "shortName";
         int x = 10;
         int y = 20;
         Location insertLocation = new Location(testFloor.getFloorID(), building, nodeType, longName, shortName, x, y);
         int retrievedID = locationDAO.insertLocation(insertLocation);
         insertLocation.setNodeID(retrievedID);
         assertNotEquals(-1, retrievedID);
         assertEquals(1, locationDAO.getAllLocations().size());

         assertEquals(1,floorDAO.getAllFloors().size());

     }

     /**
      * Test that On Delete Cascade works with LOCATION.
      */
     @Test
     void testDeleteCascade() {
         // Check DB is empty
         assertEquals(0, locationDAO.getAllLocations().size());
         assertNull(locationDAO.getLocation(1234));

         // insert Floor into DB
         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";

         Floor floor = new Floor();
         floor.setOrder(order);
         floor.setLongName(longName);
         floor.setShortName(shortName);

         int retrievedID = floorDAO.insertFloor(floor);
         floor.setFloorID(retrievedID);

         // Insert Location into DB
         int floorID = floor.getFloorID();
         String building = "Building";
         Location.NodeType nodeType = Location.NodeType.HALL;
         String longNameLoc = "LongName";
         String shortNameLoc = "shortName";
         int x = 10;
         int y = 20;

         Location location = new Location(floorID, building, nodeType, longNameLoc, shortNameLoc, x, y);
         int retrievedIDLoc = locationDAO.insertLocation(location);
         location.setNodeID(retrievedID);

         assertNotEquals(-1, retrievedID);
         assertEquals(1, locationDAO.getAllLocations().size());

         // Delete Floor from DB
         assertTrue(floorDAO.deleteFloor(floor));

         // Check Floor DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(floor.getFloorID()));

         // Check Location DB is empty
         assertEquals(0, locationDAO.getAllLocations().size());
         assertNull(locationDAO.getLocation(1234));
     }
}




