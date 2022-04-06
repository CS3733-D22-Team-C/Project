package edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class MedicineDeliverySRDAO extends ServiceRequestDAO<MedicineDeliverySR> {
    
    /**
     * Modify ServiceRequest object from query resultSet.
     *
     * @param resultSet ResultSet from query to Service Request DB Table.
     * @return ServiceRequest object.
     */
    protected MedicineDeliverySR modifyServiceRequest(ResultSet resultSet, MedicineDeliverySR serviceRequest) {
        // Modify ServiceRequest attributes
        serviceRequest = super.modifyServiceRequest(resultSet, serviceRequest);
        
        try {
            // Modify LabSystemSR attributes
            serviceRequest.setMedicine(typesafeTrim(resultSet.getString("Medicine")));
            serviceRequest.setDosage(typesafeTrim(resultSet.getString("Dosage")));
            serviceRequest.setPatientID(typesafeTrim(resultSet.getString("patientID")));
            
            return serviceRequest;
            
        } catch (SQLException e) {
            System.out.println("Creation of object from database ResultSet failed.");
            e.printStackTrace();
            
            return null;
        }
    }

}
