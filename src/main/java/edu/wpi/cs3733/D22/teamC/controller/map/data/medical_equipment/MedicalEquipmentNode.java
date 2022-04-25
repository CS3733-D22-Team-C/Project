package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class MedicalEquipmentNode extends MapNode<MedicalEquipment> {
    // Constants
    private static final Pair<Integer, Integer>[] COUNTER_OFFSETS = new Pair[] {
            new Pair(10, -58),
            new Pair(30, -28),
            new Pair(30, 2),
            new Pair(10, 32)
    };

    // Variables
    List<MedicalEquipment> medicalEquipments;

    // References
    Group contextGroup;
    private MedicalEquipmentCounter[] counters = new MedicalEquipmentCounter[MedicalEquipment.EquipmentType.values().length];

    public MedicalEquipmentNode(MedicalEquipmentManager manager, Location location) {
        super(manager, location);

        // Load FXML
        LocationMapNode locationMapNode = (LocationMapNode) manager.getMapViewController().getLocationManager().getByLocation(location);
        contextGroup = locationMapNode.getContextGroup();

        for (int i = 0; i < MedicalEquipment.EquipmentType.values().length; i++) {
            App.View<MedicalEquipmentCounter> view = App.instance.loadView(MedicalEquipmentManager.COUNTER_PATHS[i]);

            // Setup Controller
            MedicalEquipmentCounter controller = view.getController();

            contextGroup.getChildren().add(view.getNode());
            controller.setPosition(COUNTER_OFFSETS[i].getKey(), COUNTER_OFFSETS[i].getValue());
            controller.setType(MedicalEquipment.EquipmentType.values()[i]);
            controller.setup(this);

            counters[i] = controller;
        }

        updateValues();
    }


    public void updateValues() {
        MedicalEquipmentDAO dao = new MedicalEquipmentDAO();
        medicalEquipments = dao.getEquipmentByLocation(location.getID());

        for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
            List<MedicalEquipment> medicalEquipmentsByType = medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
            counters[equipmentType.ordinal()].setMedicalEquipments(medicalEquipmentsByType);
        }
    }

    //#region State Changes
        public void toPreviewMode() {
            for (MedicalEquipmentCounter counter : counters) {
                counter.setEditable(false);

                counter.setVisible((counter.getCount() != 0));
            }
        }

        public void toFocusMode() {
            for (MedicalEquipmentCounter counter : counters) {
                counter.setEditable(true);
                counter.setVisible(true);
            }
        }

        public void removeNode() {
            for (MedicalEquipmentCounter counter : counters) {
                counter.root.getChildren().clear();
                contextGroup.getChildren().remove(counter.root);
            }
        }
    //#endregion

    //#region Getters
        public List<MedicalEquipment> getMedicalEquipments() {
        return medicalEquipments;
    }
    //#endregion
}
