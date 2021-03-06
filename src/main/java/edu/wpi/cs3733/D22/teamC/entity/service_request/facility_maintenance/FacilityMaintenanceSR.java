package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;

@Entity
@Table(name = "FACILITY_MAINTENANCE_SR")
public class FacilityMaintenanceSR extends ServiceRequest {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "MaintenanceType")
    protected MaintenanceType maintenanceType;

    public enum MaintenanceType {
        Broken_Pipe,
        Broken_Machine,
        Broken_Light,
        Broken_Medical_Tool,
        Broken_Computer,
        Broken_Toilet,
        Broken_Sink,
        Broken_Door,
        Broken_Phone,
    }
    
    public FacilityMaintenanceSR() {
        super();
    }
    
    public FacilityMaintenanceSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    } 

    public MaintenanceType getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceType maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacilityMaintenanceSR that = (FacilityMaintenanceSR) o;
        return maintenanceType == that.maintenanceType;
    }

}
