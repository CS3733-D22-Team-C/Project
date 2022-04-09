package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EditMapController extends ViewMapController {
    //#region Mouse Events
        @Override
        protected void onMouseClickedNode(MouseEvent event, LocationNode locationNode) {
            super.onMouseClickedNode(event, locationNode);

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                // Single-Click delete LocationNode
                parentController.deleteLocation(locationNode.location);
            }
        }

        @Override
        protected void onMouseClickedMap(MouseEvent event) {
            super.onMouseClickedMap(event);

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Double-Click create new LocationNode
                if (event.getClickCount() == 2) {
                    parentController.addLocation((int) event.getX(), (int) event.getY());
                }
            }
        }

        @Override
        protected void onMouseDraggedNode(MouseEvent event, LocationNode locationNode) {
            super.onMouseDraggedNode(event, locationNode);

            if (event.getButton().equals(MouseButton.PRIMARY))  {
                double offsetX = event.getX() - locationNode.node.getCenterX();
                double offsetY = event.getY() - locationNode.node.getCenterY();

                int newMapX = (int) (locationNode.node.getCenterX() + offsetX);
                newMapX = Math.max(0, newMapX);
                newMapX = Math.min((int) mapPane.getPrefWidth(), newMapX);
                int newMapY = (int) (locationNode.node.getCenterY() + offsetY);
                newMapY = Math.max(0, newMapY);
                newMapY = Math.min((int) mapPane.getPrefHeight(), newMapY);

                locationNode.node.setCenterX(newMapX);
                locationNode.node.setCenterY(newMapY);

                locationNode.location.setX(newMapX);
                locationNode.location.setY(newMapY);

                parentController.touchLocation(locationNode.location);
            }
        }
    //#endregion
}
