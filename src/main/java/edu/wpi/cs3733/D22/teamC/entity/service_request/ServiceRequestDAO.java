package edu.wpi.cs3733.D22.teamC.entity.service_request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class ServiceRequestDAO<T extends ServiceRequest> {
    public abstract List<T> getAllServiceRequests();
    public abstract T getServiceRequest(int requestID);
    
    public abstract int insertServiceRequest(T serviceRequest);
    public abstract boolean updateServiceRequest(T serviceRequest);
    public abstract boolean deleteServiceRequest(T serviceRequest);

    /**
     * Modify ServiceRequest object from query resultSet.
     *
     * @param resultSet ResultSet from query to Service Request DB Table.
     * @return ServiceRequest object.
     */
    protected T modifyServiceRequest(ResultSet resultSet, T serviceRequest) {
        try {
            serviceRequest.setRequestID(resultSet.getInt("ID"));
            serviceRequest.setCreatorID(typesafeTrim(resultSet.getString("CREATORID")));
            serviceRequest.setAssigneeID(typesafeTrim(resultSet.getString("ASSIGNEEID")));
            serviceRequest.setLocation(typesafeTrim(resultSet.getString("LOCATIONID")));
            serviceRequest.setCreationTimestamp(resultSet.getTimestamp("CREATIONTIMESTAMP"));
            serviceRequest.setStatus(ServiceRequest.Status.valueOf(resultSet.getString("STATUS")));
            serviceRequest.setPriority(ServiceRequest.Priority.valueOf(resultSet.getString("PRIORITY")));
            serviceRequest.setRequestType(ServiceRequest.RequestType.valueOf(resultSet.getString("REQUESTTYPE")));
            serviceRequest.setDescription(typesafeTrim(resultSet.getString("DESCRIPTION")));

            return serviceRequest;

        } catch (SQLException e) {
            System.out.println("Creation of object from SERVICE_REQUEST ResultSet failed.");
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
