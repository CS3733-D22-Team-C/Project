package edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for handling MedicineDeliverySRs
 */
public class MedicineDeliverySRDAO extends DAO<MedicineDeliverySR> {

    @Override
    protected Class<MedicineDeliverySR> classType() {
        return MedicineDeliverySR.class;
    }
}
