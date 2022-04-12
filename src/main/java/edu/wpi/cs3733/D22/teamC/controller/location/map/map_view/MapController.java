package edu.wpi.cs3733.D22.teamC.controller.location.map.map_view;

import edu.wpi.cs3733.D22.teamC.controller.location.map.BaseMapViewController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    // Constants
    protected final static double MAX_SCALE = 1.3f;
    protected final static double MIN_SCALE = 0.7f;

    // FXML
    @FXML MFXScrollPane scrollPane;
    @FXML ImageView mapImage;
    @FXML Pane mapPane;
    @FXML StackPane stackPane;

    // Variables
    public List<LocationNode> locationNodes = new ArrayList<>();
    protected LocationNode activeLocation;

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
            List<LocationNode> locationNodeList = locationNodes.stream().filter(locationNode -> locationNode.location.getNodeID().equals(location.getNodeID())).collect(Collectors.toList());
            return (locationNodeList.size() > 0) ? locationNodeList.get(0) : null;
        }

        /**
         * Remove all Location Nodes from the Map View.
         */
        public final void removeAllLocationNodes() {
            locationNodes.forEach(locationNode -> locationNode.remove(mapPane));
            locationNodes = new ArrayList<>();
        }

        /**
         * Render a list of Locations to Location Nodes for the map View.
         * @param locations List of Locations to render.
         * @return List of rendered Location Nodes.
         */
        public final void renderLocationsNodes(List<Location> locations) {
            List<LocationNode> locationNodes = new ArrayList<>();

            if (locations != null) locations.forEach(this::addLocationNode);
        }

        /**
         * Create a Location Node for the given Location.
         * @param location The Location to create the Location Node for.
         */
        public void addLocationNode(Location location) {
            // Load Location Node
            LocationNode locationNode = LocationNode.loadNewLocationNode(this);
            locationNode.setLocation(location);
            locationNodes.add(locationNode);

            // Setup Location Node Events
            Circle circle = locationNode.getLocationNodeCircle();
            circle.setOnMouseEntered(e -> onMouseEnterNode(e, locationNode));
            circle.setOnMouseExited(e -> onMouseExitNode(e, locationNode));
            circle.setOnMouseClicked(e -> onMouseClickedNode(e, locationNode));
            circle.setOnMouseDragged(e -> onMouseDraggedNode(e, locationNode));

            locationNode.render(mapPane);
        }

        /**
         * Update a Location Node for the given Location.
         * @param location The Location to update the Location Node for.
         */
        public void updateLocationNode(Location location) {
            LocationNode locationNode = getLocationNode(location);
            locationNode.setLocation(location);
        }

        /**
         * Delete the Location Node.
         * @param location The Location whose Node is to be deleted.
         */
        public void removeLocationNode(Location location) {
            LocationNode locationNode = getLocationNode(location);
            locationNodes.remove(locationNode);
            locationNode.remove(mapPane);
        }
    //#endregion

    //#region Mouse Events
        protected void onMouseEnterNode(MouseEvent event, LocationNode locationNode) {
            if (activeLocation == null) {
                parentController.changeCurrentLocation(locationNode.location, false);
            }
        }

        protected void onMouseExitNode(MouseEvent event, LocationNode locationNode) {
            if (activeLocation == null) {
                parentController.changeCurrentLocation(null, false);
            }
        }

        protected void onMouseClickedNode(MouseEvent event, LocationNode locationNode) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click select toggle
                {
                    // Update current Location
                    parentController.changeCurrentLocation((locationNode != activeLocation) ? locationNode.location : null);
                }

                event.consume();
            }
        }

        protected void onMouseDraggedNode(MouseEvent event, LocationNode locationNode) {}

        protected void onMouseClickedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click reset active LocationNode
                {
                    if (activeLocation != null) {
                        parentController.changeCurrentLocation(null);
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
                stackPane.setScaleX(scale);
                stackPane.setScaleY(scale);

                event.consume();
            }
        }
    //#endregion

    //#region External Interaction
        public void setParentController(BaseMapViewController baseMapViewController) {
            this.parentController = baseMapViewController;
        }

        public void setLocation(Location location) {
            if (activeLocation != null) {
                activeLocation.deactivate();
                activeLocation = null;
            }

            if (location != null) {
                activeLocation = getLocationNode(location);
                activeLocation.activate();
            }
        }

        public void renderFloor(Floor floor, List<Location> locations) {
            // Reset State
            setLocation(null);

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
            List<Location> floorLocations = locations.stream().filter(location -> location.getFloor().equals(floor.getFloorID())).collect(Collectors.toList());

            // Load LocationNodes
            renderLocationsNodes(floorLocations);
        }
    //#endregion
}
