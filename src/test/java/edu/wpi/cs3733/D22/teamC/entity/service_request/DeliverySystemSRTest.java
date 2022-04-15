package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.service_request.DeliverySystemSRFactory;

public class DeliverySystemSRTest extends DAOTest<DeliverySystemSR> {
    
    public DeliverySystemSRTest() {
        super(new DeliverySystemSRFactory(), new DeliverySystemSRDAO());
    }
}
