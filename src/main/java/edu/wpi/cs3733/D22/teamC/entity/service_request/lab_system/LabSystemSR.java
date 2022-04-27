package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import javax.persistence.*;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "LAB_SYSTEM_SR")
public class LabSystemSR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
    @Column(name = "LabType")
    protected LabType labType;
    
    @ManyToOne
    @JoinColumn(name = "PatientID", referencedColumnName = "ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Patient patient;

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
        LabSystemSR that = (LabSystemSR) o;
        return labType == that.labType && patient.getID().equals(that.patient.getID());
    }
}
