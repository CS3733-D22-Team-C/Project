package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class EditMapController extends ViewMapController {
    private final static double MAP_GROWTH = 2;

    List<MapLocation> additions = new ArrayList<>();
    List<MapLocation> deletions = new ArrayList<>();

    //#region Mouse Events
        @Override
        protected void onMouseClickedNode(MouseEvent event, MapLocation mapLocation) {
            super.onMouseClickedNode(event, mapLocation);

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                // Single-Click delete MapLocation
                deleteMapLocation(mapLocation);
            }
        }

        @Override
        protected void onMouseClickedMap(MouseEvent event) {
            super.onMouseClickedMap(event);

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Double-Click create new MapLocation
                if (event.getClickCount() == 2) {
                    addMapLocation(event.getX(), event.getY());
                    updateMapSize(event.getX(), event.getY());
                }
            }
        }

        @Override
        protected void onMouseDraggedNode(MouseEvent event, MapLocation mapLocation) {
            super.onMouseDraggedNode(event, mapLocation);

            if (event.getButton().equals(MouseButton.PRIMARY))  {
                double offsetX = event.getX() - mapLocation.node.getCenterX();
                double offsetY = event.getY() - mapLocation.node.getCenterY();

                double newMapX = mapLocation.node.getCenterX() + offsetX;
                newMapX = Math.max(newMapX, MAP_BUFFER-MAP_GROWTH);
                double newMapY = mapLocation.node.getCenterY() + offsetY;
                newMapY = Math.max(newMapY, MAP_BUFFER-MAP_GROWTH);

                mapLocation.node.setCenterX(newMapX);
                mapLocation.node.setCenterY(newMapY);

                if (newMapX < MAP_BUFFER || newMapY < MAP_BUFFER) {
                    offsetAllLocations(Math.max(MAP_BUFFER-newMapX, 0), Math.max(MAP_BUFFER-newMapY, 0));
                    updateMapSize(map.getPrefWidth() - MAP_BUFFER + MAP_GROWTH, map.getPrefHeight() - MAP_BUFFER + MAP_GROWTH);
                } else if (newMapX + MAP_BUFFER > map.getPrefWidth() || newMapY  + MAP_BUFFER > map.getPrefHeight()) {
                    updateMapSize(event.getX(), event.getY());
                }
            }
        }
    //#endregion

    /**
     * Create a MapLocation Node. Prepare the associated Location as an addition.
     * @param x X-coordinate of new MapLocation Node.
     * @param y Y-coordinate of new MapLocation Node.
     */
    private void addMapLocation(double x, double y) {
        MapLocation newMapLoc = new MapLocation(x, y);
        mapLocations.add(newMapLoc);
        map.getChildren().add(newMapLoc.node);

        additions.add(newMapLoc);
    }

    /**
     * Delete the MapLocation Node. Prepare the associated Location as a deletion.
     * @param mapLocation The MapLocation to be deleted.
     */
    private void deleteMapLocation(MapLocation mapLocation) {
        map.getChildren().remove(mapLocation.node);

        if (additions.contains(mapLocation)) {
            additions.remove(mapLocation);
        } else {
            deletions.add(mapLocation);
        }
    }

    /**
     * Save the currently edited map by pushing all changes, additions, and deletions to the DB.
     */
    public void saveMap() {
        LocationDAO locationDAO = new LocationDAOImpl();

        // Update non-addition and non-deletions locations
        for (MapLocation mapLocation : mapLocations) {
            if (!additions.contains(mapLocation) && !deletions.contains(mapLocation)) {
                mapLocation.updateLocationPosition();
                locationDAO.updateLocation(mapLocation.location);
            }
        }

        // Insert addition locations
        for (MapLocation mapLocation : additions) {
            locationDAO.insertLocation(mapLocation.location);
        }
        additions = new ArrayList<>();

        // Delete deletion locations
        for (MapLocation mapLocation : deletions) {
            locationDAO.insertLocation(mapLocation.location);
        }
        deletions = new ArrayList<>();
    }
}
