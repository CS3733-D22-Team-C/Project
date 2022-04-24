package edu.wpi.cs3733.D22.teamC.controller.map.data;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.TimeZone;

public class MapCounter {
    // Constants
    private final String COUNTER_PATH = "view/map/nodes/counter.fxml";

    // FXML
    @FXML private Circle circle;
    @FXML private Label counterLabel;
    @FXML private Group root;

    // Variable
    private int count;
    private Location location;

    // References
    private Group parent;

    public MapCounter(Location location) {
        this.location = location;

        // Load FXML
        App.View<MapCounter> view = App.instance.loadView(COUNTER_PATH, this);
    }

    public void delete() {
        root.getChildren().clear();
        parent.getChildren().remove(root);
    }

    public void setParent(Group group) {
        parent = group;
        parent.getChildren().add(root);
    }

    public void setPosition(int x, int y) {
        root.setTranslateX(x);
        root.setTranslateY(y);
    }

    public void setCount(int count) {
        this.count = count;
        counterLabel.setText(Integer.toString(count));
        setVisible(count > 0);
    }

    public void setVisible(boolean visible) {
        root.setVisible(visible);
    }

    public Node getNode() {
        return circle;
    }

    public Location getLocation() {
        return location;
    }

    public int getCount() {
        return count;
    }
}
