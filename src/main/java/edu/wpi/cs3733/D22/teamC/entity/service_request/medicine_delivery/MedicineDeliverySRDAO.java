package edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicineDeliverySRDAO extends DAO<MedicineDeliverySR> {

    @Override
    protected Class<MedicineDeliverySR> classType() {
        return MedicineDeliverySR.class;
    }
}
