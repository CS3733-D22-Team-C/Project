package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.patient_transport;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.patient_transport.PatientTransportSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class PatientTransportSRCSVWriter extends CSVWriter<PatientTransportSR> {
    @Override
    protected String[] compileHeaders() {
        return new String[]{
                "requestID",
                "creatorID",
                "assigneeID",
                "location",
                "creationTimestamp",
                "status",
                "priority",
                "requestType",
                "description",
                "patientID",
                "transportTime",
                "modifierID",
                "modifiedTimestamp"
        };
    }

    @Override
    protected String compileAttribute(PatientTransportSR serviceRequest, String header) {
        String output = "";
        switch (header) {
            case "requestID":
                output = serviceRequest.getID();
                break;
            case "creatorID":
                Employee employee = serviceRequest.getCreator();
                output = (employee != null) ? employee.getID() : "";
                break;
            case "assigneeID":
                Employee assignee = serviceRequest.getAssignee();
                output = (assignee != null) ? assignee.getID() : "";
                break;
            case "location":
                Location location = serviceRequest.getLocation();
                output = (location != null) ? location.getID() : "";
                break;
            case "creationTimestamp":
                output = (serviceRequest.getCreationTimestamp() == null) ? "" : serviceRequest.getCreationTimestamp().toString();
                break;
            case "status":
                output = serviceRequest.getStatus().toString();
                break;
            case "priority":
                output = serviceRequest.getPriority().toString();
                break;
            case "requestType":
                output = serviceRequest.getRequestType().toString();
                break;
            case "description":
                output = serviceRequest.getDescription();
                break;
            case "patientID":
                output = serviceRequest.getPatient().getID();
                break;
            case "transportTime":
                output = serviceRequest.getTransportTime().toString();
                break;
            case "modifierID":
                Employee modifier = serviceRequest.getModifier();
                output = (modifier != null) ? modifier.getID() : "";
                break;
            case "modifiedTimestamp":
                output = (serviceRequest.getModifiedTimestamp() == null) ? "" : serviceRequest.getModifiedTimestamp().toString();
                break;
            default:
                break;
        }
        return output;
    }
}
