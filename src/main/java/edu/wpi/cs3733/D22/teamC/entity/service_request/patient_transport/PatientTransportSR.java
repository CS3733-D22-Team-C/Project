package edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.models.patient.PatientSelectorWindow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PATIENT_TRANSPORT_SR")
public class PatientTransportSR extends ServiceRequest {

    @Column(name = "PatientID")
    protected String patientID;

    @Column(name = "TransportTime")
    protected Timestamp transportTime;

    public PatientTransportSR(){

    }

    public PatientTransportSR(ServiceRequest serviceRequest){
        super(serviceRequest);
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
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
        return Objects.equals(patientID, that.patientID) && Objects.equals(transportTime, that.transportTime);
    }

}
