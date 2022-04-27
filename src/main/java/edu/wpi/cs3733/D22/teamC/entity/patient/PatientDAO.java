package edu.wpi.cs3733.D22.teamC.entity.patient;

import edu.wpi.cs3733.D22.teamC.HibernateManager;
import edu.wpi.cs3733.D22.teamC.entity.generic.DAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;

import java.util.List;

public class PatientDAO extends DAO<Patient> {
    @Override
    protected Class<Patient> classType() {
        return Patient.class;
    }

    /**
     * finds the list of equipment at designated location
     * @param locationID the idenitifer of the location
     * @return the list of equipments at this location
     */
    public List<Patient> getPatientByLocation(String locationID) {
        return HibernateManager.filterQuery("select q from " + classType().getName() +
                " q where q.location = '" + locationID + "'");
    }
}
