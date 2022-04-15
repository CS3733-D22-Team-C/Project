
package edu.wpi.cs3733.D22.teamC.entity.service_request.laundry;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.*;

//We use javax persistence tags to make our columns compatible with the database table for this entity

@Entity
@Table(name = "LAUNDRY")
public class LaundrySR extends ServiceRequest {

    @Id
    @Column(name = "washed")
    private String washed;

    @Column(name = "quantity")
    private String firstName;

    public LaundrySR()
    { super(); }

    public LaundrySR(ServiceRequest serviceRequest)
    { super(serviceRequest); }

}




