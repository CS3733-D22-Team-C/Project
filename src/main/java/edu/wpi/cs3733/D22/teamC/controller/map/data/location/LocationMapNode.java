package edu.wpi.cs3733.D22.teamC.controller.map.data.location;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class LocationMapNode extends MapNode<Location> {
    // Constants
    private static final String NODE_PATH = "view/map/nodes/location.fxml";

    // FXML
    @FXML private Group contextGroup;
    @FXML private Circle locationCircle;

    public LocationMapNode(LocationManager manager, Location location) {
        super(manager, location);

        // Load Node
        App.View<MapNode<Location>> view = App.instance.loadView(NODE_PATH, this);
        Parent root = (Parent) view.getNode();
        this.node = view.getNode();

        // Initialize Location
        manager.getMapController().getMap().getChildren().add(node);
        setPosition(location.getX(), location.getY());
    }

    //#region Rendering
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
        public void onFocus() {
            locationCircle.getStyleClass().add("active");
            manager.changeCurrent(location);
        }

        public void offFocus() {
            locationCircle.getStyleClass().remove("active");
            manager.changeCurrent(null);
        }

        public void onPreview() {
            if (!((LocationManager) manager).isFocusing()) manager.changeCurrent(location);
        }

        public void offPreview() {
            if (!((LocationManager) manager).isFocusing()) manager.changeCurrent(null);
        }
    //#endregion

    //#region FXML Events
        @FXML
        protected void onMouseEnterNode(MouseEvent event) {
            node.toFront();

            ((LocationManager) manager).preview(this);
        }

        @FXML
        protected void onMouseExitNode(MouseEvent event) {
            if (!(((LocationManager) manager).isFocused(this))) node.toBack();

            ((LocationManager) manager).unpreview();
        }

        @FXML
        protected void onMouseClickedNode(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click select toggle
                if (((LocationManager) manager).isFocused(this)) {
                    ((LocationManager) manager).unfocus();
                } else {
                    ((LocationManager) manager).focus(this);
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
                int currentX = (int) node.getTranslateX();
                int maxX = (int) manager.getMapController().getMap().getPrefWidth();
                int newX = (int) Math.max(-currentX, (Math.min(maxX-currentX, event.getX())));

                int currentY = (int) node.getTranslateY();
                int maxY = (int) manager.getMapController().getMap().getPrefHeight();
                int newY = (int) Math.max(-currentY, (Math.min(maxY-currentY, event.getY())));

                locationCircle.setCenterX(newX);
                locationCircle.setCenterY(newY);

                updatePosition();
                ((LocationManager) manager).updatesOccured();
            }
        }
    //#endregion

    //#endregion Getters
        public Group getContextGroup() {
            return contextGroup;
        }
    //#endregion
}
