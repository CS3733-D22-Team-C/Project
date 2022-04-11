package edu.wpi.cs3733.D22.teamC.controller.location.map.map_view;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.stream.Collectors;

public class LocationNode {
    // FXML
    @FXML private Group group;

    @FXML private Group medicalEquipmentGroup;

    @FXML private Group bedGroup;
    @FXML private Label bedCounter;

    @FXML private Group pumpGroup;
    @FXML private Label pumpCounter;

    @FXML private Group reclinerGroup;
    @FXML private Label reclinerCounter;

    @FXML private Group xrayGroup;
    @FXML private Label xrayCounter;

    @FXML private Circle node;

    // Variables
    private Location location;

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

    //#region Medical Equipment Interaction
        private void setMedicalEquipmentVisible(boolean visible) {
            medicalEquipmentGroup.setOpacity(visible ? 1 : 0);
        }

        private List<MedicalEquipment> filterMedicalEquipment(List<MedicalEquipment> medicalEquipments, MedicalEquipment.EquipmentType equipmentType) {
            return medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
        }

        private void updateMedicalEquipmentGroup(Group group, Label counter, List<MedicalEquipment> medicalEquipments, MedicalEquipment.EquipmentType equipmentType) {
            List<MedicalEquipment> filtered = filterMedicalEquipment(medicalEquipments, equipmentType);
            counter.setText(Integer.toString(filtered.size()));
            group.setOpacity((filtered.size() == 0) ? 0 : 1);
        }
    //#region
}
