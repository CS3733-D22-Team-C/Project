package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Inherited ServiceRequestDAOImpl class specifically tailored to handle MedicalEquipmentServiceRequests.
 */
public class MedicalEquipmentSRDAOImpl extends MedicalEquipmentSRDAO {
    
    /**
     * Getting all the entries in the MEDICAL_EQUIPMENT_SR Table to the DB, and
     * converting them to ServiceRequest objects.
     *
     * @return List of all Medical Equipment Service requests objects converted from queries
     */
    @Override
    public List<MedicalEquipmentSR> getAllServiceRequests() {
        try {
            //Execute SELECT query to join parent and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, MEDICAL_EQUIPMENT_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN MEDICAL_EQUIPMENT_SR " +
                            "ON SERVICE_REQUEST.ID = MEDICAL_EQUIPMENT_SR.ID "
            );
            ResultSet resultSet = statement.executeQuery();
            
            //Return ServiceRequest Objects
            List<MedicalEquipmentSR> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                MedicalEquipmentSR serviceRequest = modifyServiceRequest(resultSet, new MedicalEquipmentSR());
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;
            
        } catch (SQLException e) {
            System.out.println("Query to MEDICAL_EQUIPMENT_SR failed.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get entry in the ServiceRequest and MedEqServiceRequest table of the database with a given requestID
     * and convert it into a MedicalEquipmentServiceRequest object.
     *
     * @param requestID The requestID of the service request.
     * @return MedicalEquipmentServiceRequest object.
     */
    @Override
    public MedicalEquipmentSR getServiceRequest(int requestID) {
        try {
            // Execute SELECT Query to join the parent table and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, MEDICAL_EQUIPMENT_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN MEDICAL_EQUIPMENT_SR " +
                            "ON SERVICE_REQUEST.ID = MEDICAL_EQUIPMENT_SR.ID " +
                            "WHERE SERVICE_REQUEST.ID = ?"
            );
            statement.setInt(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return Location Object
            if (resultSet.next()) return modifyServiceRequest(resultSet, new MedicalEquipmentSR());
        } catch (SQLException e) {
            System.out.println("Query to database tables failed.");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Insert entry into ServiceRequest Table of the DB, corresponding to the given ServiceRequest object.
     * A given MedicalEquipmentServiceRequest specifically to the dedicated table.
     *
     * @param serviceRequest The ServiceRequest to be inserted into the DB via a corresponding entry.
     * @return If successful return true, else return false.
     */
    @Override
    public int insertServiceRequest(MedicalEquipmentSR serviceRequest) {
        try {
            ServiceRequestDAOImpl sRDAO = new ServiceRequestDAOImpl();
            int requestID = sRDAO.insertServiceRequest(serviceRequest);
            // If the SR can be added successfully to the parent table then we can add it to the child table.
            if (requestID != -1) {
                // Insert the child-unique attributes to the child table.
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "INSERT INTO MEDICAL_EQUIPMENT_SR VALUES(?, ?)"
                );
                statement.setInt(1, requestID);
                // Set child-specific attributes by casting
                statement.setString(2, serviceRequest.getEquipmentID().toString());
                statement.execute();
                
                return requestID;
            }
        } catch (SQLException e) {
            System.out.println("Update to database tables failed");
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * Update entry in both the Service_Requests and MEDICAL_EQUIPMENT_SR table  of DB corresponding to
     * the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(MedicalEquipmentSR serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                ServiceRequestDAOImpl sRDAO = new ServiceRequestDAOImpl();
                boolean successParent = sRDAO.updateServiceRequest(serviceRequest);
                // If the SR can be updated successfully in the parent table then we can update the child table.
                if (successParent) {
                    // Update the child-unique attributes in the child table.
                    PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                            "UPDATE MEDICAL_EQUIPMENT_SR SET EQUIPID = ?, EQUIPTYPE = ? " +
                                    "WHERE ID = ?"
                    );
                    statement.setString(1, ((MedicalEquipmentSR) serviceRequest).getEquipmentID());
                    statement.setString(2, serviceRequest.getEquipmentType().toString());
                    statement.setInt(3, serviceRequest.getRequestID());
                    statement.execute();
                    
                    return true;
                }
            }
            
        } catch (SQLException | ClassCastException e) {
            System.out.println("Update to database tables failed.");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Delete entry in SERVICE_REQUEST Table of DB corresponding to the given ServiceRequest object.
     * Will also delete entry in MEDICAL_EQUIPMENT_SR.
     *
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(MedicalEquipmentSR serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement for base SR table and Medical Equipment SR table
                ServiceRequestDAOImpl sRDAO = new ServiceRequestDAOImpl();
                sRDAO.deleteServiceRequest(serviceRequest);
                
                return true;
            }
        } catch (Exception e) {
            System.out.println("Delete from database tables failed.");
            e.printStackTrace();
        }
        
        return false;
    }
    
}
