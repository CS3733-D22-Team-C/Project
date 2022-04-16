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
    private static final String[] COUNTER_PATHS = {
            "view/map/nodes/medical_equipment/bed.fxml",
            "view/map/nodes/medical_equipment/pump.fxml",
            "view/map/nodes/medical_equipment/recliner.fxml",
            "view/map/nodes/medical_equipment/xray.fxml"
    };
    private static final Pair<Integer, Integer>[] COUNTER_OFFSETS = new Pair[] {
            new Pair(10, -55),
            new Pair(30, -25),
            new Pair(30, 5),
            new Pair(10, 35)
    };

    // References
    Group contextGroup;

    // References
    private MedicalEquipmentCounter[] counters = new MedicalEquipmentCounter[MedicalEquipment.EquipmentType.values().length];

    public MedicalEquipmentNode(MedicalEquipmentManager manager, Location location) {
        super(manager, location);

        // Load FXML
        LocationMapNode locationMapNode = (LocationMapNode) manager.getMapViewController().getLocationManager().getByLocation(location);
        contextGroup = locationMapNode.getContextGroup();

        for (int i = 0; i < MedicalEquipment.EquipmentType.values().length; i++) {
            App.View<MedicalEquipmentCounter> view = App.instance.loadView(COUNTER_PATHS[i]);

            // Setup Controller
            MedicalEquipmentCounter controller = view.getController();

            contextGroup.getChildren().add(view.getNode());
            controller.setPosition(COUNTER_OFFSETS[i].getKey(), COUNTER_OFFSETS[i].getValue());
            controller.setType(MedicalEquipment.EquipmentType.values()[i]);

            counters[i] = controller;
        }

        updateValues();
    }

    public void updateValues() {
        MedicalEquipmentDAO dao = new MedicalEquipmentDAO();
        List<MedicalEquipment> medicalEquipments = dao.getEquipmentByLocation(location.getID());

        for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
            List<MedicalEquipment> medicalEquipmentsByType = medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
            counters[equipmentType.ordinal()].setMedicalEquipments(medicalEquipmentsByType);
        }
    }

    //#region State Changes
        public void toPreviewMode() {
            for (MedicalEquipmentCounter counter : counters) {
                counter.setEditable(false);
            }
        }

        public void toFocusMode() {
            for (MedicalEquipmentCounter counter : counters) {
                counter.setEditable(true);
            }
        }

        public void removeNode() {
            for (MedicalEquipmentCounter counter : counters) {
                counter.root.getChildren().clear();
                contextGroup.getChildren().remove(counter.root);
            }
        }
    //#endregion
}
