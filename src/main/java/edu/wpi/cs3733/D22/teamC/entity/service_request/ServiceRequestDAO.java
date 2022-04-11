package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

import java.util.List;

/**
 * Wrapper for ServiceRequest handling
 */
public class ServiceRequestDAO extends DAO<ServiceRequest> {
    
    protected Class<ServiceRequest> classType() {
        return ServiceRequest.class;
    }
    
    /**
     * Return a list of Service Requests associated with a given Location.
     * @param locationID The UUID of the location.
     * @return A list of ServiceRequest objects.
     */
    public List<ServiceRequest> getAllSRByLocation(String locationID) {
        return HibernateManager.filterQuery("select q from " + classType().getName() +
                " q where q.locationID = '" + locationID + "'");
    }

}
