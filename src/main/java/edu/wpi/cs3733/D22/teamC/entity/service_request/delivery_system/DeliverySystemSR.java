package edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "DELIVERY_SYSTEM_SR")
public class DeliverySystemSR extends ServiceRequest {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "DeliveryType")
    protected DeliveryType deliveryType;
    
    @ManyToOne
    @JoinColumn(name = "PatientID", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Patient patient;
    
    public enum DeliveryType {
        Food,
        Beverage,
        Bedding,
        Flowers,
        Gift
    }
    
    public DeliverySystemSR() {}
    
    public DeliverySystemSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    }
    
    public DeliveryType getDeliveryType() {
        return deliveryType;
    }
    
    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
    
    public Patient getPatient() {
        return patient;
    }
    
    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeliverySystemSR that = (DeliverySystemSR) o;
        return deliveryType == that.deliveryType && patient.getID().equals(that.patient.getID());
    }
}
