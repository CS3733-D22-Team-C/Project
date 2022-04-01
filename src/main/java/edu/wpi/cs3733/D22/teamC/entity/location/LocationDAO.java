package edu.wpi.cs3733.D22.teamC.entity.location;

import java.util.List;

public interface LocationDAO {
    public List<Location> getAllLocations();
    public Location getLocation(int nodeID);

    public boolean insertLocation(Location location);
    public boolean updateLocation(Location location);
    public boolean deleteLocation(Location location);
}
