package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;

import java.sql.Timestamp;

public class PatientTransportSRFactory extends ServiceRequestFactory<PatientTransportSR> {
    @Override
    public PatientTransportSR create() {
        PatientTransportSR patientTransportSR = new PatientTransportSR();
    
        Patient testPatient = new Patient();
        PatientDAO patientDAO = new PatientDAO();
        patientDAO.insert(testPatient);
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        patientTransportSR.setPatient(testPatient);
        patientTransportSR.setTransportTime(timestamp);

        return super.create(patientTransportSR);
    }
}
