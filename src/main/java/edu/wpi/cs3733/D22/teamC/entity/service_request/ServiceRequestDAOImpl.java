package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl implements ServiceRequestDAO {
    /**
     * Getting all the entries in the ServiceRequests Table to the DB, converting them to ServiceRequest objects
     *
     * @return List of all Service requests objects converted from queries
     */
    @Override
    public List<ServiceRequest> getAllServiceRequests() {
        try {
            //Execute SELECT
            Statement selectStatement = DBManager.getInstance().connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM SERVICE_REQUESTS");
            
            //Return ServiceRequest Objects
            List<ServiceRequest> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                ServiceRequest serviceRequest = createServiceRequest(resultSet);
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;
            
        } catch (SQLException e) {
            System.out.println("Query to SERVICEREQUESTS failed.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Get entry in the ServiceRequest table of the database with a given requestID and convert it into a ServiceRequest
     * object.
     *
     * @param requestID The requestID of the service request.
     * @return ServiceRequest object.
     */
    @Override
    public ServiceRequest getServiceRequest(String requestID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM SERVICE_REQUESTS WHERE REQUESTID = ?"
            );
            statement.setString(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return Location Object
            if (resultSet.next()) return createServiceRequest(resultSet);
        } catch (SQLException e) {
            System.out.println("Query to SERVICE_REQUESTS table failed.");
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Insert entry into ServiceRequest Table of the DB, corresponding to the given ServiceRequest object.
     *
     * @param serviceRequest The ServiceRequest to be inserted into the DB via a corresponding entry.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean insertServiceRequest(ServiceRequest serviceRequest) {
        try {
            // See if there is an entry of the same requestID that already exists
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB == null) {
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "INSERT INTO SERVICE_REQUESTS VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?)"
                );
                statement.setString(1, serviceRequest.getRequestID());
                statement.setString(2, serviceRequest.getCreatorID());
                statement.setString(3, serviceRequest.getAssigneeID());
                statement.setString(4, serviceRequest.getLocation());
                statement.setTimestamp(5, serviceRequest.getCreationTimestamp());
                statement.setString(6, serviceRequest.getStatus());
                statement.setString(7, serviceRequest.getPriority());
                statement.setString(8, serviceRequest.getRequestType());
                statement.setString(9, serviceRequest.getDescription());
                statement.execute();
                
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Update to SERVICE_REQUESTS failed");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Update entry in REQUEST Table of DB corresponding to the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(ServiceRequest serviceRequest) {
        try {
            // check if the entry of the same requestID exists
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute UPDATE statement
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "UPDATE SERVICE_REQUESTS SET CREATORID = ?, ASSIGNEEID = ?, LOCATIONID = ?, " +
                                "CREATIONTIMESTAMP = ?, STATUS = ?, PRIORITY = ?, REQUESTTYPE = ?, DESCRIPTION = ? " +
                                "WHERE REQUESTID = ?"
                );
                statement.setString(1, serviceRequest.getCreatorID());
                statement.setString(2, serviceRequest.getAssigneeID());
                statement.setString(3, serviceRequest.getLocation());
                statement.setTimestamp(4, serviceRequest.getCreationTimestamp());
                statement.setString(5, serviceRequest.getStatus());
                statement.setString(6, serviceRequest.getPriority());
                statement.setString(7, serviceRequest.getRequestType());
                statement.setString(8, serviceRequest.getDescription());
                statement.setString(9, serviceRequest.getRequestID());
                statement.execute();
                
                return true;
            }
            
        } catch (SQLException e) {
            System.out.println("Update to REQUEST failed");
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Delete entry in SERVICE Table of DB corresponding to the given ServiceRequest object.
     *
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(ServiceRequest serviceRequest) {
        try {
            // Check if entry of same nodeID exists
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "DELETE FROM SERVICE_REQUESTS WHERE REQUESTID = ?"
                );
                statement.setString(1, serviceRequest.getRequestID());
                statement.execute();
                
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Delete from SERVICE_REQUESTS table failed.");
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
    public ServiceRequest createServiceRequest(ResultSet resultSet) {
        try {
            ServiceRequest serviceRequest = new ServiceRequest();
            
            serviceRequest.setRequestID(typesafeTrim(resultSet.getString("REQUESTID")));
            serviceRequest.setCreatorID(typesafeTrim(resultSet.getString("CREATORID")));
            serviceRequest.setAssigneeID(typesafeTrim(resultSet.getString("ASSIGNEEID")));
            serviceRequest.setLocation(typesafeTrim(resultSet.getString("LOCATIONID")));
            serviceRequest.setCreationTimestamp(resultSet.getTimestamp("CREATIONTIMESTAMP"));
            serviceRequest.setStatus(typesafeTrim(resultSet.getString("STATUS")));
            serviceRequest.setPriority(typesafeTrim(resultSet.getString("PRIORITY")));
            serviceRequest.setRequestType(typesafeTrim(resultSet.getString("REQUESTTYPE")));
            serviceRequest.setDescription(typesafeTrim(resultSet.getString("DESCRIPTION")));
            
            return serviceRequest;
            
        } catch (SQLException e) {
            System.out.println("Creation of object from SERVICE_REQUESTS ResultSet failed.");
            e.printStackTrace();
            
            return null;
        }
    }
    
    /**
     * Trim str if not null.
     *
     * @param str The String to trim.
     * @return The trimmed str.
     */
    protected String typesafeTrim(String str) {
        if (str == null) return null;
        else return str.trim();
    }
}
