package edu.wpi.cs3733.D22.teamC.controller.location.map;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EditMapController extends ViewMapController {
    @Override
    protected void onMouseClickedMap(MouseEvent event) {
        super.onMouseClickedMap(event);

        if (event.getButton().equals(MouseButton.PRIMARY)) {
            // Double-Click create new MapLocation
            if (event.getClickCount() == 2) {
                MapLocation newMapLoc = new MapLocation(event.getX(), event.getY());
                mapLocations.add(newMapLoc);
                map.getChildren().add(newMapLoc.node);
                updateMapSize(event.getX(), event.getY());
            }
        }
    }

    @Override
    protected void onMouseDraggedNode(MouseEvent event, MapLocation mapLocation) {
        double offsetX = event.getX() - mapLocation.node.getCenterX();
        double offsetY = event.getY() - mapLocation.node.getCenterY();

        mapLocation.node.setCenterX(mapLocation.node.getCenterX() + offsetX);
        mapLocation.node.setCenterY(mapLocation.node.getCenterY() + offsetY);

        updateMapSize(event.getX(), event.getY());
    }
}
