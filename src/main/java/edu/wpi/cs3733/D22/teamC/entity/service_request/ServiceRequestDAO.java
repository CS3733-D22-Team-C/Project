package edu.wpi.cs3733.D22.teamC.entity.service_request;

import java.util.List;

public interface ServiceRequestDAO {
    public List<ServiceRequest> getAllServiceRequests();
    public ServiceRequest getServiceRequest(String requestID);
    
    public boolean insertServiceRequest(ServiceRequest serviceRequest);
    public boolean updateServiceRequest(ServiceRequest serviceRequest);
    public boolean deleteServiceRequest(ServiceRequest serviceRequest);
}
