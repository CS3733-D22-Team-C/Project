package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import javax.persistence.*;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

import java.util.Objects;

@Entity
@Table(name = "LAB_SYSTEM_SR")
public class LabSystemSR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
    @Column(name = "LabType")
    protected LabType labType;

    @Column(name = "PatientID")
    protected String patientID; // TODO: Link to Patient

    public enum LabType {
        Blood_Sample,
        Urine_Sample,
        X_Ray,
        Cat_Scan,
        MRI
    }
    
    public LabSystemSR() {}
    
    public LabSystemSR(ServiceRequest serviceRequest) {
        super(serviceRequest);
    } 

    public LabType getLabType() {
       return labType;
   }

    public void setLabType(LabType labType) {
        this.labType = labType;
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
        LabSystemSR that = (LabSystemSR) o;
        return labType == that.labType && patientID.equals(that.patientID);
    }
}
