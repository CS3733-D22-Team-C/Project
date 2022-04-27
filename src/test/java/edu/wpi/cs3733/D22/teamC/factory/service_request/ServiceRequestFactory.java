package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.location.LocationFactory;

import java.sql.Timestamp;
import java.util.Random;

public class ServiceRequestFactory<T extends ServiceRequest> implements Factory<T> {
    protected Random generator = new Random();

    @SuppressWarnings("unchecked")
    public T create() {
        T serviceRequest = (T) new ServiceRequest();
        return create(serviceRequest);
    }

    public T create(T serviceRequest) {
        Employee creator = null;
        Employee assignee = null;
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.values()[generator.nextInt(ServiceRequest.Status.values().length)];
        ServiceRequest.Priority priority = ServiceRequest.Priority.values()[generator.nextInt(ServiceRequest.Priority.values().length)];
        String description = "jsjsjsjsjskssksksksksprrrr";
        Employee modifier = null;
        Timestamp modifiedTimeStamp = new Timestamp(System.currentTimeMillis());
    
        Location location = new Location();
        LocationDAO testDao = new LocationDAO();
        testDao.insert(location);

        serviceRequest.setCreator(creator);
        serviceRequest.setAssignee(assignee);
        serviceRequest.setLocation(location);
        serviceRequest.setCreationTimestamp(creationTimestamp);
        serviceRequest.setStatus(status);
        serviceRequest.setPriority(priority);
        serviceRequest.setDescription(description);
        serviceRequest.setModifier(modifier);
        serviceRequest.setModifiedTimestamp(modifiedTimeStamp);
        
        return serviceRequest;
    }
}
