package edu.wpi.cs3733.D22.teamC.fileio.csv.patient;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatientCSVReader extends CSVReader<Patient> {
    
    private final LocationDAO locationDAO = new LocationDAO();
    
    @Override
    protected Patient parseAttribute(Patient patient, String header, String value) {
            switch (header) {
                case "ID":
                    patient.setID(value);
                    break;
                case "FirstName":
                    patient.setFirstName(value);
                    break;
                case "LastName":
                    patient.setLastName(value);
                    break;
                case "DOB":
                    try {
                        patient.setDOB(new SimpleDateFormat("YYYY-MM-DD").parse(value));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Location":
                    Location location = locationDAO.getByID(value);
                    patient.setLocation(location);
                    break;
                case "Phone":
                    patient.setPhone(value);
                    break;
                case "EmergencyContact":
                    patient.setEmergencyContact(value);
                    break;
            }

        return patient;
    }

    @Override
    protected Patient createObject() {
        return new Patient();
    }
}
