package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ManagerMapNodes<T extends IDEntity> extends Manager<T> {
    protected List<MapNode<T>> nodes = new ArrayList<>();
    protected MapNode<T> previewed;
    protected MapNode<T> focused;

    public ManagerMapNodes(MapViewController mapViewController) {
        super(mapViewController);
    }

    //#region Selection Status
        public boolean isFocusing() {
        return (focused != null);
        }

        public boolean isFocused(MapNode<Location> mapNode) {
            return (focused == mapNode);
        }

        public boolean isPreviewing() {
            return (previewed != null);
        }

        public boolean isPreviewed(MapNode<Location> mapNode) {
            return (previewed == mapNode);
        }
    //#endregion

    //#region Map Node Manipulation
        /**
         * Get Map Node related to the provided Location.
         * @param location The Location to cross-reference for a Map Node.
         * @return The related Map Node.
         */
        public MapNode<T> getByLocation(Location location) {
            List<MapNode<T>> list = nodes.stream().filter(mapNode -> mapNode.getLocation() == location).collect(Collectors.toList());
            return (list.size()) == 0 ? null : list.get(0);
        }

        public abstract void removeNode(MapNode<T> mapNode);
    //#endregion
}
