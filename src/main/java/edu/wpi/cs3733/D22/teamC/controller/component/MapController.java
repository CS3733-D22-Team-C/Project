package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    protected class MapLocation {
        public Circle node;
        public Location location;

        public MapLocation(Location location) {
            this.location = location;
            this.node = createNode(location.getX(), location.getY());
        }

        public MapLocation(double x, double y) {
            this.location = null;
            this.node = createNode(x, y);
        }

        /**
         * Create this MapLocation node.
         * @param x x-coordinate for node location.
         * @param y y-coordinate for node location.
         * @return The circle drawn as the MapLocation's node.
         */
        public Circle createNode(double x, double y) {
            Circle circle = drawCircle(x, y);
            circle.setOnMouseEntered(e -> onMouseEnterNode(e, this));
            circle.setOnMouseExited(e -> onMouseExitNode(e, this));
            circle.setOnMouseClicked(e -> onMouseClickedNode(e, this));
            circle.setOnMouseDragged(e -> onMouseDraggedNode(e, this));
            return circle;
        }
    }

    // Constants
    private final static double MAX_SCALE = 4f;
    private final static double MIN_SCALE = 0.25f;

    // FXML
    @FXML ScrollPane scroll;
    @FXML Pane map;

    // Variables
    protected MapLocation clickedMapLocation, hoveredMapLocation;
    protected List<MapLocation> mapLocations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Testing !!!
        LocationDAO locationDAO = new LocationDAOImpl();
        mapLocations = renderLocations(locationDAO.getAllLocations());
        for (MapLocation mapLocation : mapLocations) {
            map.getChildren().add(mapLocation.node);
        }


        map.setOnMouseClicked(this::onMouseClickedMap);
        map.setOnScroll(this::onMouseScrollMap);

        scroll.setPrefViewportHeight(500);
        scroll.setPrefViewportWidth(500);
    }

    private List<MapLocation> renderLocations(List<Location> locations) {
        List<MapLocation> mapLocs = new ArrayList<>();

        for (Location location : locations) {
            MapLocation mapLocation = new MapLocation(location);
            mapLocs.add(mapLocation);
        }

        return mapLocs;
    }

    //#region Mouse Events
        protected void onMouseEnterNode(MouseEvent event, MapLocation mapLocation) {
            hoveredMapLocation = mapLocation;
            onHoverNode(mapLocation);
        }

        protected void onMouseExitNode(MouseEvent event, MapLocation mapLocation) {
            hoveredMapLocation = null;
            offHoverNode(mapLocation);
        }

        protected void onMouseClickedNode(MouseEvent event, MapLocation mapLocation) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click select toggle
                {
                    if (clickedMapLocation != null) offActiveNode(clickedMapLocation);

                    if (clickedMapLocation == mapLocation) {
                        clickedMapLocation = null;
                    } else {
                        onActiveNode(mapLocation);
                        clickedMapLocation = mapLocation;
                    }
                }

                event.consume();
            }
        }

        protected void onMouseDraggedNode(MouseEvent event, MapLocation mapLocation) {
            double offsetX = event.getX() - mapLocation.node.getCenterX();
            double offsetY = event.getY() - mapLocation.node.getCenterY();

            mapLocation.node.setCenterX(mapLocation.node.getCenterX() + offsetX);
            mapLocation.node.setCenterY(mapLocation.node.getCenterY() + offsetY);
        }

        protected void onMouseClickedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click reset active MapLocation
                if (clickedMapLocation != null && clickedMapLocation != hoveredMapLocation) {
                    offActiveNode(clickedMapLocation);
                    clickedMapLocation = null;
                }

                // Double-Click create new MapLocation
                // Todo: Move to Edit-only controller
                if (event.getClickCount() == 2) {
                    MapLocation newMapLoc = new MapLocation(event.getX(), event.getY());
                    mapLocations.add(newMapLoc);
                    map.getChildren().add(newMapLoc.node);
                }
            }
        }

        protected void onMouseScrollMap(ScrollEvent event) {
            if (event.getDeltaY() != 0) {
                double scale = (event.getDeltaY() < 0)
                    ? Math.max(map.getScaleX() - 0.1, MIN_SCALE)
                    : Math.min(map.getScaleX() + 0.1, MAX_SCALE);
                map.setScaleX(scale);
                map.setScaleY(scale);

                event.consume();
            }
        }
    //#endregion

    //#region Node Interaction Feedback
        protected void onHoverNode(MapLocation mapLocation) {
            mapLocation.node.setStroke(Color.GRAY);
            mapLocation.node.setStrokeWidth(5f);
        }

        protected void offHoverNode(MapLocation mapLocation) {
            mapLocation.node.setStroke(Color.DARKSLATEGREY);
            mapLocation.node.setStrokeWidth(1f);
        }

        protected void onActiveNode(MapLocation mapLocation) {
            mapLocation.node.setFill(Color.BLACK);
        }

        protected void offActiveNode(MapLocation mapLocation) {
            mapLocation.node.setFill(Color.DARKCYAN);
        }
    //#endregion

    //#region Utility
        private Circle drawCircle(double x, double y) {
            Circle circle = new Circle();

            circle.setCenterX(x);
            circle.setCenterY(y);

            circle.setRadius(12.5f);
            circle.setFill(Color.DARKCYAN);

            circle.setStrokeWidth(1.0);
            circle.setStroke(Color.DARKSLATEGREY);

            return circle;
        }
    //#endregion
}
