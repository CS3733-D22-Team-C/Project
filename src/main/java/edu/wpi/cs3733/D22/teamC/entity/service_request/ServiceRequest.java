package edu.wpi.cs3733.D22.teamC.entity.service_request;

import java.sql.Time;
import java.sql.Date;
import java.sql.Timestamp;

public class ServiceRequest {
    protected String requestID;
    protected String creatorID;     // TODO: Link to Employee
    protected String assigneeID;    // TODO: Link to Employee
    protected String location;      // TODO: Link to Location
    protected Timestamp creationTimestamp;
    protected Status status;
    protected Priority priority;
    protected RequestType requestType;
    protected String description;

    public enum Status {
        Blank,      //0
        Processing, //1
        Done;       //2
    }

    public enum Priority {
        Low,
        Medium,
        High;
    }

    public enum RequestType {
        Medical_Equipment_SR,
        Facility_Maintenance_SR,
        Lab_System_SR,
        Medicine_Delivery_SR,
        Sanitation_SR,
        Security_SR;
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
    }

    public ServiceRequest(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getAssigneeID() {
        return assigneeID;
    }

    public void setAssigneeID(String assigneeID) {
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

}
