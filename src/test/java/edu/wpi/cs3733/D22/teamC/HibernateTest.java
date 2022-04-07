package edu.wpi.cs3733.D22.teamC;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HibernateTest {

	@BeforeEach
	void setUp() {}

	@AfterEach
	void tearDown() {}

	@Test
	void testInsertLocation() {
		Location newLoc = new Location();
		HibernateManager.insertObj(newLoc);
	}

	@Test
	void testGetLocation() {
		// Insert a new object into the database
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setNodeType(Location.NodeType.STOR);
		newLoc.setLongName("YourMom69");
		newLoc.setX(1000);
		newLoc.setY(1000);
		int insertedID = HibernateManager.insertObj(newLoc);

		// Retrieve the object
		Location retrievedLoc = HibernateManager.getObjByID(insertedID, Location.class);
		assertNotNull(retrievedLoc);

		// Verify attributes
		assertEquals(insertedID, retrievedLoc.getNodeID());
		assertEquals(newLoc.getBuilding(), retrievedLoc.getBuilding());
		assertEquals(newLoc.getNodeType(), retrievedLoc.getNodeType());
		assertEquals(newLoc.getLongName(), retrievedLoc.getLongName());
		assertEquals(newLoc.getX(), retrievedLoc.getX());
		assertEquals(newLoc.getY(), retrievedLoc.getY());
	}

	@Test
	void testUpdateLocation() {
		// Insert a new object into the database
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setNodeType(Location.NodeType.STOR);
		newLoc.setLongName("YourMom69");
		newLoc.setX(1000);
		newLoc.setY(1000);
		int insertedID = HibernateManager.insertObj(newLoc);

		// Retrieve the object
		Location retrievedLoc = HibernateManager.getObjByID(insertedID, Location.class);
		assertNotNull(retrievedLoc);

		// Update attributes
		Location.NodeType newNodeType = Location.NodeType.LABS;
		String newLongName = "NotYourMom";
		String newShortName = "NYM";
		int newX = 500;
		int newY = 500;

		retrievedLoc.setNodeID(insertedID);
		retrievedLoc.setNodeType(newNodeType);
		retrievedLoc.setLongName(newLongName);
		retrievedLoc.setShortName(newShortName);
		retrievedLoc.setX(newX);
		retrievedLoc.setY(newY);
		HibernateManager.updateObj(retrievedLoc);

		// Verify attributes
		assertEquals(newNodeType, retrievedLoc.getNodeType());
		assertEquals(newLongName, retrievedLoc.getLongName());
		assertEquals(newShortName, retrievedLoc.getShortName());
		assertEquals(newX, retrievedLoc.getX());
		assertEquals(newY, retrievedLoc.getY());
	}

	@Test
	void testDeleteLocation() {
		Location newLoc = new Location();
		newLoc.setBuilding("TOWER");
		newLoc.setLongName("deleteMe");

		HibernateManager.insertObj(newLoc);

		HibernateManager.deleteObj(newLoc);
	}
}
