package edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

public class PatientTransportSRDAO extends DAO<PatientTransportSR> {

    @Override
    protected Class<PatientTransportSR> classType() {
        return PatientTransportSR.class;
    }
}
