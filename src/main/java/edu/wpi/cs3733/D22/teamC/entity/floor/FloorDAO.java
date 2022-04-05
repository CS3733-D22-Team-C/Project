package edu.wpi.cs3733.D22.teamC.entity.floor;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.util.List;

public interface FloorDAO {
    public List<Location> getAllLocations(int floor);
    public List<Floor> getAllFloors(); // equivalent to getalllocations() on locationDAO and getallsr
    public Floor getFloor(int floorID);



    public int insertFloor(Floor floor);
    public boolean deleteFloor(Floor floor);
    public boolean updateFloor(Floor floor);


}
