package edu.wpi.cs3733.D22.teamC.factory.patient;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.factory.Factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class PatientFactory implements Factory<Patient> {
    private Random generator = new Random();
    //Instantiating the SimpleDateFormat class
    SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public Patient create() {
        Patient patient = new Patient();

        try {
            String firstName = "Wilson";
            String lastName = "Wong";
            String phone = "1321231312";
            Date dob = format1.parse("05/01/1999");
            String emergencyContact = "1321231312";
    
            Location location = new Location();
            LocationDAO testDao = new LocationDAO();
            testDao.insert(location);

            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setPhone(phone);
            patient.setLocation(location);
            patient.setEmergencyContact(emergencyContact);
            patient.setDOB(dob);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return patient;
    }
}
