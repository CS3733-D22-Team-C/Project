package edu.wpi.cs3733.D22.teamC.controller.location.map.map_view;

import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class EditMapController extends MapController {
    //#region Mouse Events
        @Override
        protected void onMouseClickedNode(MouseEvent event, LocationNode locationNode) {
            super.onMouseClickedNode(event, locationNode);

            if (event.getButton().equals(MouseButton.SECONDARY)) {
                // Single-Click delete LocationNode
                ((BaseMapViewController) parentController).deleteLocation(locationNode.location);
            }
        }

        @Override
        protected void onMouseClickedMap(MouseEvent event) {
            super.onMouseClickedMap(event);

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Double-Click create new LocationNode
                if (event.getClickCount() == 2) {
                    Location location = createLocation((int) event.getX(), (int) event.getY());
                    ((BaseMapViewController) parentController).addLocation(location);
                    ((BaseMapViewController) parentController).changeCurrentLocation(location);
                }
            }
        }

        @Override
        protected void onMouseDraggedNode(MouseEvent event, LocationNode locationNode) {
            super.onMouseDraggedNode(event, locationNode);

            if (event.getButton().equals(MouseButton.PRIMARY))  {
                locationNode.getLocationNodeCircle().setCenterX(event.getX());
                locationNode.getLocationNodeCircle().setCenterY(event.getY());

                locationNode.updatePosition();

                event.consume();
            }

            event.consume();
        }

        public Location createLocation(int x, int y) {
            // Create Location
            Location location = new Location();

            location.setX((int) x);
            location.setY((int) y);
            location.setFloor(((BaseMapViewController) parentController).getCurrentFloor().getFloorID());
            location.setNodeType(Location.NodeType.PATI);

            return location;
        }
    //#endregion
}
