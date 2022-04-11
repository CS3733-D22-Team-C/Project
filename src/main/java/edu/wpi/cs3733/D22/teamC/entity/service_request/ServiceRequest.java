package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SERVICE_REQUEST")
public class ServiceRequest {
    @Id
    @Column(name = "ID")
    protected String requestID;


    //@Column(name = "CreatorID")
    @ManyToOne
    @JoinColumn(name = "CreatorID", referencedColumnName = "ID")
    protected Employee creator;     // TODO: Link to Employee


    //@ManyToOne
    //@JoinColumn(name = "AssigneeID", referencedColumnName = "ID")
    @Column(name = "AssigneeID")
    protected String assigneeID;    // TODO: Link to Employee
    
    @Column(name = "LocationID")
    protected String locationID;      // TODO: Link to Location

    @CreationTimestamp
    @Column(name = "CreationTimestamp")
    protected Timestamp creationTimestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    protected Status status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "Priority")
    protected Priority priority;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "RequestType")
    protected RequestType requestType;
    
    @Column(name = "Description")
    protected String description;
    
    @Column(name = "ModifierID")
    protected String modifierID;

    @Column(name = "ModifiedTimestamp")
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
    
    public ServiceRequest(){
        this.requestID = UUID.randomUUID().toString();
    }
    
    public ServiceRequest(ServiceRequest serviceRequest) {
        this.requestID = serviceRequest.getRequestID();
        this.creator = serviceRequest.getCreator();
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
    
    public ServiceRequest(String requestID) {
        this.requestID = requestID;
    }
    
    public String getRequestID() {
        return requestID;
    }
    
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
    
    public Employee getCreator() {
        return creator;
    }
    
    public void setCreatorID(String creatorID) {
        this.creator = creator;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRequest that = (ServiceRequest) o;
        return requestID.equals(that.requestID)
                && creator.equals(that.creator)
                && assigneeID.equals(that.assigneeID)
                && locationID.equals(that.locationID)
                && creationTimestamp.equals(that.creationTimestamp)
                && status == that.status
                && priority == that.priority
                && requestType == that.requestType
                && description.equals(that.description)
                && modifierID.equals(that.modifierID)
                && modifiedTimestamp.equals(that.modifiedTimestamp);
    }
}
