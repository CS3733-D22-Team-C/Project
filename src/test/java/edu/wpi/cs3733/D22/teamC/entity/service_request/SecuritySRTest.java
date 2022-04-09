package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.SecuritySRFactory;

public class SecuritySRTest extends DAOTest<SecuritySR> {
    public SecuritySRTest() {
        super(new SecuritySRFactory(), new SecuritySRDAO());
    }
}
