package edu.wpi.cs3733.D22.teamC.controller.map.data.location;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.Manager;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;

import java.util.ArrayList;
import java.util.Iterator;

public class LocationManager extends ManagerMapNodes<Location> {
    public LocationManager(MapViewController mapViewController) {
        super(mapViewController);
    }

    public void renderFloor(Floor floor) {
        allNodes.forEach(this::removeNode);
        allNodes = new ArrayList<>();

        all = new FloorDAO().getAllLocations(floor.getFloorID());

        all.forEach(location -> allNodes.add(drawNode(location)));
    }

    public MapNode<Location> drawNode(Location object) {
        return new LocationMapNode(this, object);
    }
}
