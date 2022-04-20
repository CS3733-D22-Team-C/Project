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

    public Location getByLongName(String longName) {
        List<Location> query = HibernateManager.filterQuery("select q from " + classType().getName() +
                " q where q.longName = '" + longName + "'");

        if (query.size() > 0) return query.get(0);
        else return null;
    }
}
