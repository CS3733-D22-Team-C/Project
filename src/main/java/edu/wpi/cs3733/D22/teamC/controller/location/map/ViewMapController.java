package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewMapController implements Initializable {
    protected class MapLocation {
        public Circle node;
        public Location location;

        public MapLocation(Location location) {
            this.location = location;
            this.node = createNode(location.getX(), location.getY());
        }

        public MapLocation(double x, double y) {
            this.location = new Location();
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

        /**
         * Update Location Position from MapLocation Node attributes.
         */
        public void updateLocationPosition() {
            this.location.setX((int) this.node.getCenterX());
            this.location.setY((int) this.node.getCenterY());
        }
    }

    // Constants
    protected final static double MAX_SCALE = 1.1f;
    protected final static double MIN_SCALE = 0.8f;
    protected final static int MAP_BUFFER = 100;

    // FXML
    @FXML ScrollPane scroll;
    @FXML Pane map;

    // Variables
    protected MapLocation clickedMapLocation, hoveredMapLocation;
    protected List<MapLocation> mapLocations;

    // References
    protected BaseMapViewController baseMapViewController;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        // Testing !!!
        LocationDAO locationDAO = new LocationDAOImpl();
        mapLocations = renderLocations(locationDAO.getAllLocations());
        for (MapLocation mapLocation : mapLocations) {
            map.getChildren().add(mapLocation.node);
        }

        // Initialize ScrollPane Size (Should be rewritten to make use of full screen space
        scroll.setPrefViewportHeight(750);
        scroll.setPrefViewportWidth(750);

        // Initialize Map Functionality
        map.setOnMouseClicked(this::onMouseClickedMap);
        map.setOnMousePressed(this::onMousePressedMap);
        map.setOnMouseReleased(this::onMouseReleasedMap);
        map.setOnScroll(this::onMouseScrollMap);
    }

    private final List<MapLocation> renderLocations(List<Location> locations) {
        List<MapLocation> mapLocs = new ArrayList<>();
        int maxX = 0, maxY = 0;

        for (Location location : locations) {
            MapLocation mapLocation = new MapLocation(location);
            mapLocs.add(mapLocation);

            maxX = Math.max(maxX, location.getX());
            maxY = Math.max(maxY, location.getY());
        }

        // Initialize Map Size and Position
        updateMapSize(maxX , maxY);
        map.setTranslateX(0);
        map.setTranslateY(0);

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
                        //clickedMapLocation = null;
                        setClickMapLocation(null);
                    } else {
                        onActiveNode(mapLocation);
                        //clickedMapLocation = mapLocation;
                        setClickMapLocation(mapLocation);

                    }
                }

                event.consume();
            }
        }

    private void setClickMapLocation(MapLocation mapLocation) {
        // TODO: figure out how to bind things
        clickedMapLocation = mapLocation;
        baseMapViewController.onLocationFocus(clickedMapLocation);
    }

    public void setBaseMapViewController(BaseMapViewController baseMapViewController) {
        this.baseMapViewController = baseMapViewController;
    }

    protected void onMouseDraggedNode(MouseEvent event, MapLocation mapLocation) {}

        protected void onMouseClickedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click reset active MapLocation
                if (clickedMapLocation != null && clickedMapLocation != hoveredMapLocation) {
                    offActiveNode(clickedMapLocation);
                    //clickedMapLocation = null;
                    setClickMapLocation(null);
                }
            }
        }

        protected void onMousePressedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click set pannable map
                scroll.setPannable(true);
            }
        }

        protected void onMouseReleasedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click set pannable map
                scroll.setPannable(false);
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
        protected final void onHoverNode(MapLocation mapLocation) {
            mapLocation.node.setStroke(Color.GRAY);
            mapLocation.node.setStrokeWidth(5f);
        }

        protected final void offHoverNode(MapLocation mapLocation) {
            mapLocation.node.setStroke(Color.DARKSLATEGREY);
            mapLocation.node.setStrokeWidth(1f);
        }

        protected final void onActiveNode(MapLocation mapLocation) {
            mapLocation.node.setFill(Color.BLACK);
        }

        protected final void offActiveNode(MapLocation mapLocation) {
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

        protected void updateMapSize(double x, double y) {
            double maxX = Math.max(map.getPrefWidth(), x + MAP_BUFFER);
            double maxY = Math.max(map.getPrefHeight(), y + MAP_BUFFER);

            map.setPrefWidth(maxX);
            map.setPrefHeight(maxY);
        }

        protected void offsetAllLocations(double xOffset, double yOffset) {
            for (MapLocation mapLoc : mapLocations) {
                mapLoc.node.setCenterX(mapLoc.node.getCenterX() + xOffset);
                mapLoc.node.setCenterY(mapLoc.node.getCenterY() + yOffset);
            }
        }
    //#endregion


}
