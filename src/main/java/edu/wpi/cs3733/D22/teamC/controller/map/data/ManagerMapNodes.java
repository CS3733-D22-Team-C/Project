package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ManagerMapNodes<T extends IDEntity> extends Manager<T> {
    public ManagerMapNodes(MapViewController mapViewController) {
        super(mapViewController);
    }
}
