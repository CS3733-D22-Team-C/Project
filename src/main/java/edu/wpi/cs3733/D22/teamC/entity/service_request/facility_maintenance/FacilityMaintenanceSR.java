package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;
@Entity
@Table(name = "Facility_Maintenance_SR")
public class FacilityMaintenanceSR extends ServiceRequest {
    @Enumerated(EnumType.STRING)
    protected MaintenanceType maintenanceType;

    public enum MaintenanceType {
        Cleaning,
        Organizing
    }

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

}
