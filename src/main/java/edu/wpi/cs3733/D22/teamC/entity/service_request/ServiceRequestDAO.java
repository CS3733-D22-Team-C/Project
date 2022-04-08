package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Wrapper for ServiceRequest handling
 */
public class ServiceRequestDAO extends DAO<ServiceRequest> {
    
    protected Class<ServiceRequest> classType() {
        return ServiceRequest.class;
    }
    
}
