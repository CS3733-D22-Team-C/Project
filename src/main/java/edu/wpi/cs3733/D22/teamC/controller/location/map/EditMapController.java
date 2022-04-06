package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAOImpl;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class EditMapController extends ViewMapController {
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
                newMapX = Math.max(0, newMapX);
                newMapX = Math.min(mapPane.getPrefWidth(), newMapX);
                double newMapY = mapLocation.node.getCenterY() + offsetY;
                newMapY = Math.max(0, newMapY);
                newMapY = Math.min(mapPane.getPrefHeight(), newMapY);

                mapLocation.node.setCenterX(newMapX);
                mapLocation.node.setCenterY(newMapY);
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
        mapPane.getChildren().add(newMapLoc.node);

        Location location = newMapLoc.location;
        location.setFloor(parentController.getFloor().getFloorID());
        location.setX((int) x);
        location.setY((int) y);

        additions.add(newMapLoc);
    }

    /**
     * Delete the MapLocation Node. Prepare the associated Location as a deletion.
     * @param mapLocation The MapLocation to be deleted.
     */
    private void deleteMapLocation(MapLocation mapLocation) {
        mapPane.getChildren().remove(mapLocation.node);

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
            locationDAO.deleteLocation(mapLocation.location);
        }
        deletions = new ArrayList<>();
    }
}
