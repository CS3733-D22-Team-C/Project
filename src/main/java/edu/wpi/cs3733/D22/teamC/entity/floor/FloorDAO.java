package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.util.List;

public interface FloorDAO {
    public List<Location> getAllLocations(int floorID);
    public List<Floor> getAllFloors();
    public Floor getFloor(int floorID);

    public int insertFloor(Floor floor);
    public boolean deleteFloor(Floor floor);
    public boolean updateFloor(Floor floor);


}
