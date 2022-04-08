package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalEquipmentSRDAO extends DAO<MedicalEquipmentSR> {
    @Override
    protected Class<MedicalEquipmentSR> classType() {
        return MedicalEquipmentSR.class;
    }
}
