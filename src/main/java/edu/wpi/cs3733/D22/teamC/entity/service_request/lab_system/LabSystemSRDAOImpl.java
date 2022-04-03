package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabSystemSRDAOImpl extends LabSystemSRDAO {
    /**
     * Getting all the entries in the LAB_SYSTEM_SR Table to the DB, and
     * converting them to ServiceRequest objects.
     *
     * @return List of all Lab System Service requests objects converted from queries
     */
    @Override
    public List<LabSystemSR> getAllServiceRequests() {
        try {
            //Execute SELECT query to join parent and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, LAB_SYSTEM_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN LAB_SYSTEM_SR " +
                            "ON SERVICE_REQUEST.ID = LAB_SYSTEM_SR.ID "
            );
            ResultSet resultSet = statement.executeQuery();
            
            //Return ServiceRequest Objects
            List<LabSystemSR> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                LabSystemSR serviceRequest = modifyServiceRequest(resultSet, new LabSystemSR());
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;
            
        } catch (SQLException e) {
            System.out.println("Query to LAB_SYSTEM_SR failed.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get entry in the ServiceRequest and LAB_SYSTEM_SR table of the database with a given requestID
     * and convert it into a LabSystemSR object.
     *
     * @param requestID The requestID of the service request.
     * @return LabSystemSR object.
     */
    @Override
    public LabSystemSR getServiceRequest(int requestID) {
        try {
            // Execute SELECT Query to join the parent table and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, LAB_SYSTEM_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN LAB_SYSTEM_SR " +
                            "ON SERVICE_REQUEST.ID = LAB_SYSTEM_SR.ID " +
                            "WHERE SERVICE_REQUEST.ID = ?"
            );
            statement.setInt(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return Location Object
            if (resultSet.next()) return modifyServiceRequest(resultSet, new LabSystemSR());
        } catch (SQLException e) {
            System.out.println("Query to database tables failed.");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Insert entry into ServiceRequest Table of the DB, corresponding to the given ServiceRequest object.
     * A given LabSystemSR specifically to the dedicated table.
     *
     * @param serviceRequest The ServiceRequest to be inserted into the DB via a corresponding entry.
     * @return If successful return true, else return false.
     */
    @Override
    public int insertServiceRequest(LabSystemSR serviceRequest) {
        try {
            ServiceRequestDAOImpl sRDAO = new ServiceRequestDAOImpl();
            int requestID = sRDAO.insertServiceRequest(serviceRequest);
            // If the SR can be added successfully to the parent table then we can add it to the child table.
            if (requestID != -1) {
                // Insert the child-unique attributes to the child table.
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "INSERT INTO LAB_SYSTEM_SR VALUES(?, ?, ?)"
                );
                statement.setInt(1, requestID);
                // Set child-specific attributes by casting
                statement.setString(2, serviceRequest.getLabType().toString());
                statement.setString(3, serviceRequest.getPatientID());
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
     * Update entry in both the Service_Requests and LAB_SYSTEM_SR table of DB corresponding to
     * the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(LabSystemSR serviceRequest) {
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
                            "UPDATE LAB_SYSTEM_SR SET labType = ?, patientID = ? " +
                                    "WHERE ID = ?"
                    );
                    statement.setString(1, serviceRequest.getLabType().toString());
                    statement.setString(2, serviceRequest.getPatientID());
                    statement.setInt(3, serviceRequest.getRequestID());
                    statement.execute();
                    
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Update to database tables failed.");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Delete entry in SERVICE_REQUEST Table of DB corresponding to the given ServiceRequest object.
     * Will also delete entry in LAB_SYSTEM_SR.
     *
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(LabSystemSR serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement for base SR table and LAB_SYSTEM_SR table
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
