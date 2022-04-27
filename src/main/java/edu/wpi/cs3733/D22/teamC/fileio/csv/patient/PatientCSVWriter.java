package edu.wpi.cs3733.D22.teamC.fileio.csv.patient;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class PatientCSVWriter extends CSVWriter<Patient> {
    @Override
    protected String[] compileHeaders() {

        return new String[]{
                "ID",
                "FirstName",
                "LastName",
                "DOB",
                "Location",
                "Phone",
                "EmergencyContact"
        };
    }

    @Override
    protected String compileAttribute(Patient object, String header) {
        String output = "";
        switch(header){
            case "ID":
                output = object.getID();
                break;
            case "FirstName":
                output = object.getFirstName();
                break;
            case "LastName":
                output = object.getLastName();
                break;
            case "DOB":
                output = object.getDOB().toString();
                break;
            case "Location":
                Location location = object.getLocation();
                output = (location != null) ? location.getID() : "";
                break;
            case "Phone":
                output = object.getPhone();
                break;
            case "EmergencyContact":
                output = object.getEmergencyContact();
                break;
        }
        return output;
    }
}
