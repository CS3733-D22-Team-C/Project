package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.ServiceRequestFactory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.TranslatorSRFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TranslatorSRTest extends DAOTest<TranslatorSR> {
    public TranslatorSRTest() {
        super(new TranslatorSRFactory(), new TranslatorSRDAO());
    }
    
    @Override
    protected void deleteTest() {
        TranslatorSR obj = factory.create();
        String id = dao.insert(obj);
        assertNotNull(id);
        
        ServiceRequestDAO srDAO = new ServiceRequestDAO();
        boolean success = srDAO.delete(obj);
        assertTrue(success);
    }
}
