package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.service_request.FacilityMaintenanceSRFactory;

public class FacilityMaintenanceSRTest extends DAOTest<FacilityMaintenanceSR> {

    public FacilityMaintenanceSRTest() {
        super(new FacilityMaintenanceSRFactory(), new FacilityMaintenanceSRDAO());
    }
}
