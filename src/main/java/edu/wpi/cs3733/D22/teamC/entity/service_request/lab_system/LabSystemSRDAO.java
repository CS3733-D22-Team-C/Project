package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class LabSystemSRDAO extends ServiceRequestDAO<LabSystemSR> {
    /**
     * Modify ServiceRequest object from query resultSet.
     *
     * @param resultSet ResultSet from query to Service Request DB Table.
     * @return ServiceRequest object.
     */
    protected LabSystemSR modifyServiceRequest(ResultSet resultSet, LabSystemSR serviceRequest) {
        // Modify ServiceRequest attributes
        serviceRequest = super.modifyServiceRequest(resultSet, serviceRequest);

        try {
            // Modify LabSystemSR attributes
            serviceRequest.setLabType(LabSystemSR.LabType.valueOf(typesafeTrim((resultSet.getString("labType")))));
            serviceRequest.setPatientID(typesafeTrim(resultSet.getString("patientID")));
    
            return serviceRequest;
        } catch (SQLException e) {
            System.out.println("Creation of object from database ResultSet failed.");
            e.printStackTrace();
    
            return null;
        }
    }
}
