package edu.wpi.cs3733.D22.teamC.entity.medical_equipment;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

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
        return HibernateManager.filterQuery("from " + classType().getName() + " where LOCATIONID = " + locationID);
    }
}
