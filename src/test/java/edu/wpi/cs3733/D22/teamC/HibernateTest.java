package edu.wpi.cs3733.D22.teamC;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateTest {
    
    @BeforeEach
    void setUp() {
        
    }
    
    @AfterEach
    void tearDown() {
        
    }
    
    @Test
    void testInsertLocation() {
        Location newLoc = new Location();
        HibernateManager.saveObj(newLoc);
    }
    
    @Test
    void testUpdateLocation() {
        Location newLoc = new Location();
        HibernateManager.saveObj(newLoc);
        
        newLoc.setBuilding("TOWER");
        newLoc.setLongName("YourMom69");
        newLoc.setX(1000);
        newLoc.setY(1000);
        HibernateManager.updateObj(newLoc);
    }
    
    @Test
    void testDeleteLocation() {
        Location newLoc = new Location();
        newLoc.setBuilding("TOWER");
        newLoc.setLongName("deleteMe");
        
        HibernateManager.saveObj(newLoc);
        
        HibernateManager.deleteObj(newLoc);
    }
}
