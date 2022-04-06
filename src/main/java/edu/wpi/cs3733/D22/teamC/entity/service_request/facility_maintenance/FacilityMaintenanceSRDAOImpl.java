package edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.DBManager;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceSR;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilityMaintenanceSRDAOImpl extends FacilityMaintenanceSRDAO{
    /**
     * Getting all the entries in the FACILITY_MAINTENANCE_SR Table to the DB, and
     * converting them to FacilityMaintenanceSR objects.
     *
     * @return List of all FacilityMaintenanceSR objects converted from queries
     */
    @Override
    public List<FacilityMaintenanceSR> getAllServiceRequests() {
        try {
            // Execute SELECT query to join parent and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, FACILITY_MAINTENANCE_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN FACILITY_MAINTENANCE_SR " +
                            "ON SERVICE_REQUEST.ID = FACILITY_MAINTENANCE_SR.ID "
            );
            ResultSet resultSet = statement.executeQuery();

            // Return ServiceRequest Objects
            List<FacilityMaintenanceSR> serviceRequests = new ArrayList<>();
            while (resultSet.next()) {
                FacilityMaintenanceSR serviceRequest = modifyServiceRequest(resultSet, new FacilityMaintenanceSR());
                if (serviceRequest != null) serviceRequests.add(serviceRequest);
            }
            return serviceRequests;

        } catch (SQLException e) {
            System.out.println("Query to FACILITY_MAINTENANCE_SR failed.");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get entry in the ServiceRequest and FACILITY_MAINTENANCE_SR table of the database with a given requestID
     * and convert it into a FacilityMaintenanceSR object.
     *
     * @param requestID The requestID of the service request.
     * @return FacilityMaintenanceSR object.
     */
    @Override
    public FacilityMaintenanceSR getServiceRequest(int requestID) {
        try {
            // Execute SELECT Query to join the parent table and child table attributes
            PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                    "SELECT SERVICE_REQUEST.*, FACILITY_MAINTENANCE_SR.* " +
                            "FROM SERVICE_REQUEST INNER JOIN FACILITY_MAINTENANCE_SR " +
                            "ON SERVICE_REQUEST.ID = FACILITY_MAINTENANCE_SR.ID " +
                            "WHERE SERVICE_REQUEST.ID = ?"
            );
            statement.setInt(1, requestID);
            ResultSet resultSet = statement.executeQuery();

            // Return Location Object
            if (resultSet.next()) return modifyServiceRequest(resultSet, new FacilityMaintenanceSR());
        } catch (SQLException e) {
            System.out.println("Query to database tables failed.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Insert entry into ServiceRequest Table of the DB, corresponding to the given ServiceRequest object.
     * A given FacilityMaintenanceSR specifically to the dedicated table.
     *
     * @param serviceRequest The ServiceRequest to be inserted into the DB via a corresponding entry.
     * @return If successful return the requestID, else -1.
     */

    @Override
    public int insertServiceRequest(FacilityMaintenanceSR serviceRequest) {
        try {
            ServiceRequestDAOImpl sRDAO = new ServiceRequestDAOImpl();
            int requestID = sRDAO.insertServiceRequest(serviceRequest);
            // If the SR can be added successfully to the parent table then we can add it to the child table.
            if (requestID != -1) {
                // Insert the child-unique attributes to the child table.
                PreparedStatement statement = DBManager.getInstance().connection.prepareStatement(
                        "INSERT INTO FACILITY_MAINTENANCE_SR VALUES(?, ?)"
                );
                statement.setInt(1, requestID);
                // Set child-specific attributes
                statement.setString(2, serviceRequest.getMaintenanceType().toString());
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
     * Update entry in both the Service_Requests and FACILITY_MAINTENANCE_SR table of DB corresponding to
     * the given ServiceRequest object.
     *
     * @param serviceRequest the ServiceRequest whose corresponding DB entry is to be updated.
     * @return If successful return true, else return false.
     */
    @Override
    public boolean updateServiceRequest(FacilityMaintenanceSR serviceRequest) {
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
                            "UPDATE FACILITY_MAINTENANCE_SR SET MaintenanceType = ? WHERE ID = ?"
                    );
                    statement.setString(1, serviceRequest.getMaintenanceType().toString());
                    statement.setInt(2, serviceRequest.getRequestID());
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
     * Will also delete entry in SECURITY_SR.
     *
     * @param serviceRequest The service request to be deleted from the DB.
     * @return True if successful.
     */
    @Override
    public boolean deleteServiceRequest(FacilityMaintenanceSR serviceRequest) {
        try {
            // Check if entry of same requestID exists in either table
            ServiceRequest serviceRequestInDB = getServiceRequest(serviceRequest.getRequestID());
            if (serviceRequestInDB != null) {
                // Execute DELETE Statement for base SR table and SECURITY_SR table
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
