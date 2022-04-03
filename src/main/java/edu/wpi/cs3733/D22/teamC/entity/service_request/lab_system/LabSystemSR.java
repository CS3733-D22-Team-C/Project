package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class LabSystemSR extends ServiceRequest {
    protected LabType labType;
    protected String patientID; // TODO: Link to Patient

   public enum LabType {
        Blood_Sample,
        Urine_Sample,
        X_Ray,
        Cat_Scan,
        MRI
    }
    public LabType getLabType() {return labType;}

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
