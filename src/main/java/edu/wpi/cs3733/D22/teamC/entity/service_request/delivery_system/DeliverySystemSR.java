package edu.wpi.cs3733.D22.teamC.entity.service_request.delivery_system;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import javax.persistence.*;

@Entity
@Table(name = "DELIVERY_SYSTEM_SR")
public class DeliverySystemSR extends ServiceRequest {
    
    @Enumerated(EnumType.STRING)
    @Column(name = "DeliveryType")
    protected DeliveryType deliveryType;
    
    @Column(name = "PatientID")
    protected String patientID;
    
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
    
    public String getPatientID() {
        return patientID;
    }
    
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeliverySystemSR that = (DeliverySystemSR) o;
        return deliveryType == that.deliveryType && patientID.equals(that.patientID);
    }
}
