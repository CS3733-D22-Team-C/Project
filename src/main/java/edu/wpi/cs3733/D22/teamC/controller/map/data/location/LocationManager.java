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

public class LocationManager extends ManagerMapNodes<Location> {
    public LocationManager(MapViewController mapViewController) {
        super(mapViewController);
    }

    //#region Map Node Manipulation
        public MapNode<Location> drawNode(Location location) {
            return new LocationMapNode(this, location);
        }
    //#endregion

    //#region Content Changes
        public void renderFloor(Floor floor) {
            allNodes.forEach(this::removeNode);
            allNodes = new ArrayList<>();

            all = new FloorDAO().getAllLocations(floor.getFloorID());

            all.forEach(location -> allNodes.add(drawNode(location)));
        }
    //#endregion
}
