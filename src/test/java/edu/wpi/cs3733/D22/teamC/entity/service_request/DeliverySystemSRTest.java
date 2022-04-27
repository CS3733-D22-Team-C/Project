package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.service_request.DeliverySystemSRFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeliverySystemSRTest extends DAOTest<DeliverySystemSR> {
    
    public DeliverySystemSRTest() {
        super(new DeliverySystemSRFactory(), new DeliverySystemSRDAO());
    }
    
    @Override
    protected void deleteTest() {
        DeliverySystemSR obj = factory.create();
        String id = dao.insert(obj);
        assertNotNull(id);
    
        ServiceRequestDAO srDAO = new ServiceRequestDAO();
        boolean success = srDAO.delete(obj);
        assertTrue(success);
    }
}
