package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class FacilityMaintenanceSR extends ServiceRequest {
    protected MaintenanceType maintenanceType;

    public enum MaintenanceType {
        //TODO: Create more ENUM types
        Cleaning,
        Organizing
    }

    //public FacilityMaintainanceSR(){}

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

}
