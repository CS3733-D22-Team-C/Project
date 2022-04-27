package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;

public class FacilityMaintenanceSRFactory extends ServiceRequestFactory<FacilityMaintenanceSR> {
    public FacilityMaintenanceSR create() {

        FacilityMaintenanceSR serviceRequest = new FacilityMaintenanceSR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Facility_Maintenance;
        FacilityMaintenanceSR.MaintenanceType maintenanceType = FacilityMaintenanceSR.MaintenanceType.Broken_Pipe.values()[generator.nextInt(FacilityMaintenanceSR.MaintenanceType.values().length)];

        serviceRequest.setRequestType(requestType);
        serviceRequest.setMaintenanceType(maintenanceType);

        return super.create(serviceRequest);

    }
}
