package edu.wpi.cs3733.D22.teamC.controller.location.map.map_view;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationNode extends Group {
    // Constants
    private final static Pair<Integer, Integer>[] MEDICAL_EQUIPMENT_COUNTER_NODE_OFFSETS = new Pair[] {
        new Pair<>(10, -55),
        new Pair<>(30, -25),
        new Pair<>(30, 5),
        new Pair<>(10, 35)
    };

    // FXML
    @FXML private Group group;
    @FXML private Group contextGroup;
    @FXML private Circle node;

    // References
    MapController mapController;
    public MedicalEquipmentCounterNode[] medicalEquipmentCounterNodes = new MedicalEquipmentCounterNode[MedicalEquipment.EquipmentType.values().length];

    // Variables
    Location location;

    public void setLocation(Location location) {
        this.location = location;

        // Initialize Location
        group.setTranslateX(location.getX());
        group.setTranslateY(location.getY());

        addMedicalEquipmentCounters();
    }

    //#region Medical Equipment Counter Node Interaction
        public void addMedicalEquipmentCounters() {
            for (MedicalEquipment.EquipmentType equipType : MedicalEquipment.EquipmentType.values()) {
                addMedicalEquipmentCounter(equipType);
            }
        }

        /**
         * Create a Medical Equipment Counter Node for the given Location.
         * @param equipmentType The Equipment Type to create the Location Node for.
         */
        public void addMedicalEquipmentCounter(MedicalEquipment.EquipmentType equipmentType) {
            // Load Medical Equipment Counter
            MedicalEquipmentCounterNode medicalEquipmentCounterNode = MedicalEquipmentCounterNode.loadNewMedicalEquipmentCounterNode(equipmentType);
            medicalEquipmentCounterNode.setLocationNode(this);
            medicalEquipmentCounterNodes[equipmentType.ordinal()] = medicalEquipmentCounterNode;

            medicalEquipmentCounterNode.render(contextGroup, MEDICAL_EQUIPMENT_COUNTER_NODE_OFFSETS[equipmentType.ordinal()]);
        }
    //#endregion

    //#region State Updates
        public void render(Pane pane) {
            pane.getChildren().add(group);
        }

        public void remove(Pane pane) {
            group.getChildren().clear();
            pane.getChildren().remove(group);
        }

        public void updatePosition() {
            int xOffset = (int) node.getCenterX();
            int yOffset = (int) node.getCenterY();

            int x = location.getX() + xOffset;
            int y = location.getY() + yOffset;

            location.setX(x);
            location.setY(y);

            node.setCenterX(0);
            node.setCenterY(0);

            setPosition(x, y);
        }

        public void setPosition(int x, int y) {
            group.setTranslateX(x);
            group.setTranslateY(y);
        }

        public void activate() {
            node.getStyleClass().add("active");
        }

        public void deactivate() {
            node.getStyleClass().remove("active");
        }
    //#endregion

    //#region External Interaction
        public Circle getLocationNodeCircle() {
            return node;
        }

        public Group getLocationNodeGroup() {
            return group;
        }
    //#endregion

    //#region Load Component
        public static LocationNode loadNewLocationNode(MapController mapController) {
            App.View<LocationNode> view = App.instance.loadView("view/location/map/map_view/location_node.fxml");
            LocationNode locationNode = view.getController();
            locationNode.mapController = mapController;
            return locationNode;
        }
    //#endregion
}
