package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.patient.Patient;
import edu.wpi.cs3733.D22.teamC.entity.patient.PatientDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

import java.sql.Timestamp;

public class PatientTransportSRCSVReader extends CSVReader<PatientTransportSR> {
    
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final PatientDAO patientDAO = new PatientDAO();
   
    /**
     * Maps ServiceRequest (header, value) pairs to a value to change for the object.
     * @param serviceRequest The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected PatientTransportSR parseAttribute(PatientTransportSR serviceRequest, String header, String value) {
        switch(header) {
            case "requestID":
                serviceRequest.setID(value);
                break;
            case "creatorID":
                Employee creator = employeeDAO.getByID(value);
                serviceRequest.setCreator(creator);
                break;
            case "assigneeID":
                Employee assignee = employeeDAO.getByID(value);
                serviceRequest.setAssignee(assignee);
                break;
            case "location":
                serviceRequest.setLocation(value);
                break;
            case "creationTimestamp":
                serviceRequest.setCreationTimestamp(Timestamp.valueOf(value));

                break;
            case "status":
                serviceRequest.setStatus(ServiceRequest.Status.valueOf(value));
                break;
            case "priority":
                serviceRequest.setPriority(ServiceRequest.Priority.valueOf(value));
                break;
            case "requestType":
                serviceRequest.setRequestType(ServiceRequest.RequestType.valueOf(value));
                break;
            case "description":
                serviceRequest.setDescription(value);
                break;
            case "patientID":
                Patient patient = patientDAO.getByID(value);
                serviceRequest.setPatient(patient);
                break;
            case "transportTime":
                serviceRequest.setTransportTime(Timestamp.valueOf(value));
                break;
            case "modifierID":
                Employee modifier = employeeDAO.getByID(value);
                serviceRequest.setModifier(modifier);
                break;
            case "modifiedTimestamp":
                serviceRequest.setModifiedTimestamp(Timestamp.valueOf(value)); //TODO: Handle the modified time
                break;
        }
        return serviceRequest;
    }

    @Override
    protected PatientTransportSR createObject() {
        return new PatientTransportSR();
    }
}
