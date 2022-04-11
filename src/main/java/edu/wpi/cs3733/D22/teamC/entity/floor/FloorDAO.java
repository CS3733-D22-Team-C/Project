package edu.wpi.cs3733.D22.teamC.entity.floor;
import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.util.List;


public class FloorDAO extends DAO<Floor> {
    
    protected Class<Floor> classType() {
        return Floor.class;
    }

    // TODO: Broken after UUID Migration
    /**
     * Return a list of locations associated with the given floorID.
     * @param floorID Floor ID used for querying locations of the given floor.
     * @return Return a list of locations associated with the given floorID.
     */
    public List<Location> getAllLocations(String floorID) {
        return HibernateManager.filterQuery("select q from " + Location.class.getName() + 
                " q where q.floor = '" + floorID + "'");    
    }
    

}
