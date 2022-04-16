package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class MapNode<T extends IDEntity> {
        // References
    // The manager for this Map Node type.
    protected Manager<T> manager;
    // The graphical representation of this Map Node.
    protected Node node;
    // The related location for this Map Node.
    protected Location location;

    public MapNode(Manager<T> manager, Location mapLocation) {
        this.manager = manager;
        this.location = mapLocation;
    }

    //#endregion Loading
        public Location getLocation() {
            return location;
        }

        public Node getNode() {
            return node;
        }
    //#endregion
}
