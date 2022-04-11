package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.factory.floor.FloorFactory;
import edu.wpi.cs3733.D22.teamC.factory.location.LocationFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FloorDAOTest extends DAOTest<Floor> {

    public FloorDAOTest() {
        super(new FloorFactory(), new FloorDAO());
    }
    
    @Test
    public void getAllLocTest() {
        // Create a new floor and store it in the DB
        FloorDAO testDAO = new FloorDAO();
        Floor testFloor = factory.create();
        String testFloorID = testDAO.insert(testFloor);
        assertNotNull(testFloorID);
        
        // Create and store a location in the DB, associated with the Floor
        LocationDAO testLDAO = new LocationDAO();
        LocationFactory locationFactory = new LocationFactory();
        Location testLoc = locationFactory.create();
        testLoc.setFloor(testFloorID);
        String testLocID = testLDAO.insert(testLoc);
        assertNotNull(testFloorID);
        assertNotNull(testLocID);
        
        List<Location> returnList = testDAO.getAllLocations(testFloorID);
        assertEquals(1, returnList.size());
    }
}
