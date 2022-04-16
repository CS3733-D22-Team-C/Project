package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ManagerMapNodes<T extends IDEntity> extends Manager<T> {
        // References
    // List of all nodes
    protected List<MapNode<T>> allNodes = new ArrayList<>();
    // List of preview nodes.
    protected List<MapNode<T>> previewed = new ArrayList<>();
    // List of focused nodes
    protected List<MapNode<T>> focused = new ArrayList<>();

    public ManagerMapNodes(MapViewController mapViewController) {
        super(mapViewController);
    }

    //#region Rendering
        public void clearAll() {
            previewed.forEach(this::unpreview);
            focused.forEach(this::unfocus);
        }

        public abstract MapNode<T> drawNode(T object);

        public void removeNode(MapNode<T> mapNode) {
            ((Group) mapNode.node).getChildren().clear();
            getMap().getChildren().remove(mapNode);
        }
    //#endregion

    //#region Filtering
        public MapNode<T> getByLocation(Location location) {
            List<MapNode<T>> list = allNodes.stream().filter(mapNode -> mapNode.location == location).collect(Collectors.toList());
            return (list.size()) == 0 ? null : list.get(0);
        }

        public List<MapNode<T>> getAllNodes() {
            return allNodes;
        }

        public List<MapNode<T>> getPreviewed() {
            return previewed;
        }

        public List<MapNode<T>> getFocused() {
            return focused;
        }
    //#endregion

    //#region Map Node Interaction
        public void focus(MapNode<T> mapNode) {
            if (!focused.contains(mapNode)) {
                mapNode.onFocus();
                focused.add(mapNode);
            }
        }

        public void unfocus(MapNode<T> mapNode) {
            if (focused.contains(mapNode)) {
                mapNode.offFocus();
                focused.remove(mapNode);
            }
        }

        public void unfocusAll() {
            new ArrayList<>(focused).forEach(this::unfocus);
        }

        public void preview(MapNode<T> mapNode) {
            if (!previewed.contains(mapNode)) {
                mapNode.onPreview();
                previewed.add(mapNode);
            }
        }

        public void unpreview(MapNode<T> mapNode) {
            if (previewed.contains(mapNode)) {
                mapNode.offPreview();
                previewed.remove(mapNode);
            }
        }

        public void unpreviewAll() {
            new ArrayList<>(previewed).forEach(this::unpreview);
        }
    //#endregion

    //#region State
        public boolean isPreviewing() {
            return (previewed.size() != 0);
        }

        public boolean isFocusing() {
            return (focused.size() != 0);
        }
    //#endregion
}
