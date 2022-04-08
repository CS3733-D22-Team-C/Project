package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import javax.persistence.*;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table (name = "LAB SYSTEM")
public class LabSystemSR extends ServiceRequest {

    @Enumerated(EnumType.STRING)
    protected LabType labType;

    protected String patientID; // TODO: Link to Patient

    public enum LabType {
        Blood_Sample,
        Urine_Sample,
        X_Ray,
        Cat_Scan,
        MRI
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
}
