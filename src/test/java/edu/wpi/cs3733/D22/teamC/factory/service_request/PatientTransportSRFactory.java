package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;

import java.sql.Timestamp;

public class PatientTransportSRFactory extends ServiceRequestFactory<PatientTransportSR> {
    @Override
    public PatientTransportSR create() {
        PatientTransportSR patientTransportSR = new PatientTransportSR();

        String patientID = "alskfj";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        patientTransportSR.setPatientID(patientID);
        patientTransportSR.setTransportTime(timestamp);

        return super.create(patientTransportSR);
    }
}
