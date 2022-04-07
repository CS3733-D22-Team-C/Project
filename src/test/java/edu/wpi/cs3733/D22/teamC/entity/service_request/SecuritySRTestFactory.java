package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.security.SecuritySR;

public class SecuritySRTestFactory extends ServiceRequestTestFactory<SecuritySR> {
    public SecuritySR create() {

        SecuritySR serviceRequest = new SecuritySR();

        SecuritySR.SecurityType securityType = SecuritySR.SecurityType.Intruder;
        ServiceRequest.RequestType requestType = ServiceRequest.RequestType.Security;

        serviceRequest.setRequestType(requestType);
        serviceRequest.setSecurityType(securityType);
        return super.create(serviceRequest);
    }
}
