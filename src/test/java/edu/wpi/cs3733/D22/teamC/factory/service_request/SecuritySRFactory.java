package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

public class SecuritySRFactory extends ServiceRequestFactory<SecuritySR>{
    public SecuritySR create() {

        SecuritySR serviceRequest = new SecuritySR();

        SecuritySR.SecurityType securityType = SecuritySR.SecurityType.values()[generator.nextInt(SecuritySR.SecurityType.values().length)];
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;

        serviceRequest.setRequestType(requestType);
        serviceRequest.setSecurityType(securityType);
        return super.create(serviceRequest);
    }
}
