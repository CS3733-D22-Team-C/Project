package edu.wpi.cs3733.D22.teamC.entity.location;

import edu.wpi.cs3733.D22.teamC.HibernateManager;

import java.util.List;

public interface LocationDAO {
    public List<Location> getAllLocations();
    public Location getLocation(int nodeID);

    public int insertLocation(Location location);
    public boolean updateLocation(Location location);
    public boolean deleteLocation(Location location);
}
