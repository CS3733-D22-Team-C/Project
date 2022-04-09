package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.SanitationSRFactory;

public class SanitationSRTest extends DAOTest<SanitationSR> {

    public SanitationSRTest() {
        super(new SanitationSRFactory(), new SanitationSRDAO());
    }
}
