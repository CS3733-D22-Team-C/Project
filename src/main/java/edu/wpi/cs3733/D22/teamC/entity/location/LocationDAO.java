package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

import java.util.List;

/**
 * Wrapper for Location handling.
 */
public class LocationDAO extends DAO<Location> {
    
    protected Class<Location> classType() {
        return Location.class;
    }
    
    /**
     * Get all entries in LOCATION Table of DB, converting them to Location objects.
     * @return List of all Location objects converted from queries.
     */
    public List<Location> getAll() {
        return HibernateManager.filterQuery("from Location");
    }
    
}
