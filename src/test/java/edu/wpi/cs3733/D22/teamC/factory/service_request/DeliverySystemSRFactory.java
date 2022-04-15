package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system.DeliverySystemSR;

public class DeliverySystemSRFactory extends ServiceRequestFactory<DeliverySystemSR>{
    public DeliverySystemSR create() {
        
        DeliverySystemSR serviceRequest = new DeliverySystemSR();
        
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Delivery_System;
        DeliverySystemSR.DeliveryType deliveryType = DeliverySystemSR.DeliveryType.values()[generator.nextInt(DeliverySystemSR.DeliveryType.values().length)];
        String patientID = "BingBong";
        
        serviceRequest.setRequestType(requestType);
        serviceRequest.setDeliveryType(deliveryType);
        serviceRequest.setPatientID(patientID);
        
        return super.create(serviceRequest);
        
    }
}
