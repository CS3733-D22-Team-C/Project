package edu.wpi.cs3733.D22.teamC.controller.map;


import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.panel.LegendController;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.PopOver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Facilitates Map interaction as a go between for data (managers, nodes), panel (interaction, controls), and the Map.
 */
public class MapController implements Initializable {
    // Constants
    protected final static double MAX_SCALE = 1.3f;
    protected final static double MIN_SCALE = 0.7f;

    // FXML
    @FXML private Pane stackPane;
    @FXML private Pane mapPane;
    @FXML private ScrollPane scrollPane;
    @FXML private ImageView mapImage;

    @FXML private HBox topOverlay;
    @FXML private HBox centerOverlay;
    @FXML private HBox bottomOverlay;

    @FXML private Button legendButton;

    // References
    MapViewController mapViewController;

    //#region External Events
        @Override
        public void initialize(URL location, ResourceBundle resources) {
            App.View<LegendController> legendView = App.instance.loadView("view/map/panel/legend.fxml");

            PopOver popOver = new PopOver(legendView.getNode());
            popOver.arrowLocationProperty().setValue(PopOver.ArrowLocation.TOP_RIGHT);
            popOver.setTitle("Legend");

            legendView.getController().setup(this);

            legendButton.setOnAction(e -> popOver.show(legendButton));
        }

        public void setup(MapViewController mapViewController) {
            this.mapViewController = mapViewController;
        }

        public void setFloor(Floor newFloor) {
            Image image = new Image(new ByteArrayInputStream(newFloor.getImage()));
            mapImage.setImage(image);
            mapPane.setPrefWidth(image.getWidth());
            mapPane.setPrefHeight(image.getHeight());
        }
    //#endregion

    //#region FXML Events
        @FXML
        protected void onMouseClickedMap(MouseEvent event) {
            mapViewController.getLocationManager().unfocus();
            if (mapViewController.getLocationManager().isEditMode()) doubleClickAddLocation(event);

            event.consume();
        }

        @FXML
        protected void onMousePressedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click allow map panning
                scrollPane.setPannable(true);
            }
        }

        @FXML
        protected void onMouseReleasedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click disallow map panning
                scrollPane.setPannable(false);
            }
        }

        @FXML
        protected void onMouseScrollMap(ScrollEvent event) {
            if (event.getDeltaY() != 0) {
                double scale = (event.getDeltaY() < 0)
                        ? Math.max(stackPane.getScaleX() - 0.1, MIN_SCALE)
                        : Math.min(stackPane.getScaleX() + 0.1, MAX_SCALE);
                stackPane.setScaleX(scale);
                stackPane.setScaleY(scale);

                event.consume();
            }
        }
    //#endregion

    //#region Toggleable Events
        /**
         * Double Click Map, Add Location.
         * @param event MouseEvent
         */
        public void doubleClickAddLocation(MouseEvent event) {
            if (event.getClickCount() == 2) {
                Location location = new Location();
                location.setX((int) event.getX());
                location.setY((int) event.getY());
                location.setFloor(mapViewController.getFloorManager().getCurrent());
                location.setNodeType(Location.NodeType.values()[0]);

                mapViewController.getLocationManager().addObject(location);
                mapViewController.getLocationManager().changeCurrent(location);
            }
        }
    //#endregion

    //#region Getters
        public Pane getMap() {
            return mapPane;
        }

        public HBox getBottomOverlay() {
            return bottomOverlay;
        }
        
        public MapViewController getMapViewController() {
            return mapViewController;
        }
    //#endregion
}
