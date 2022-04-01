package edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;

public class LabSystemSR extends ServiceRequest {
    protected String labType;   // TODO: Make Enum
    protected String patientID; // TODO: Link to Patient

    enum LabType {
        Blood_Sample,  //0
        Urine_Sample,  //1
        X_Ray,         //2
        Cat_Scan,      //3
        MRI;           //4
    }
    public String getLabType() {
        return labType;
    }

    public void setLabType(String labType) {
        this.labType = labType;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
