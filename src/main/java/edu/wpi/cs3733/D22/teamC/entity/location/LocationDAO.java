package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper for Location handling.
 */
public class LocationDAO extends DAO<Location> {
    
    protected Class<Location> classType() {
        return Location.class;
    }

    public Location getByLongName(String longName) {
        return null;
    }
}
