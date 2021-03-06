package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.translator;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

import java.sql.Timestamp;

public class TranslatorSRCSVReader extends CSVReader<TranslatorSR> {
    
    private final LocationDAO locationDAO = new LocationDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    
    
    @Override
    protected TranslatorSR parseAttribute(TranslatorSR object, String header, String value) {
        switch (header) {
            case "requestID":
                object.setID(value);
                break;
            case "creatorID":
                Employee creator = employeeDAO.getByID(value);
                object.setCreator(creator);
                break;
            case "assigneeID":
                Employee assignee = employeeDAO.getByID(value);
                object.setAssignee(assignee);
                break;
            case "locationID":
                Location location = locationDAO.getByID(value);
                object.setLocation(location);
                break;
            case "creationTimestamp":
                object.setCreationTimestamp(Timestamp.valueOf(value));
                break;
            case "status":
                object.setStatus(ServiceRequest.Status.valueOf(value));
                break;
            case "priority":
                object.setPriority(ServiceRequest.Priority.valueOf(value));
                break;
            case "requestType":
                object.setRequestType(ServiceRequest.RequestType.valueOf(value));
                break;
            case "description":
                object.setDescription(value);
                break;
            case "language":
                object.setLanguage(TranslatorSR.Language.valueOf(value));
                break;
            case "patientID":
                Patient patient = patientDAO.getByID(value);
                object.setPatient(patient);
                break;
            case "modifierID":
                Employee modifier = employeeDAO.getByID(value);
                object.setModifier(modifier);
                break;
            case "modifiedTimestamp":
                object.setModifiedTimestamp(Timestamp.valueOf(value));
                break;
        }
        return object;
    }

        @Override
        protected TranslatorSR createObject () {
            return new TranslatorSR();
        }
}
