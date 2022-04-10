package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MapController implements Initializable {
    protected class LocationNode {
        public Circle node;
        public Location location;

        public LocationNode(Location location) {
            this.location = location;
            this.node = createNode(location.getX(), location.getY());
        }

        public void updateNode(Location location) {
            this.location = location;
            this.node.setCenterX(location.getX());
            this.node.setCenterY(location.getY());
        }

        /**
         * Create this LocationNode node.
         * @param x x-coordinate for node location.
         * @param y y-coordinate for node location.
         * @return The circle drawn as the LocationNode's node.
         */
        public Circle createNode(double x, double y) {
            Circle circle = drawCircle(x, y);
            circle.setOnMouseEntered(e -> onMouseEnterNode(e, this));
            circle.setOnMouseExited(e -> onMouseExitNode(e, this));
            circle.setOnMouseClicked(e -> onMouseClickedNode(e, this));
            circle.setOnMouseDragged(e -> onMouseDraggedNode(e, this));
            return circle;
        }

        public void deactivate() {
            this.node.setFill(Color.DARKCYAN);
        }

        public void activate() {
            this.node.setFill(Color.BLACK);
        }

        public void unfocus() {
            this.node.setStroke(Color.DARKSLATEGREY);
            this.node.setStrokeWidth(1f);
        }

        public void focus() {
            this.node.setStroke(Color.GRAY);
            this.node.setStrokeWidth(5f);
        }
    }

    // Constants
    protected final static double MAX_SCALE = 1.25f;
    protected final static double MIN_SCALE = 0.75f;

    // FXML
    @FXML MFXScrollPane scrollPane;
    @FXML ImageView mapImage;
    @FXML Pane mapPane;

    // Variables
    protected List<LocationNode> locationNodes = new ArrayList<>();
    protected LocationNode clickedLocation;

    // References
    protected BaseMapViewController parentController;


    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        // Initialize Map Functionality
        mapPane.setOnMouseClicked(this::onMouseClickedMap);
        mapPane.setOnMousePressed(this::onMousePressedMap);
        mapPane.setOnMouseReleased(this::onMouseReleasedMap);
        mapPane.setOnScroll(this::onMouseScrollMap);
    }

    //#region Location Node Interaction
        private final LocationNode getLocationNode(Location location) {
            return locationNodes.stream().filter(node -> node.location.getNodeID() == location.getNodeID()).collect(Collectors.toList()).get(0);
        }

        /**
         * Remove all Location Nodes from the Map View.
         */
        public final void removeAllLocationNodes() {
            for (LocationNode locationNode : locationNodes) {
                mapPane.getChildren().remove(locationNode.node);
            }
            locationNodes = new ArrayList<>();
        }

        /**
         * Render a list of Locations to Location Nodes for the map View.
         * @param locations List of Locations to render.
         * @return List of rendered Location Nodes.
         */
        public final List<LocationNode> renderLocationsNodes(List<Location> locations) {
            List<LocationNode> locationNodes = new ArrayList<>();

            if (locations != null) {
                for (Location location : locations) {
                    LocationNode locationNode = new LocationNode(location);
                    locationNodes.add(locationNode);
                    mapPane.getChildren().add(locationNode.node);
                }
            }

            return locationNodes;
        }

        /**
         * Create a Location Node for the given Location.
         * @param location The Location to create the Location Node for.
         */
        public void addLocationNode(Location location) {
            LocationNode newMapLoc = new LocationNode(location);
            locationNodes.add(newMapLoc);
            mapPane.getChildren().add(newMapLoc.node);
        }

        /**
         * Update a Location Node for the given Location.
         * @param location The Location to update the Location Node for.
         */
        public void updateLocationNode(Location location) {
            LocationNode locationNode = getLocationNode(location);
            locationNode.updateNode(location);
        }

        /**
         * Delete the Location Node.
         * @param location The Location whose Node is to be deleted.
         */
        public void removeLocationNode(Location location) {
            LocationNode locationNode = getLocationNode(location);
            mapPane.getChildren().remove(locationNode.node);
            locationNodes.remove(locationNode);
        }
    //#endregion

    //#region Mouse Events
        protected void onMouseEnterNode(MouseEvent event, LocationNode locationNode) {
            locationNode.focus();
            if (clickedLocation == null) {
                parentController.setCurrentLocation(locationNode.location, false);
            }
        }

        protected void onMouseExitNode(MouseEvent event, LocationNode locationNode) {
            locationNode.unfocus();
            if (clickedLocation == null) {
                parentController.setCurrentLocation(null, false);
            }
        }

        protected void onMouseClickedNode(MouseEvent event, LocationNode locationNode) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click select toggle
                {
                    // Update clicked Location Node
                    setClickedLocation(locationNode.location);

                    // Update current Location
                    parentController.setCurrentLocation((clickedLocation != null) ? clickedLocation.location : null);
                }

                event.consume();
            }
        }

        protected void onMouseDraggedNode(MouseEvent event, LocationNode locationNode) {}

        protected void onMouseClickedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click reset active LocationNode
                {
                    if (clickedLocation != null) {
                        parentController.setCurrentLocation(null);
                    }
                }
            }
        }

        protected void onMousePressedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click set pannable map
                scrollPane.setPannable(true);
            }
        }

        protected void onMouseReleasedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click set pannable map
                scrollPane.setPannable(false);
            }
        }

        protected void onMouseScrollMap(ScrollEvent event) {
            if (event.getDeltaY() != 0) {
                double scale = (event.getDeltaY() < 0)
                        ? Math.max(mapPane.getScaleX() - 0.1, MIN_SCALE)
                        : Math.min(mapPane.getScaleX() + 0.1, MAX_SCALE);
                mapPane.setScaleX(scale);
                mapImage.setScaleX(scale);
                mapPane.setScaleY(scale);
                mapImage.setScaleY(scale);

                event.consume();
            }
        }
    //#endregion

    //#region External Interaction
        public void setParentController(BaseMapViewController baseMapViewController) {
            this.parentController = baseMapViewController;
        }

        public void setClickedLocation(Location location) {
            if (clickedLocation != null) {
                clickedLocation.deactivate();
                clickedLocation = null;
            }
            if (location != null) {
                clickedLocation = getLocationNode(location);
                clickedLocation.activate();
            }
        }

        public void renderFloor(Floor floor) {
            // Reset State
            clickedLocation = null;

            // Reset LocationNodes
            removeAllLocationNodes();

            // Set Image
            // TODO: Pull Image from DB
            Path filePath = Paths.get("maps/" + floor.getImageSrc());
            Image image = new Image("file:" + filePath);
            mapImage.setImage(image);
            mapPane.setPrefWidth(image.getWidth());
            mapPane.setPrefHeight(image.getHeight());

            // Load Locations
            List<Location> locations = new FloorDAO().getAllLocations(floor.getFloorID());

            // Load LocationNodes
            locationNodes = renderLocationsNodes(locations);
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
