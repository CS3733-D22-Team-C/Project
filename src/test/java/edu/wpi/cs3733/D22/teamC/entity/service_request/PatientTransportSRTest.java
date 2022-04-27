package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.service_request.PatientTransportSRFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientTransportSRTest extends DAOTest<PatientTransportSR> {
    public PatientTransportSRTest() {
        super(new PatientTransportSRFactory(), new PatientTransportSRDAO());
    }
    
    @Override
    protected void deleteTest() {
        PatientTransportSR obj = factory.create();
        String id = dao.insert(obj);
        assertNotNull(id);
        
        ServiceRequestDAO srDAO = new ServiceRequestDAO();
        boolean success = srDAO.delete(obj);
        assertTrue(success);
    }
}
