package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.service_request.ServiceRequestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ServiceRequestTest extends DAOTest<ServiceRequest> {
    public ServiceRequestTest() {
        super(new ServiceRequestFactory<>(), new ServiceRequestDAO());
    }
    
    @Test
    public void getAllByLocationTest() {
        // Create test SRs and store in DB
        ServiceRequest sr1 = factory.create();
        String sr1LocID = sr1.getLocation();
        ServiceRequest sr2 = factory.create();
        sr2.setLocation(sr1LocID);
        String sr1ID = dao.insert(sr1); 
        String sr2ID = dao.insert(sr2);
        assertNotNull(sr1ID);
        assertNotNull(sr2ID);
        
        ServiceRequestDAO testDAO = new ServiceRequestDAO();
        List<ServiceRequest> returnList = testDAO.getAllSRByLocation(sr1LocID);
        assertEquals(2, returnList.size());
    }
}
