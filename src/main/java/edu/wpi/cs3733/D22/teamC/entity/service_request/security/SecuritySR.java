package edu.wpi.cs3733.D22.teamC.entity.service_request.security;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;

@Entity
@Table(name = "SECURITY_SR")
public class SecuritySR extends ServiceRequest {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "SecurityType")
    protected SecurityType securityType;

    public enum SecurityType {
        Intruder,
        LOCKDOWN,
        TORNADO
    }

    public SecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SecurityType securityType) {
        this.securityType = securityType;
    }
}
