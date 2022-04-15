package edu.wpi.cs3733.D22.teamC.controller.location.map;

import edu.wpi.cs3733.D22.teamC.controller.location.map.map_view.MedicalEquipmentCounterNode;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import javafx.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the count of medical equipment for the Map Controller.
 */
public class MedicalEquipmentManager {
    // Constants
    private final static Pair<Integer, Integer>[] MEDICAL_EQUIPMENT_COUNTER_NODE_OFFSETS = new Pair[] {
            new Pair<>(0, 10),
            new Pair<>(100, 10),
            new Pair<>(200, 10),
            new Pair<>(300, 10)
    };

    // Variables
    private List<MedicalEquipment> medicalEquipments;
    private final List<MedicalEquipment>[] equipmentsByType = new List[MedicalEquipment.EquipmentType.values().length];
    private final boolean[] disabledIncreases = new boolean[MedicalEquipment.EquipmentType.values().length];
    private MedicalEquipmentCounterNode[] editOverlays = new MedicalEquipmentCounterNode[MedicalEquipment.EquipmentType.values().length];

    // References
    private BaseMapViewController parentController;

    public MedicalEquipmentManager(BaseMapViewController baseMapViewController) {
        this.parentController = baseMapViewController;

        setupData();
    }

    public void setupData() {
        medicalEquipments = new MedicalEquipmentDAO().getAll();

        for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
            List<MedicalEquipment> equipmentByType = medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType().equals(equipmentType)).collect(Collectors.toList());
            equipmentsByType[equipmentType.ordinal()] = equipmentByType;
        }
    }

    public List<MedicalEquipment> getPerLocation(Location location) {
        return medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getLocationID().equals(location.getID())).collect(Collectors.toList());
    }

    public List<MedicalEquipment> getPerLocationPerType(Location location, MedicalEquipment.EquipmentType equipmentType) {
        List<MedicalEquipment> equipmentByType = equipmentsByType[equipmentType.ordinal()];

        return equipmentByType.stream().filter(medicalEquipment -> medicalEquipment.getLocationID().equals(location.getID())).collect(Collectors.toList());
    }

    /**
     * Store a newly released Medical Equipment, updating increase counters if relevant.
     * @param medicalEquipment
     */
    public void releaseMedicalEquipment(MedicalEquipment medicalEquipment) {
        medicalEquipment.setLocationID("");

        MedicalEquipment.EquipmentType equipmentType = medicalEquipment.getEquipmentType();

        List<MedicalEquipment> releasedEquipmentByType = editOverlays[equipmentType.ordinal()].getMedicalEquipments();
        releasedEquipmentByType.add(medicalEquipment);
        editOverlays[equipmentType.ordinal()].resetCounter();

        disabledIncreases[equipmentType.ordinal()] = false;
    }

    public MedicalEquipment reclaimMedicalEquipment(MedicalEquipment.EquipmentType equipmentType, Location location) {
        MedicalEquipment medicalEquipment = null;

        List<MedicalEquipment> releasedEquipmentByType = editOverlays[equipmentType.ordinal()].getMedicalEquipments();
        if (releasedEquipmentByType.size() > 0) {
            medicalEquipment = releasedEquipmentByType.get(0);
            medicalEquipment.setLocationID(location.getID());

            releasedEquipmentByType.remove(medicalEquipment);
            editOverlays[equipmentType.ordinal()].resetCounter();

            disabledIncreases[equipmentType.ordinal()] = (releasedEquipmentByType.size() == 0);
        }

        return medicalEquipment;
    }


    //#region Edit Mode
        public void setEditMode(boolean editMode) {
            if (editMode) initializeEditCounters();
        }

        public void initializeEditCounters() {
            for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
                MedicalEquipmentCounterNode medicalEquipmentCounterNode = MedicalEquipmentCounterNode.loadNewMedicalEquipmentCounterNode(equipmentType);
                medicalEquipmentCounterNode.setEditable(false);
                medicalEquipmentCounterNode.setVisible(true);
                medicalEquipmentCounterNode.group.setScaleX(1.5);
                medicalEquipmentCounterNode.group.setScaleY(1.5);
                medicalEquipmentCounterNode.render(parentController.mapController.rightOverlay, MEDICAL_EQUIPMENT_COUNTER_NODE_OFFSETS[equipmentType.ordinal()]);

                List<MedicalEquipment> releasedEquipmentByType = equipmentsByType[equipmentType.ordinal()].stream().filter(medicalEquipment -> medicalEquipment.getLocationID().equals("")).collect(Collectors.toList());
                medicalEquipmentCounterNode.setMedicalEquipments(releasedEquipmentByType);
                disabledIncreases[equipmentType.ordinal()] = (releasedEquipmentByType.size() == 0);

                editOverlays[equipmentType.ordinal()] = medicalEquipmentCounterNode;
            }
        }


        public boolean getDisabledIncrease(MedicalEquipment.EquipmentType equipmentType) {
            return disabledIncreases[equipmentType.ordinal()];
        }
    //#endregion

    //#region Stashed Changes
        /**
         * Save medical equipment with changes to DB.
         */
        public void saveChanges() {
            MedicalEquipmentDAO medicalEquipmentDAO = new MedicalEquipmentDAO();
            medicalEquipmentDAO.deleteAllFromTable();

            medicalEquipments.forEach(medicalEquipmentDAO::insert);
        }

        /**
         * Clear locations of changes from DB.
         */
        public void clearChanges() {
            setupData();
        }
    //#endregion
}
