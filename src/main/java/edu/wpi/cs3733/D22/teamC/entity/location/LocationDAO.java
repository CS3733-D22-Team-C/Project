package edu.wpi.cs3733.D22.teamC.entity.location;

import java.util.List;

public interface LocationDAO {
    public List<Location> getAllLocations();
    public Location getLocation(String nodeID);
    public void updateLocation(Location location);
    public void deleteLocation(Location location);
}
