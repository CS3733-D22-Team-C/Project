package edu.wpi.cs3733.D22.teamC.fileio.csv.patient;

import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatientCSVReader extends CSVReader<Patient> {

    @Override
    protected Patient parseAttribute(Patient patient, String header, String value) {
        try {
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
                    patient.setDOB(new SimpleDateFormat("mm/dd/yyyy").parse(value));
                    break;
                case "Location":
                    patient.setLocationID(value);
                    break;
                case "Phone":
                    patient.setPhone(value);
                    break;
                case "EmergencyContact":
                    patient.setEmergencyContact(value);
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return patient;
    }

    @Override
    protected Patient createObject() {
        return new Patient();
    }
}
