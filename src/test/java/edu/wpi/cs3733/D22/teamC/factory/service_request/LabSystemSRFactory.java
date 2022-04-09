package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

public class LabSystemSRFactory extends ServiceRequestFactory<LabSystemSR> {
    public LabSystemSR create() {

        LabSystemSR serviceRequest = new LabSystemSR();

        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Lab_System;
        LabSystemSR.LabType labType = LabSystemSR.LabType.values()[generator.nextInt(LabSystemSR.LabType.values().length)];
        String patientID = "JohnCena";

        serviceRequest.setRequestType(requestType);
        serviceRequest.setLabType(labType);
        serviceRequest.setPatientID(patientID);

        return super.create(serviceRequest);
        
    }
}
