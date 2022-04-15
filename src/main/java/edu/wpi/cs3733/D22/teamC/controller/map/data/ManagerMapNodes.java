package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;

public abstract class ManagerMapNodes<T> extends Manager<T> {
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

    //#region Map Node Interaction
        public void focus(MapNode<T> mapNode) {
            if (!focused.contains(mapNode)) {
                focused.add(mapNode);
                mapNode.onFocus();
            }
        }

        public void unfocus(MapNode<T> mapNode) {
            if (focused.contains(mapNode)) {
                focused.remove(mapNode);
                mapNode.offFocus();
            }
        }

        public void preview(MapNode<T> mapNode) {
            if (!previewed.contains(mapNode)) {
                previewed.add(mapNode);
                mapNode.onPreview();
            }
        }

        public void unpreview(MapNode<T> mapNode) {
            if (previewed.contains(mapNode)) {
                previewed.remove(mapNode);
                mapNode.offPreview();
            }
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
