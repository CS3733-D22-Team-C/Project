package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.LabSystemSRFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LabSystemSRTest extends DAOTest<LabSystemSR> {

    public LabSystemSRTest() {
        super(new LabSystemSRFactory(), new LabSystemSRDAO());
    }
    
    @Override
    protected void deleteTest() {
        LabSystemSR obj = factory.create();
        String id = dao.insert(obj);
        assertNotNull(id);
        
        ServiceRequestDAO srDAO = new ServiceRequestDAO();
        boolean success = srDAO.delete(obj);
        assertTrue(success);
    }
}
