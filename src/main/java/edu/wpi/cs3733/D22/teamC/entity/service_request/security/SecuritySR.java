package edu.wpi.cs3733.D22.teamC.entity.service_request.security;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class SecuritySR extends ServiceRequest {
    protected SecurityType securityType;

    public enum SecurityType {
        // TODO: Create Enum Types
        Intruder
    }

    public SecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SecurityType securityType) {
        this.securityType = securityType;
    }
}
