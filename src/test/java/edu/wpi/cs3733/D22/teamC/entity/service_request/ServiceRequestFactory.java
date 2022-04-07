package edu.wpi.cs3733.D22.teamC.entity.service_request;

import java.sql.Timestamp;
import java.util.Random;

public class ServiceRequestFactory<T extends ServiceRequest> {
    private Random generator = new Random();
    public T create() {
        T serviceRequest = (T) new ServiceRequest();
        return create(serviceRequest);
    }

    public Random getGenerator() {
        return generator;
    }
    public T create(T serviceRequest) {
        String creatorID = String.valueOf(generator.nextInt(200000));
        String assigneeID = String.valueOf(generator.nextInt(200000));
        String locationID = String.valueOf(generator.nextInt(200000));
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        int ordinal = generator.nextInt(3);
        ServiceRequest.Status status = ServiceRequest.Status.values()[ordinal];
        ServiceRequest.Priority priority = ServiceRequest.Priority.values()[ordinal];
        String description = "jsjsjsjsjskssksksksksprrrr";

        serviceRequest.setCreatorID(creatorID);
        serviceRequest.setAssigneeID(assigneeID);
        serviceRequest.setLocation(locationID);
        serviceRequest.setCreationTimestamp(creationTimestamp);
        serviceRequest.setStatus(status);
        serviceRequest.setPriority(priority);
        serviceRequest.setDescription(description);
        
        return serviceRequest;
    }
}
