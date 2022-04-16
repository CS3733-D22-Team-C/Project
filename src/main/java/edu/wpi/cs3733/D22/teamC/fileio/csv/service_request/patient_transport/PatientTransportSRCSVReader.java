package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

public class PatientTransportSRCSVReader extends CSVReader<PatientTransportSR> {
    @Override
    protected PatientTransportSR parseAttribute(PatientTransportSR object, String header, String value) {
        return null;
    }

    @Override
    protected PatientTransportSR createObject() {
        return null;
    }
}
