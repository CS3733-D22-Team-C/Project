package edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for handling DeliverySystemSRs.
 */
public class DeliverySystemSRDAO extends DAO<DeliverySystemSR> {
    
    protected Class<DeliverySystemSR> classType() {
        return DeliverySystemSR.class;
    }
}
