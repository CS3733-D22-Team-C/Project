package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVWriter;

public class LabSystemSRCSVWriter extends CSVWriter<LabSystemSR> {

    /**
     * Manually define headers of attributes output to CSV.
     * @return The array of headers to be output to CSV.
     */
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
                "labType",
                "patientID",
                "modifierID",
                "modifiedTimestamp"
        };
    }

    /**
     * Maps headers to a value to get from the object.
     * @param serviceRequest The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(LabSystemSR serviceRequest, String header) {
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
            case "labType":
                output = serviceRequest.getLabType().toString();
                break;
            case "patientID":
                output = serviceRequest.getPatient().getID();
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
