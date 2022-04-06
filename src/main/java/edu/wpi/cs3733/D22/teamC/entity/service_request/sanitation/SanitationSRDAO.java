package edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SanitationSRDAO  extends ServiceRequestDAO<SanitationSR> {
    /**
     * Modify ServiceRequest Sanitation object from query resultSet.
     *
     * @param resultSet ResultSet from query to Service Request DB Table.
     * @return ServiceRequest object.
     */
    protected SanitationSR modifyServiceRequest(ResultSet resultSet, SanitationSR serviceRequest) {
        serviceRequest = super.modifyServiceRequest(resultSet, serviceRequest);
        try {
            // Create generic SR then convert and modify into MedicalEquipmentServiceRequest
            serviceRequest.setRequestID(resultSet.getInt("ID")); // redundant?
            serviceRequest.setSanitationType(SanitationSR.SanitationType.valueOf(resultSet.getString("SANITATIONTYPE")));

            return serviceRequest;

        } catch (SQLException e) {
            System.out.println("Creation of object from database ResultSet failed.");
            e.printStackTrace();

            return null;
        }
    }
}
