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
public class MedicalEquipmentSRDAOImpl extends ServiceRequestDAOImpl {
    
    /**
     * Getting all the entries in the MEDICAL_EQUIP_SERVICE_REQUEST Table to the DB, and
     * converting them to ServiceRequest objects.
     *
     * @return List of all Medical Equipment Service requests objects converted from queries
     */
    @Override
    public List<ServiceRequest> getAllServiceRequests() {
        try {
            //Execute SELECT query to join parent and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, MEDICAL_EQUIP_SERVICE_REQUEST.* " +
                            "FROM SERVICE_REQUEST INNER JOIN MEDICAL_EQUIP_SERVICE_REQUEST " +
                            "ON SERVICE_REQUEST.REQUESTID = MEDICAL_EQUIP_SERVICE_REQUEST.REQUESTID "
            );
            ResultSet resultSet = statement.executeQuery();
            
            //Return ServiceRequest Objects
            List<ServiceRequest> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                ServiceRequest serviceRequest = createServiceRequest(resultSet);
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;
            
        } catch (SQLException e) {
            System.out.println("Query to MEDICAL_EQUIP_SERVICE_REQUEST failed.");
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
    public MedicalEquipmentSR getServiceRequest(String requestID) {
        try {
            // Execute SELECT Query to join the parent table and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, MEDICAL_EQUIP_SERVICE_REQUEST.* " +
                            "FROM SERVICE_REQUEST INNER JOIN MEDICAL_EQUIP_SERVICE_REQUEST " +
                            "ON SERVICE_REQUEST.REQUESTID = MEDICAL_EQUIP_SERVICE_REQUEST.REQUESTID " +
                            "WHERE SERVICE_REQUEST.REQUESTID = ?"
            );
            statement.setString(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return Location Object
            if (resultSet.next()) return createServiceRequest(resultSet);
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
    public boolean insertServiceRequest(ServiceRequest serviceRequest) {
        try {
            boolean successParent = super.insertServiceRequest(serviceRequest);
            // If the SR can be added successfully to the parent table then we can add it to the child table.
            if (successParent) {
                // Insert the child-unique attributes to the child table.
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "INSERT INTO MEDICAL_EQUIP_SERVICE_REQUEST VALUES(?, ?, ?)"
                );
                statement.setString(1, serviceRequest.getRequestID());
                // Set child-specific attributes by casting
                statement.setString(2, ((MedicalEquipmentSR) serviceRequest).getEquipmentID());
                statement.setString(3, ((MedicalEquipmentSR) serviceRequest).getEquipmentType());
                statement.execute();
                
                return true;
            }
        } catch (SQLException | ClassCastException e) {
            System.out.println("Update to database tables failed");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Update entry in both the Service_Requests and Medical_Equip_Service_Requests table  of DB corresponding to
     * the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(ServiceRequest serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                boolean successParent = super.updateServiceRequest(serviceRequest);
                // If the SR can be updated successfully in the parent table then we can update the child table.
                if (successParent) {
                    // Update the child-unique attributes in the child table.
                    PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                            "UPDATE MEDICAL_EQUIP_SERVICE_REQUEST SET EQUIPID = ?, EQUIPTYPE = ? " +
                                    "WHERE REQUESTID = ?"
                    );
                    statement.setString(1, ((MedicalEquipmentSR) serviceRequest).getEquipmentID());
                    statement.setString(2, ((MedicalEquipmentSR) serviceRequest).getEquipmentType());
                    statement.setString(3, serviceRequest.getRequestID());
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
     * Will also delete entry in MEDICAL_EQUIP_SERVICE_REQUEST.
     *
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(ServiceRequest serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement for base SR table and Medical Equipment SR table
                super.deleteServiceRequest(serviceRequest);
                /*PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "DELETE SERVICE_REQUEST, MEDICAL_EQUIP_SERVICE_REQUEST " +
                                "FROM SERVICE_REQUEST INNER JOIN MEDICAL_EQUIP_SERVICE_REQUEST " +
                                "WHERE SERVICE_REQUEST.REQUESTID = MEDICAL_EQUIP_SERVICE_REQUEST.REQUESTID " +
                                "AND SERVICE_REQUEST.REQUESTID = ?"
                );
                statement.setString(1, serviceRequest.getRequestID());
                statement.execute();*/
                
                return true;
            }
        } catch (Exception e) {
            System.out.println("Delete from database tables failed.");
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Create ServiceRequest object from query resultSet.
     *
     * @param resultSet ResultSet from query to Service Request DB Table.
     * @return ServiceRequest object.
     */
    @Override
    protected MedicalEquipmentSR createServiceRequest(ResultSet resultSet) {
        try {
            // Create generic SR then convert and modify into MedicalEquipmentServiceRequest
            MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR(super.createServiceRequest(resultSet));
            serviceRequest.setRequestID(typesafeTrim(resultSet.getString("REQUESTID"))); // redundant?
            serviceRequest.setEquipmentID(typesafeTrim(resultSet.getString("EQUIPID")));
            serviceRequest.setEquipmentType(typesafeTrim(resultSet.getString("EQUIPTYPE")));
            
            return serviceRequest;
            
        } catch (SQLException | ClassCastException e) {
            System.out.println("Creation of object from database ResultSet failed.");
            e.printStackTrace();
            
            return null;
        }
    }
    
}
