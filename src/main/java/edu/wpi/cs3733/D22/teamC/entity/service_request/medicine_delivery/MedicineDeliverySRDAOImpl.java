package edu.wpi.cs3733.D22.teamC.entity.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDeliverySRDAOImpl extends MedicineDeliverySRDAO {
    
    /**
     * Getting all the entries in the MEDICINE_DELIVERY_SR Table to the DB, and
     * converting them to MedicineDeliverySR objects.
     *
     * @return List of all MedicineDeliverySR objects converted from queries
     */
    @Override
    public List<MedicineDeliverySR> getAllServiceRequests() {
        try {
            // Execute SELECT query to join parent and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, MEDICINE_DELIVERY_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN MEDICINE_DELIVERY_SR " +
                            "ON SERVICE_REQUEST.ID = MEDICINE_DELIVERY_SR.ID "
            );
            ResultSet resultSet = statement.executeQuery();
            
            // Return ServiceRequest Objects
            List<MedicineDeliverySR> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                MedicineDeliverySR serviceRequest = modifyServiceRequest(resultSet, new MedicineDeliverySR());
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;
            
        } catch (SQLException e) {
            System.out.println("Query to MEDICINE_DELIVERY_SR failed.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get entry in the ServiceRequest and MEDICINE_DELIVERY_SR table of the database with a given requestID
     * and convert it into a MedicineDeliverySR object.
     *
     * @param requestID The requestID of the service request.
     * @return MedicineDeliverySR object.
     */
    @Override
    public MedicineDeliverySR getServiceRequest(int requestID) {
        try {
            // Execute SELECT Query to join the parent table and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, MEDICINE_DELIVERY_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN MEDICINE_DELIVERY_SR " +
                            "ON SERVICE_REQUEST.ID = MEDICINE_DELIVERY_SR.ID " +
                            "WHERE SERVICE_REQUEST.ID = ?"
            );
            statement.setInt(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return MedicineDeliverySR Object
            if (resultSet.next()) return modifyServiceRequest(resultSet, new MedicineDeliverySR());
        } catch (SQLException e) {
            System.out.println("Query to database tables failed.");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Insert entry into ServiceRequest Table of the DB, corresponding to the given ServiceRequest object.
     * A given MedicineDeliverySR specifically to the dedicated table.
     *
     * @param serviceRequest The ServiceRequest to be inserted into the DB via a corresponding entry.
     * @return If successful return true, else return false.
     */
    @Override
    public int insertServiceRequest(MedicineDeliverySR serviceRequest) {
        try {
            ServiceRequestDAOImpl sRDAO = new ServiceRequestDAOImpl();
            int requestID = sRDAO.insertServiceRequest(serviceRequest);
            // If the SR can be added successfully to the parent table then we can add it to the child table.
            if (requestID != -1) {
                // Insert the child-unique attributes to the child table.
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "INSERT INTO MEDICINE_DELIVERY_SR VALUES(?, ?, ?, ?)"
                );
                statement.setInt(1, requestID);
                // Set child-specific attributes by casting
                statement.setString(2, serviceRequest.getMedicine());
                statement.setString(3, serviceRequest.getDosage());
                statement.setString(4, serviceRequest.getPatientID());
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
     * Update entry in both the Service_Requests and MEDICINE_DELIVERY_SR table of DB corresponding to
     * the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(MedicineDeliverySR serviceRequest) {
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
                            "UPDATE MEDICINE_DELIVERY_SR SET Medicine = ?, Dosage = ?, PatientID = ? " +
                                    "WHERE ID = ?"
                    );
                    statement.setString(1, serviceRequest.getMedicine());
                    statement.setString(2, serviceRequest.getDosage());
                    statement.setString(3, serviceRequest.getPatientID());
                    statement.setInt(4, serviceRequest.getRequestID());
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
     * Will also delete entry in MEDICINE_DELIVERY_SR.
     *
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(MedicineDeliverySR serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement for base SR table and MEDICINE_DELIVERY_SR table
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
