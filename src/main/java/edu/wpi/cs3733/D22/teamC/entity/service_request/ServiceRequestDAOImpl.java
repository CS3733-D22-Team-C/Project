package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.DBManager;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequestDAOImpl extends ServiceRequestDAO<ServiceRequest> {
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
            ResultSet resultSet = selectStatement.executeQuery("SELECT * FROM SERVICE_REQUEST");
            
            //Return ServiceRequest Objects
            List<ServiceRequest> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                ServiceRequest serviceRequest = modifyServiceRequest(resultSet, new ServiceRequest());
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
    public ServiceRequest getServiceRequest(int requestID) {
        try {
            // Execute SELECT Query
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT * FROM SERVICE_REQUEST WHERE REQUESTID = ?"
            );
            statement.setInt(1, requestID);
            ResultSet resultSet = statement.executeQuery();
            
            // Return Location Object
            if (resultSet.next()) return modifyServiceRequest(resultSet, new ServiceRequest());
        } catch (SQLException e) {
            System.out.println("Query to SERVICE_REQUEST table failed.");
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
                PreparedStatement statement = (serviceRequest.getRequestID() == 0)
                        ? DBManager.getInstance().connection.prepareStatement("INSERT INTO SERVICE_REQUEST VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                        : DBManager.getInstance().connection.prepareStatement("INSERT INTO SERVICE_REQUEST VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                int index = 1;
                if(serviceRequest.getRequestID() != 0){
                    statement.setString(index, (serviceRequest.getRequestID()!=0) ? String.valueOf(serviceRequest.getRequestID()) : "DEFAULT");
                    index++;
                }
                statement.setString(index, serviceRequest.getCreatorID()); index++;
                statement.setString(index, serviceRequest.getAssigneeID()); index++;
                statement.setString(index, serviceRequest.getLocation()); index++;
                statement.setTimestamp(index, serviceRequest.getCreationTimestamp()); index++;
                statement.setString(index, serviceRequest.getStatus().toString()); index++;
                statement.setString(index, serviceRequest.getPriority().toString()); index++;
                statement.setString(index, serviceRequest.getRequestType().toString()); index++;
                statement.setString(index, serviceRequest.getDescription());
                statement.execute();

                ResultSet resultSetID = statement.getGeneratedKeys();
                if(resultSetID.next()) return resultSetID.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Update to SERVICE_REQUEST failed");
            e.printStackTrace();
        }
        return -1;
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
                        "UPDATE SERVICE_REQUEST SET CREATORID = ?, ASSIGNEEID = ?, LOCATIONID = ?, " +
                                "CREATIONTIMESTAMP = ?, STATUS = ?, PRIORITY = ?, REQUESTTYPE = ?, DESCRIPTION = ? " +
                                "WHERE REQUESTID = ?"
                );
                statement.setString(1, serviceRequest.getCreatorID());
                statement.setString(2, serviceRequest.getAssigneeID());
                statement.setString(3, serviceRequest.getLocation());
                statement.setTimestamp(4, serviceRequest.getCreationTimestamp());
                statement.setString(5, serviceRequest.getStatus().toString());
                statement.setString(6, serviceRequest.getPriority().toString());
                statement.setString(7, serviceRequest.getRequestType().toString());
                statement.setString(8, serviceRequest.getDescription());
                statement.setInt(9, serviceRequest.getRequestID());
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
                        "DELETE FROM SERVICE_REQUEST WHERE REQUESTID = ?"
                );
                statement.setInt(1, serviceRequest.getRequestID());
                statement.execute();
                
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Delete from SERVICE_REQUEST table failed.");
            e.printStackTrace();
        }
        
        return false;
    }
    

    

}
