package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.SessionManager;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Wrapper for ServiceRequest handling
 */
public class ServiceRequestDAO extends DAO<ServiceRequest> {
    
    protected Class<ServiceRequest> classType() {
        return ServiceRequest.class;
    }
    
    /**
     * Override of delete to handle reverse-cascade triggers.
     * @param obj ServiceRequest
     * @return True if successful.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean delete(Object obj) {
        Session session = SessionManager.getSession();
        try {
            session.beginTransaction();
            ServiceRequest castSR = classType().cast(obj);
            Query<ServiceRequest> query = session.createQuery("delete from " + classType().getName() + 
                    " s where s.ID = '" + castSR.getID() + "'");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
        }
        return false;
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

    public List<ServiceRequest> getAllSRByAssignee(String assigneeID) {
        return HibernateManager.filterQuery("select q from " + classType().getName() +
                " q where q.assignee = '" + assigneeID + "'");
    }

    public List<ServiceRequest> getAllSRByCreator(String creatorID) {
        return HibernateManager.filterQuery("select q from " + classType().getName() +
                " q where q.creator = '" + creatorID + "'");
    }
}
