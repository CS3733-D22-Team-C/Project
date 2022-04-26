package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.generic.IDEntity;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SERVICE_REQUEST")
@OnDelete(action = OnDeleteAction.CASCADE)
public class ServiceRequest implements IDEntity {
    @Id
    @Column(name = "ID")
    protected String ID;
    
    @Column(name = "Number", columnDefinition = "int NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1)")
    @Generated(GenerationTime.ALWAYS)
    protected int number;
    
    @ManyToOne
    @JoinColumn(name = "CreatorID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "fk_srCreator",
            foreignKeyDefinition = "FOREIGN KEY (CreatorID) REFERENCES EMPLOYEE (ID) ON DELETE SET NULL"))
    protected Employee creator;
    
    @ManyToOne
    @JoinColumn(name = "AssigneeID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "fk_srAssignee",
            foreignKeyDefinition = "FOREIGN KEY (AssigneeID) REFERENCES EMPLOYEE (ID) ON DELETE SET NULL"))
    protected Employee assignee;
    
    @ManyToOne
    @JoinColumn(name = "LocationID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "fk_srLocation",
            foreignKeyDefinition = "FOREIGN KEY (LocationID) REFERENCES LOCATION (ID) ON DELETE SET NULL"))
    protected Location location;

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
    @JoinColumn(name = "ModifierID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "fk_srModifier", 
            foreignKeyDefinition = "FOREIGN KEY (ModifierID) REFERENCES EMPLOYEE (ID) ON DELETE SET NULL"))
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
        Translator,
        Patient_Transport,
        Laundry,
        Delivery_System
    }
    
    public ServiceRequest(){
        this.ID = UUID.randomUUID().toString();
    }
    
    public ServiceRequest(ServiceRequest serviceRequest) {
        this.ID = serviceRequest.getID();
        this.number = serviceRequest.getNumber();
        this.creator = serviceRequest.getCreator();
        this.assignee = serviceRequest.getAssignee();
        this.location = serviceRequest.getLocation();
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
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
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
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
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
                && location.getID().equals(that.location.getID())
                && creationTimestamp.equals(that.creationTimestamp)
                && status == that.status
                && priority == that.priority
                && requestType == that.requestType
                && description.equals(that.description)
                && Objects.equals(modifier, that.modifier)
                && modifiedTimestamp.equals(that.modifiedTimestamp);
    }
    
    /**
     * Output the number attribute with proper left padding and an octothorp
     * @return Formatted string.
     */
    @Override
    public String toString() {
        return "#" + String.format("%010d", number);
    }
}
