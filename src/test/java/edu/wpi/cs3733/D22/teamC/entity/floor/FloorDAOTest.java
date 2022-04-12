package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.factory.floor.FloorFactory;
import edu.wpi.cs3733.D22.teamC.factory.location.LocationFactory;
import org.junit.jupiter.api.Test;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.SQLException;
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

    @Test
    void insertImageTest(){
        File file = new File("maps/00_thegroundfloor.png");
        byte[] bFile = new byte[(int) file.length()];

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Floor floorGround = new Floor();
        floorGround.setImage(bFile);
        floorGround.setLongName("ground");

        FloorDAO testDAO = new FloorDAO();
        String floorID = testDAO.insert(floorGround);
        assertNotNull(floorID);

        assertNotNull(floorGround.getImage());
    }
}
