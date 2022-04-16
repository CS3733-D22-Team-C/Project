package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.laundry.LaundrySRDAO;
import edu.wpi.cs3733.D22.teamC.factory.service_request.LaundrySRFactory;

public class LaundrySRTest extends DAOTest<LaundrySR> {

    public LaundrySRTest() {
        super(new LaundrySRFactory(), new LaundrySRDAO());
    }
}

