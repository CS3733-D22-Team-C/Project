package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
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
            this.location.setNodeType(Location.NodeType.values()[0]);

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

    // FXML
    @FXML ScrollPane scrollPane;
    @FXML ImageView mapImage;
    @FXML Pane mapPane;

    // Variables
    protected MapLocation clickedMapLocation, hoveredMapLocation;
    protected List<MapLocation> mapLocations;

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

    private final void resetLocations() {
        if (mapLocations != null) {
            for (MapLocation mapLocation : mapLocations) {
                mapPane.getChildren().remove(mapLocation.node);
            }
        }
    }

    private final List<MapLocation> renderLocations(List<Location> locations) {
        if (locations != null) {
            List<MapLocation> mapLocs = new ArrayList<>();

            for (Location location : locations) {
                MapLocation mapLocation = new MapLocation(location);
                mapLocs.add(mapLocation);
            }

            // Initialize Map Size and Position
            mapPane.setTranslateX(0);
            mapPane.setTranslateY(0);

            return mapLocs;
        }

        return null;
    }

    //#region Update State
        private void setClickMapLocation(MapLocation mapLocation) {
            clickedMapLocation = mapLocation;
            parentController.getLocationInfoController().onMapLocationFocus(mapLocation);
        }

        private void setHoveredMapLocation(MapLocation mapLocation) {
            hoveredMapLocation = mapLocation;
            if (clickedMapLocation == null) {
                parentController.getLocationInfoController().onMapLocationFocus(mapLocation);
            }
        }
    //#endregion

    //#region External Setup
        public void setParentController(BaseMapViewController baseMapViewController) {
            this.parentController = baseMapViewController;
        }

        public void setFloor(Floor floor) {
            resetLocations();

            // Set Image
            Path filePath = Paths.get("maps/" + floor.getImageSrc());
            System.out.println(filePath);
            Image image = new Image("file:" + filePath);
            mapImage.setImage(image);
            mapPane.setPrefWidth(image.getWidth());
            mapPane.setPrefHeight(image.getHeight());


            FloorDAO floorDAO = new FloorDAO();
            List<Location> locations = floorDAO.getAllLocations(floor.getFloorID());
            mapLocations = renderLocations(locations);
            for (MapLocation mapLocation : mapLocations) {
                mapPane.getChildren().add(mapLocation.node);
            }
        }
    //#endregion

    //#region Mouse Events
        protected void onMouseEnterNode(MouseEvent event, MapLocation mapLocation) {
            setHoveredMapLocation(mapLocation);
            onHoverNode(mapLocation);
        }

        protected void onMouseExitNode(MouseEvent event, MapLocation mapLocation) {
            setHoveredMapLocation(null);
            offHoverNode(mapLocation);
        }

        protected void onMouseClickedNode(MouseEvent event, MapLocation mapLocation) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click select toggle
                {
                    if (clickedMapLocation != null) offActiveNode(clickedMapLocation);

                    if (clickedMapLocation == mapLocation) {
                        setClickMapLocation(null);
                    } else {
                        onActiveNode(mapLocation);
                        setClickMapLocation(mapLocation);
                    }
                }

                event.consume();
            }
        }

        protected void onMouseDraggedNode(MouseEvent event, MapLocation mapLocation) {}

        protected void onMouseClickedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                // Single-Click reset active MapLocation
                if (clickedMapLocation != null && clickedMapLocation != hoveredMapLocation) {
                    offActiveNode(clickedMapLocation);
                    setClickMapLocation(null);
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
    //#endregion
}
