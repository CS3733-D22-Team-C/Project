
package edu.wpi.cs3733.D22.teamC.entity.service_request.laundry;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.sanitation.SanitationSR;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.*;

//We use javax persistence tags to make our columns compatible with the database table for this entity

@Entity
@Table(name = "LAUNDRY_SR")
public class LaundrySR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
    @Column(name = "LaundryType")
    private LaundryType laundryType;

    public enum LaundryType {
        Bedding,
        Towel,
        Gown,
        Blanket
    }

    @Column(name = "Quantity")
    private String quantity;

    public LaundrySR() {}

    public LaundrySR(ServiceRequest serviceRequest)
    { super(serviceRequest); }

    public LaundryType getLaundryType(){ return laundryType;}

    public void setLaundryType(LaundryType laundryType) {
        this.laundryType = laundryType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LaundrySR that = (LaundrySR) o;
        return laundryType == that.laundryType;
    }
}




