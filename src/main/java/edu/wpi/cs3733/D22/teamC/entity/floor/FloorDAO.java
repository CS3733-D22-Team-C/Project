package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.util.List;

public class FloorDAO {
    
    /**
     * Return a list of locations associated with the given floorID.
     * @param floorID Floor ID used for querying locations of the given floor.
     * @return Return a list of locations associated with the given floorID.
     */
    public List<Location> getAllLocations(int floorID) {
        return HibernateManager.filterQuery("from Location where FLOORID = " + floorID);    
    }
    
    /**
     * Get all entries in FLOOR Table of DB, converting them to Floor objects.
     * @return List of all FLOOR objects converted from queries.
     */
    public List<Floor> getAllFloors() {
        return HibernateManager.filterQuery("from Floor");
    }
    
    /**
     * Get entry in FLOOR Table of DB with given floorID, converting it to a Floor object.
     * @param floorID The given floorID.
     * @return Floor object converted from query.
     */
    public Floor getFloor(int floorID) {
        return HibernateManager.getObjByID(floorID, Floor.class);
    }
    
    /**
     * Insert entry in FLOOR Table of DB corresponding to the given Floor object.
     * @param floor The Floor to be inserted into the DB via a corresponding entry.
     * @return If successful return ID, else return -1.
     */
    public int insertFloor(Floor floor) {
        return HibernateManager.insertObj(floor);
    }
    
    /**
     * Update entry in FLOOR Table of DB corresponding to the given Floor object.
     * @param floor The FLOOR whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    public boolean updateFloor(Floor floor) {
        return HibernateManager.updateObj(floor);
    }
    
    /**
     * Delete entry in FLOOR Table of DB corresponding to the given Floor object.
     * @param floor The Floor whose corresponding DB entry is to be deleted.
     * @return If successful return true, else return false.
     */
    public boolean deleteFloor(Floor floor) {
        return HibernateManager.deleteObj(floor);
    }
    
}
