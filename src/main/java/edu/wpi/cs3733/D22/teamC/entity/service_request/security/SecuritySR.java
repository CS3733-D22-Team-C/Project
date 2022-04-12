package edu.wpi.cs3733.D22.teamC.entity.service_request.security;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;
import java.util.Objects;

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

    public SecuritySR() {}
    
    public SecuritySR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }
    
    public SecurityType getSecurityType() {
        return securityType;
    }

    public void setSecurityType(SecurityType securityType) {
        this.securityType = securityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SecuritySR that = (SecuritySR) o;
        return securityType == that.securityType;
    }
}
