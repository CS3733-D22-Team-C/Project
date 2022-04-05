package edu.wpi.cs3733.D22.teamC.entity.service_request;

import java.sql.Timestamp;

public class ServiceRequest {
    protected int requestID;
    protected int creatorID;     // TODO: Link to Employee
    protected int assigneeID;    // TODO: Link to Employee
    protected String location;      // TODO: Link to Location
    protected Timestamp creationTimestamp;
    protected Status status;
    protected Priority priority;
    protected RequestType requestType;
    protected String description;
    protected String modifierID;
    protected Timestamp modifiedTimestamp;

    public enum Status {
        Blank,
        Processing,
        Done
    }

    public enum Priority {
        Low,
        Medium,
        High
    }

    public enum RequestType {
        Medical_Equipment,
        Facility_Maintenance,
        Lab_System,
        Medicine_Delivery,
        Sanitation,
        Security
    }

    public ServiceRequest(){}
    
    public ServiceRequest(ServiceRequest serviceRequest) {
        this.requestID = serviceRequest.getRequestID();
        this.creatorID = serviceRequest.getCreatorID();
        this.assigneeID = serviceRequest.getAssigneeID();
        this.location = serviceRequest.getLocation();
        this.creationTimestamp = serviceRequest.getCreationTimestamp();
        this.status = serviceRequest.getStatus();
        this.priority = serviceRequest.getPriority();
        this.requestType = serviceRequest.getRequestType();
        this.description = serviceRequest.getDescription();
        this.modifierID = serviceRequest.getModifierID();
        this.modifiedTimestamp = serviceRequest.getModifiedTimestamp();
    }

    public ServiceRequest(int requestID) {
        this.requestID = requestID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public int getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(int assigneeID) {
        this.assigneeID = assigneeID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) { this.status = status;}

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getModifierID() {
        return modifierID;
    }
    
    public void setModifierID(String modifierID) {
        this.modifierID = modifierID;
    }
    
    public Timestamp getModifiedTimestamp() {
        return modifiedTimestamp;
    }
    
    public void setModifiedTimestamp(Timestamp modifiedTimestamp) {
        this.modifiedTimestamp = modifiedTimestamp;
    }
}
