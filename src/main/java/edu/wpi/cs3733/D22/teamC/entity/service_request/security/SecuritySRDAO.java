package edu.wpi.cs3733.D22.teamC.entity.service_request.security;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

/**
 * Wrapper class for SecuritySR handling.
 */
public class SecuritySRDAO extends DAO<SecuritySR> {
    
    protected Class<SecuritySR> classType() {
        return SecuritySR.class;
    }
    
}
