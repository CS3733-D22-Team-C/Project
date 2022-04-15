package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SERVICE_REQUEST")
public class ServiceRequest implements IDEntity {
    @Id
    @Column(name = "ID")
    protected String ID;
    
    @ManyToOne
    @JoinColumn(name = "CreatorID", referencedColumnName = "ID")
    protected Employee creator;
    
    @ManyToOne
    @JoinColumn(name = "AssigneeID", referencedColumnName = "ID")
    protected Employee assignee;
    
    @Column(name = "LocationID")
    protected String locationID;      // TODO: Link to Location

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

    @ManyToOne
    @JoinColumn(name = "ModifierID", referencedColumnName = "ID")
    protected Employee modifier;
    
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
        Security,
        Patient_Transport,
        Delivery_System
    }
    
    public ServiceRequest(){
        this.ID = UUID.randomUUID().toString();
    }
    
    public ServiceRequest(ServiceRequest serviceRequest) {
        this.ID = serviceRequest.getID();
        this.creator = serviceRequest.getCreator();
        this.assignee = serviceRequest.getAssignee();
        this.locationID = serviceRequest.getLocation();
        this.creationTimestamp = serviceRequest.getCreationTimestamp();
        this.status = serviceRequest.getStatus();
        this.priority = serviceRequest.getPriority();
        this.requestType = serviceRequest.getRequestType();
        this.description = serviceRequest.getDescription();
        this.modifier = serviceRequest.getModifier();
        this.modifiedTimestamp = serviceRequest.getModifiedTimestamp();
    }
    
    public ServiceRequest(String ID) {
        this.ID = ID;
    }
    
    public String getID() {
        return ID;
    }
    
    public void setID(String requestID) {
        this.ID = requestID;
    }
    
    public Employee getCreator() {
        return creator;
    }
    
    public void setCreator(Employee creator) {this.creator = creator;}
    
    public Employee getAssignee() {
        return assignee;
    }
    
    public void setAssignee(Employee assignee) {
        this.assignee = assignee;
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
    
    public Employee getModifier() {
        return modifier;
    }
    
    public void setModifier(Employee modifier) {
        this.modifier = modifier;
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
        return ID.equals(that.ID)
                && Objects.equals(creator, that.creator) //(creator == null ? that.creator == null : creator.equals(that.creator))
                && Objects.equals(assignee, that.assignee)
                && locationID.equals(that.locationID)
                && creationTimestamp.equals(that.creationTimestamp)
                && status == that.status
                && priority == that.priority
                && requestType == that.requestType
                && description.equals(that.description)
                && Objects.equals(modifier, that.modifier)
                && modifiedTimestamp.equals(that.modifiedTimestamp);
    }
}
