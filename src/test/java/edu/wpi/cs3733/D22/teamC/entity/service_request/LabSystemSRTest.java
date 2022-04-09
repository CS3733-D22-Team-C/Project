package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.LabSystemSRFactory;

public class LabSystemSRTest extends DAOTest<LabSystemSR> {

    public LabSystemSRTest() {
        super(new LabSystemSRFactory(), new LabSystemSRDAO());
    }
}
