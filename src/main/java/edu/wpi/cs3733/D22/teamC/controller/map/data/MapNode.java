package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.models.generic.SelectorWindow;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.Map;

public abstract class MapNode<T> {
        // References
    // The manager for this Map Node type.
    protected ManagerMapNodes<T> manager;
    // The graphical representation of this Map Node.
    protected Node node;
    // The related location for this Map Node.
    protected Location location;

    public MapNode(ManagerMapNodes<T> manager, Location location) {
        // Load Node
        App.View<MapNode<T>> view = App.instance.loadView(getNodePath(), this);
        Parent root = (Parent) view.getNode();

        this.manager = manager;
        this.node = view.getNode();
        this.location = location;
    }

    //#region Rendering
        protected abstract void setPosition(int x, int y);
    //#endregion

    //#region State Updating
        public abstract void onFocus();
        public abstract void offFocus();

        public abstract void onPreview();
        public abstract void offPreview();
    //#endregion

    //#endregion Loading
        protected abstract String getNodePath();
    //#endregion
}
