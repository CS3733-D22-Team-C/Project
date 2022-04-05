package edu.wpi.cs3733.D22.teamC.entity.service_request.security;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SecuritySRDAO extends ServiceRequestDAO<SecuritySR> {
    /**
     * Modify ServiceRequest object from query resultSet.
     *
     * @param resultSet ResultSet from query to Service Request DB Table.
     * @return ServiceRequest object.
     */
    protected SecuritySR modifyServiceRequest(ResultSet resultSet, SecuritySR serviceRequest) {
        // Modify ServiceRequest attributes
        serviceRequest = super.modifyServiceRequest(resultSet, serviceRequest);
        
        try {
            // Modify SecuritySR attributes
            serviceRequest.setSecurityType(SecuritySR.SecurityType.valueOf(typesafeTrim((resultSet.getString("securityType")))));
            
            return serviceRequest;
            
        } catch (SQLException e) {
            System.out.println("Creation of object from database ResultSet failed.");
            e.printStackTrace();
            
            return null;
        }
    }

}
