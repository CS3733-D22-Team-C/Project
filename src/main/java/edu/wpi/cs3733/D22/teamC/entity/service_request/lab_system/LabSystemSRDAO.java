package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

public class LabSystemSRDAO extends DAO<LabSystemSR> {
    @Override
    protected Class<LabSystemSR> classType() {
        return LabSystemSR.class;
    }


//    /**
//     * Modify ServiceRequest object from query resultSet.
//     *
//     * @param resultSet ResultSet from query to Service Request DB Table.
//     * @return ServiceRequest object.
//     */
//    protected LabSystemSR modifyServiceRequest(ResultSet resultSet, LabSystemSR serviceRequest) {
//        // Modify ServiceRequest attributes
//        serviceRequest = super.modifyServiceRequest(resultSet, serviceRequest);
//
//        try {
//            // Modify LabSystemSR attributes
//            serviceRequest.setLabType(LabSystemSR.LabType.valueOf(typesafeTrim((resultSet.getString("labType")))));
//            serviceRequest.setPatientID(typesafeTrim(resultSet.getString("patientID")));
//
//            return serviceRequest;
//
//        } catch (SQLException e) {
//            System.out.println("Creation of object from database ResultSet failed.");
//            e.printStackTrace();
//
//            return null;
//        }
//    }
}
