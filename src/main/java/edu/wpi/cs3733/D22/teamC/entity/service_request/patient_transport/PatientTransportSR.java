package edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PATIENT_TRANSPORT_SR")
public class PatientTransportSR extends ServiceRequest {
    
    @ManyToOne
    @JoinColumn(name = "PatientID", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Patient patient;

    @Column(name = "TransportTime")
    protected Timestamp transportTime;

    public PatientTransportSR(){

    }

    public PatientTransportSR(ServiceRequest serviceRequest){
        super(serviceRequest);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Timestamp getTransportTime() {
        return this.transportTime;
    }

    public void setTransportTime(Timestamp transportTime) {
        this.transportTime = transportTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PatientTransportSR that = (PatientTransportSR) o;
        return Objects.equals(patient.getID(), that.patient.getID()) && Objects.equals(transportTime, that.transportTime);
    }

}
