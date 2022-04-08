package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

public class FacilityMaintenanceSRDAO extends DAO<FacilityMaintenanceSR> {
    @Override
    protected Class<FacilityMaintenanceSR> classType() {
        return FacilityMaintenanceSR.class;
    }
}
