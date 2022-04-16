package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class PatientTransportSRCSVWriter extends CSVWriter<PatientTransportSR> {
    @Override
    protected String[] compileHeaders() {
        return new String[0];
    }

    @Override
    protected String compileAttribute(PatientTransportSR object, String header) {
        return null;
    }
}
