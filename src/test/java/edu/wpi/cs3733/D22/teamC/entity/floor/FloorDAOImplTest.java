package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FloorDAOImplTest {
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

     /**
      * to test that an empty floor table TB returns nothing for queries
      */
     @Test
     void testEmptyQueryFloor() {
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));
     }

     @Test
     void testInsertFloor() {
         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";
         String imageSrc = "/";

         Floor insertFlo = new Floor();
         insertFlo.setOrder(order);
         insertFlo.setLongName(longName);
         insertFlo.setShortName(shortName);
         insertFlo.setImageSrc(imageSrc);

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
         assertEquals(imageSrc, queryFlo.getImageSrc());
     }

     @Test
     void testDeleteFloor() {
         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";
         String imageSrc = "/";

         Floor deleteFlo = new Floor();
         deleteFlo.setOrder(order);
         deleteFlo.setLongName(longName);
         deleteFlo.setShortName(shortName);
         deleteFlo.setImageSrc(imageSrc);

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
     void testUpdateFloor() {
         //check if the DB is empty
         assertEquals(0, floorDAO.getAllFloors().size());
         assertNull(floorDAO.getFloor(1001));

         // insert Floor into DB
         int order = 1;
         String longName = "Really Big Room";
         String shortName = "BigRoom";
         String imageSrc = "/1";

         Floor updateFloo = new Floor();
         updateFloo.setOrder(order);
         updateFloo.setLongName(longName);
         updateFloo.setShortName(shortName);
         updateFloo.setImageSrc(imageSrc);

         int retrievedID = floorDAO.insertFloor(updateFloo);
         updateFloo.setFloorID(retrievedID);

         assertNotEquals(-1, retrievedID);
         assertEquals(1, floorDAO.getAllFloors().size());

         //updating the previous floor
         int newOrder = 7;
         String newLongName = "TinyBabyRoom";
         String newShortName = "TinyRoom";
         String newImageSrc = "/2";


         updateFloo.setOrder(newOrder);
         updateFloo.setLongName(newLongName);
         updateFloo.setShortName(newShortName);
         updateFloo.setImageSrc(newImageSrc);

         assertTrue(floorDAO.updateFloor(updateFloo));
         assertEquals(1, floorDAO.getAllFloors().size());

         //check that the DB values are expected
         Floor queryFlo = floorDAO.getFloor(updateFloo.getFloorID());
         assertNotNull(queryFlo);
         assertEquals(retrievedID, queryFlo.getFloorID());
         assertEquals(newOrder, queryFlo.getOrder());
         assertEquals(newLongName, queryFlo.getLongName());
         assertEquals(newShortName, queryFlo.getShortName());
         assertEquals(newImageSrc, queryFlo.getImageSrc());

         //Cannot update NonExistent Floor
         Floor newFloo = new Floor(1001);
         assertFalse(floorDAO.updateFloor(newFloo));

     }

     @Test
     void testGetAllLocations() {
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
         String imageSrc = "/";

         Floor floor = new Floor();
         floor.setOrder(order);
         floor.setLongName(longName);
         floor.setShortName(shortName);
         floor.setImageSrc(imageSrc);

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




