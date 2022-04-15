package edu.wpi.cs3733.D22.teamC.entity.patient;

import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAOTest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.patient.PatientFactory;

public class PatientDAOTest extends DAOTest<Patient> {
    public PatientDAOTest() {
        super(new PatientFactory(), new PatientDAO());
    }
}
