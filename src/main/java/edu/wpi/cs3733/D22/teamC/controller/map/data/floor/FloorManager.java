package edu.wpi.cs3733.D22.teamC.controller.map.data.floor;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.Manager;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;

public class FloorManager extends Manager<Floor> {
    public FloorManager(MapViewController mapViewController) {
        super(mapViewController);

        all = new FloorDAO().getAll();
    }
}
