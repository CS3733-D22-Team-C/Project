package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SanitationSRDAO  extends DAO<SanitationSR> {

    @Override
    protected Class<SanitationSR> classType() {
        return SanitationSR.class;
    }
}
