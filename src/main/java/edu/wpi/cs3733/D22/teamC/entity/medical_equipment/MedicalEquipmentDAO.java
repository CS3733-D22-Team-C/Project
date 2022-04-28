package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;

import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentDAO extends DAO<MedicalEquipment> {

    
    protected Class<MedicalEquipment> classType() {
        return MedicalEquipment.class;
    }

    /**
     * finds the list of equipment at designated location
     * @param locationID the idenitifer of the location
     * @return the list of equipments at this location
     */
    public List<MedicalEquipment> getEquipmentByLocation(String locationID) {
        return HibernateManager.filterQuery("select q from " + classType().getName() + 
                " q where q.location = '" + locationID + "'");
    }

    /**
     * Returns a list of MedicalEquipment associated with a certain floor.
     * @param floorID The UUID of the floor.
     * @return List of MedicalEquipment objects.
     */
    public List<MedicalEquipment> getEquipmentByFloor(String floorID) {
        List<MedicalEquipment> equipOnFloor = new ArrayList<>();
        List<Location> locationsOnFloor = HibernateManager.filterQuery("select q from " +
                Location.class.getName() + " q where q.floor = '" + floorID + "'");

        assert locationsOnFloor != null;
        for (Location loc : locationsOnFloor) {
            if (loc.getFloor().getID().equals(floorID)) {
                equipOnFloor.addAll(getEquipmentByLocation(loc.getID()));
            }
        }
        return equipOnFloor;
    }

    /**
     * Returns a list of MedicalEquipment of a given type.
     * @param equipmentType  EquipmentType enum.
     * @return List of MedicalEquipment objects.
     */
    public List<MedicalEquipment> getEquipmentByType(MedicalEquipment.EquipmentType equipmentType) {
        return HibernateManager.filterQuery("select q from " + classType().getName() +
                " q where q.equipmentType = '" + equipmentType.toString() + "'");
    }

    /**
     * Returns a list of MedicalEquipment of a given type and floor
     * @param floorID The UUID of the floor.
     * @param equipmentType EquipmentType enum.
     * @return List of MedicalEquipment objects.
     */
    public List<MedicalEquipment> getEquipmentByFloorAndType(String floorID, MedicalEquipment.EquipmentType equipmentType) {
        List<MedicalEquipment> medEquipType = getEquipmentByType(equipmentType);
        List<MedicalEquipment> medEquipFloor = getEquipmentByFloor(floorID);
        medEquipType.retainAll(medEquipFloor);
        return medEquipType;
    }
}
