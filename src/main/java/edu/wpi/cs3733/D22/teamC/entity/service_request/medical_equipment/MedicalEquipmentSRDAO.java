package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for handling MedicalEquipmentSRs
 */
public class MedicalEquipmentSRDAO extends DAO<MedicalEquipmentSR> {
    @Override
    protected Class<MedicalEquipmentSR> classType() {
        return MedicalEquipmentSR.class;
    }
}
