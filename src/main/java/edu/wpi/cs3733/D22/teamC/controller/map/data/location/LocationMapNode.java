package edu.wpi.cs3733.D22.teamC.controller.map.data.location;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.LocationNode;
import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.MapController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class LocationMapNode extends MapNode<Location> {
    // FXML
    @FXML private Group contextGroup;
    @FXML private Circle locationCircle;

    public LocationMapNode(ManagerMapNodes<Location> manager, Location location) {
        super(manager, location);

        // Initialize Location
        manager.getMap().getChildren().add(node);
        setPosition(location.getX(), location.getY());
    }

    //#region Rendering
        @Override
        public void setPosition(int x, int y) {
            node.setTranslateX(x);
            node.setTranslateY(y);

            location.setX(x);
            location.setY(y);
        }

        /**
         * Updates Position of Location Map Node based on the moved Circle.
         */
        public void updatePosition() {
            int xOffset = (int) locationCircle.getCenterX();
            int yOffset = (int) locationCircle.getCenterY();

            int x = location.getX() + xOffset;
            int y = location.getY() + yOffset;

            locationCircle.setCenterX(0);
            locationCircle.setCenterY(0);

            setPosition(x, y);
        }
    //#endregion

    //#region State Updating
        @Override
        public void onFocus() {
            manager.unfocusAll();

            locationCircle.getStyleClass().add("active");
            manager.changeCurrent(location);
        }

        @Override
        public void offFocus() {
            locationCircle.getStyleClass().remove("active");
            manager.changeCurrent(null);
        }

        @Override
        public void onPreview() {
            if (!manager.isFocusing()) manager.changeCurrent(location);
        }

        @Override
        public void offPreview() {
            if (!manager.isFocusing()) manager.changeCurrent(null);
        }
    //#endregion

    //#region FXML Events
        @FXML
        protected void onMouseEnterNode(MouseEvent event) {
            node.toFront();

            manager.preview(this);
        }

        @FXML
        protected void onMouseExitNode(MouseEvent event) {
            node.toBack();

            manager.unpreview(this);
        }

        @FXML
        protected void onMouseClickedNode(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click select toggle
                if (manager.isFocusing() && manager.getCurrent() == location) {
                    manager.unfocus(this);
                } else {
                    manager.focus(this);
                }

                event.consume();
            }

            if (((LocationManager) manager).isEditMode()) rightClickDelete(event);
        }

        @FXML
        protected void onMouseDraggedNode(MouseEvent event) {
            if (((LocationManager) manager).isEditMode()) leftDragMove(event);
        }
    //#endregion

    //#region Toggleable Events
        /**
         * Right Click Node, Delete Location
         * @param event MouseEvent
         */
        private void rightClickDelete(MouseEvent event) {
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                manager.removeObject(location);
            }
        }

        /**
         * Left Drag Node, Move Location
         * @param event
         */
        private void leftDragMove(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY))  {
                locationCircle.setCenterX(event.getX());
                locationCircle.setCenterY(event.getY());

                updatePosition();
                ((LocationManager) manager).updatesOccured();
            }
        }
    //#endregion

    //#endregion Loading
        protected String getNodePath() {
            return "view/map/nodes/location.fxml";
        }
    //#endregion
}
