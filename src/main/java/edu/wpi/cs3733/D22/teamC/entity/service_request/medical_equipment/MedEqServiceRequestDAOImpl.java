package edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedEqServiceRequestDAOImpl extends ServiceRequestDAOImpl {
    
    /**
     * Getting all the entries in the ServiceRequests Table to the DB, converting them to ServiceRequest objects
     *
     * @return List of all Medical Equipment Service requests objects converted from queries
     */
    @Override
    public List<ServiceRequest> getAllServiceRequests() {
        try {
            //Execute SELECT
            Statement selectStatement = DBManager.getInstance().connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM MEDEQUIPSERVICEREQUESTS");
            
            //Return ServiceRequest Objects
            List<ServiceRequest> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                ServiceRequest serviceRequest = createServiceRequest(resultSet);
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;
            
        } catch (SQLException e) {
            System.out.println("Query to MEDEQUIPSERVICEREQUESTS failed.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get entry in the ServiceRequest table of the database with a given requestID and convert it into a 
     * MedicalEquipmentServiceRequest object.
     *
     * @param requestID The requestID of the service request.
     * @return MedicalEquipmentServiceRequest object.
     */
    @Override
    public MedicalEquipmentServiceRequest getServiceRequest(String requestID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM MEDEQUIPSERVICEREQUESTS WHERE SERVICEREQUESTID = ?"
            );
            statement.setString(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return Location Object
            if (resultSet.next()) return createServiceRequest(resultSet);
        } catch (SQLException e) {
            System.out.println("Query to SERVICEREQUESTS table failed.");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Update entry in REQUEST Table of DB corresponding to the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(ServiceRequest serviceRequest) {
        // check if the entry of the same requestID exists
        ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
        try {
            if (serviceRequestInDB != null) {
                //Excute UPDATE statement
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "UPDATE MEDEQUIPSERVICEREQUESTS SET CREATORID = ?, ASSIGNEEID = ?, LOCATIONID = ?, WHENREQUESTED = ?, " +
                                "REQUESTSTATUS = ?, PRIORITY = ?, SERVICEREQUESTTYPE = ?, REQUESTDESCRIPTION = ?, " + 
                                "EQUIPMENTTYPE = ?, EQUIPMENTID = ? WHERE REQUESTID = ?"
                );
                statement.setString(1, serviceRequest.getCreatorID());
                statement.setString(2, serviceRequest.getAssigneeID());
                statement.setString(3, serviceRequest.getLocation());
                statement.setTimestamp(4, serviceRequest.getCreationTimestamp());
                statement.setString(5, serviceRequest.getStatus());
                statement.setString(6, serviceRequest.getPriority());
                statement.setString(7, serviceRequest.getRequestType());
                statement.setString(8, serviceRequest.getDescription());
                statement.setString(9, serviceRequest.getEquipmentType());
                statement.setString(10, serviceRequest.getEquipmentID());
                statement.setString(11, serviceRequest.getRequestID());
                
                
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println("Update to MEDEQUIPSERVICEREQUESTS failed");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Delete entry in SERVICEREQUESTS Table of DB corresponding to the given ServiceRequest object.
     * Will also delete entry in MEDEQUIPSERVICEREQUESTS
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(ServiceRequest serviceRequest) {
        try {
            // Check if entry of same requestID exists
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "DELETE FROM MEDEQUIPSERVICEREQUESTS WHERE SERVICEREQUESTID = ?"
                );
                statement.setString(1, serviceRequest.getRequestID());
                statement.execute();
                
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Delete from SERVICEREQUESTS table failed.");
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
    public MedicalEquipmentServiceRequest createServiceRequest(ResultSet resultSet) {
        try {
            MedicalEquipmentServiceRequest serviceRequest = new MedicalEquipmentServiceRequest();
            
            serviceRequest.setRequestID(typesafeTrim(resultSet.getString("REQUESTID")));
            serviceRequest.setCreatorID(typesafeTrim(resultSet.getString("CREATORID")));
            serviceRequest.setAssigneeID(typesafeTrim(resultSet.getString("ASSIGNEEID")));
            serviceRequest.setLocation(typesafeTrim(resultSet.getString("LOCATIONID")));
            serviceRequest.setCreationTimestamp(resultSet.getTimestamp("WHENREQUESTED"));
            serviceRequest.setStatus(typesafeTrim(resultSet.getString("REQUESTSTATUS")));
            serviceRequest.setPriority(typesafeTrim(resultSet.getString("PRIORITY")));
            serviceRequest.setRequestType(typesafeTrim(resultSet.getString("SERVICEREQUESTTYPE")));
            serviceRequest.setDescription(typesafeTrim(resultSet.getString("REQUESTDESCRIPTION")));
            serviceRequest.setEquipmentType(typesafeTrim(resultSet.getString("EQUIPMENTTYPE")));
            serviceRequest.setEquipmentID(typesafeTrim(resultSet.getString("EQUIPMENTID")));
            
            return serviceRequest;
            
        } catch (SQLException e) {
            System.out.println("Creation of object from MEDEQUIPSERVICEREQUESTS ResultSet failed.");
            e.printStackTrace();
            
            return null;
        }
    }
    
}
