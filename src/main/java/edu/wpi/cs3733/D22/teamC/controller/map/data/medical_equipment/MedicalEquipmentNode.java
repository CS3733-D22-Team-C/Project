package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.FloorMapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.controller.map.data.location.LocationMapNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import javafx.scene.Group;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class MedicalEquipmentNode extends MapNode<MedicalEquipment> {
    // Constants
    private static final Pair<Integer, Integer>[] TOKEN_OFFSETS = new Pair[] {
            new Pair(30, -58),
            new Pair(30, -28),
            new Pair(30, 2),
            new Pair(30, 32)
    };

    // Variables
    List<MedicalEquipment> medicalEquipments;

    // References
    Group contextGroup;
    private MedicalEquipmentToken[] tokens = new MedicalEquipmentToken[MedicalEquipment.EquipmentType.values().length];

    public MedicalEquipmentNode(MedicalEquipmentManager manager, Location location) {
        super(manager, location);

        // Load FXML
        LocationMapNode locationMapNode = (LocationMapNode) manager.getMapViewController().getLocationManager().getByLocation(location);
        contextGroup = locationMapNode.getContextGroup();

        for (int i = 0; i < MedicalEquipment.EquipmentType.values().length; i++) {
            App.View<MedicalEquipmentToken> view = App.instance.loadView(MedicalEquipmentManager.TOKEN_PATHS[i]);

            // Setup Controller
            MedicalEquipmentToken controller = view.getController();

            contextGroup.getChildren().add(view.getNode());
            controller.setPosition(TOKEN_OFFSETS[i].getKey(), TOKEN_OFFSETS[i].getValue());
            controller.setType(MedicalEquipment.EquipmentType.values()[i]);
            controller.setup(this);

            tokens[i] = controller;
        }

        updateValues();
    }


    public void updateValues() {
        MedicalEquipmentDAO dao = new MedicalEquipmentDAO();
        medicalEquipments = dao.getEquipmentByLocation(location.getID());

        for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
            List<MedicalEquipment> medicalEquipmentsByType = medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
            tokens[equipmentType.ordinal()].setMedicalEquipments(medicalEquipmentsByType);
        }
    }

    //#region State Changes
        public void toPreviewMode() {
            for (MedicalEquipmentToken counter : tokens) {
                counter.setEditable(false);

                counter.setVisible((counter.getCount() != 0) && ((FloorMapViewController) manager.getMapViewController()).getMapControlsController().getTokenChecked());
            }
        }

        public void toFocusMode() {
            for (MedicalEquipmentToken counter : tokens) {
                counter.setEditable(true);
                counter.setVisible(((FloorMapViewController) manager.getMapViewController()).getMapControlsController().getTokenChecked());
            }
        }

        public void removeNode() {
            for (MedicalEquipmentToken counter : tokens) {
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
