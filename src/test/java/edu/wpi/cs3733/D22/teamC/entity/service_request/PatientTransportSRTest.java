package edu.wpi.cs3733.D22.teamC.entity.service_request;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSRDAO;
import edu.wpi.cs3733.D22.teamC.factory.service_request.PatientTransportSRFactory;

public class PatientTransportSRTest extends DAOTest<PatientTransportSR> {
    public PatientTransportSRTest() {
        super(new PatientTransportSRFactory(), new PatientTransportSRDAO());
    }
}
