package edu.wpi.cs3733.D22.teamC.entity.service_request;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SERVICE_REQUEST")
public class ServiceRequest {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    protected int requestID;
    
    protected String creatorID;     // TODO: Link to Employee
    
    protected String assigneeID;    // TODO: Link to Employee
    
    protected String locationID;      // TODO: Link to Location
    
    @CreationTimestamp
    protected Timestamp creationTimestamp;
    
    @Enumerated(EnumType.STRING)
    protected Status status;
    
    @Enumerated(EnumType.STRING)
    protected Priority priority;
    
    @Enumerated(EnumType.STRING)
    protected RequestType requestType;
    
    protected String description;
    
    protected String modifierID;
    
    @UpdateTimestamp
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
        this.locationID = serviceRequest.getLocation();
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
        return locationID;
    }

    public void setLocation(String location) {
        this.locationID = location;
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
