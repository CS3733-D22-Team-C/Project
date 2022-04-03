package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    protected class MapLocation {
        public Group group;
        public Circle node;
        public Location location;

        public MapLocation(Location location) {
            this.location = location;
            this.node = createNode(location.getX(), location.getY());
            this.group = new Group(this.node);
        }

        public MapLocation(double x, double y) {
            this.location = null;
            this.node = createNode(x, y);
            this.group = new Group(this.node);
        }

        /**
         * Create this MapLocation node.
         * @param x x-coordinate for node location.
         * @param y y-coordinate for node location.
         * @return The circle drawn as the MapLocation's node.
         */
        public Circle createNode(double x, double y) {
            return drawCircle(x, y);
        }
    }

    @FXML
    Pane map;

    Group root = new Group();

    protected MapLocation clickedMapLocation, hoveredMapLocation;
    protected List<MapLocation> mapLocations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Testing !!!
        LocationDAO locationDAO = new LocationDAOImpl();
        mapLocations = renderLocations(locationDAO.getAllLocations()).subList(0, 10);
        for (MapLocation mapLocation : mapLocations) {
            root.getChildren().add(mapLocation.group);
        }
        map.setOnMouseClicked(this::onMouseClickedMap);

        root.setTranslateX(map.getPrefWidth() / 2);
        root.setTranslateY(map.getPrefHeight() / 2);
        map.getChildren().add(root);
    }

    private List<MapLocation> renderLocations(List<Location> locations) {
        List<MapLocation> mapLocs = new ArrayList<>();

        for (Location location : locations) {
            MapLocation mapLocation = new MapLocation(location);

            mapLocation.group.setOnMouseEntered(e -> onMouseEnterNode(e, mapLocation));
            mapLocation.group.setOnMouseExited(e -> onMouseExitNode(e, mapLocation));
            mapLocation.group.setOnMouseClicked(e -> onMouseClickedNode(e, mapLocation));

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
                if (clickedMapLocation != null) offActiveNode(clickedMapLocation);

                if (clickedMapLocation == mapLocation) {
                    clickedMapLocation = null;
                } else {
                    onActiveNode(mapLocation);
                    clickedMapLocation = mapLocation;
                }
            }
        }

        protected void onMouseClickedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click reset active MapLocation
                if (clickedMapLocation != null && clickedMapLocation != hoveredMapLocation) {
                    offActiveNode(clickedMapLocation);
                    clickedMapLocation = null;
                }

                // Double-Click create new MapLocation
                if (event.getClickCount() == 2) {
                    System.out.println("Double: " + event.getX() + " " + event.getY());
                    MapLocation newMapLoc = new MapLocation(event.getX(), event.getY());
                    mapLocations.add(newMapLoc);
                    root.getChildren().add(newMapLoc.group);
                }
            }
        }
    //#endregion

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
