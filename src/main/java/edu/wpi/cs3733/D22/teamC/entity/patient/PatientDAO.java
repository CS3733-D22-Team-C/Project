package edu.wpi.cs3733.D22.teamC.entity.patient;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;

public class PatientDAO extends DAO<Patient> {
    @Override
    protected Class<Patient> classType() {
        return Patient.class;
    }
}
