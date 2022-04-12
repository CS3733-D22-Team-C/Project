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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LocationNode {
    // FXML
    @FXML private Group group;
    @FXML private Circle node;

    @FXML private Group medicalEquipmentGroup;

    @FXML private Group bedGroup;
    @FXML private Label bedCounter;

    @FXML private Group pumpGroup;
    @FXML private Label pumpCounter;

    @FXML private Group reclinerGroup;
    @FXML private Label reclinerCounter;

    @FXML private Group xrayGroup;
    @FXML private Label xrayCounter;

    // Variables
    Location location;

    public void setLocation(Location location) {
        this.location = location;

        // Initialize Location
        group.setTranslateX(location.getX());
        group.setTranslateY(location.getY());

        // Initialize Medical Equipment
        List<MedicalEquipment> medicalEquipments = new MedicalEquipmentDAO().getEquipmentByLocation(location.getNodeID());

        updateMedicalEquipmentGroup(bedGroup, bedCounter, medicalEquipments, MedicalEquipment.EquipmentType.Bed);
        updateMedicalEquipmentGroup(xrayGroup, xrayCounter, medicalEquipments, MedicalEquipment.EquipmentType.Portable_X_Ray);
        updateMedicalEquipmentGroup(reclinerGroup, reclinerCounter, medicalEquipments, MedicalEquipment.EquipmentType.Recliner);
        updateMedicalEquipmentGroup(pumpGroup, pumpCounter, medicalEquipments, MedicalEquipment.EquipmentType.Infusion_Pump);
    }

    //#region Location Node Interaction
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

    //#region Medical Equipment Interaction
        private void setMedicalEquipmentVisible(boolean visible) {
            medicalEquipmentGroup.setVisible(visible);
        }

        private List<MedicalEquipment> filterMedicalEquipment(List<MedicalEquipment> medicalEquipments, MedicalEquipment.EquipmentType equipmentType) {
            return medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
        }

        private void updateMedicalEquipmentGroup(Group group, Label counter, List<MedicalEquipment> medicalEquipments, MedicalEquipment.EquipmentType equipmentType) {
            List<MedicalEquipment> filtered = filterMedicalEquipment(medicalEquipments, equipmentType);
            counter.setText(Integer.toString(filtered.size()));
            group.setVisible((filtered.size() != 0));
        }
    //#region

    //#region Load Component
        public static LocationNode loadNewLocationNode() {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.class.getResource("view/location/map/location_node.fxml"));
                loader.load();
                return loader.getController();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
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
}
