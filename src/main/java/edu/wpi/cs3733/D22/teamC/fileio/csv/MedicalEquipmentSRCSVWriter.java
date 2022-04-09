package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

public class MedicalEquipmentSRCSVWriter extends CSVWriter<MedicalEquipmentSR> {
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
                "equipType",
                "equipID"
        };
    }
    /**
     * Maps headers to a value to get from the object.
     * @param serviceRequest The object to be read from.
     * @param header The header to be mapped to an attribute.
     * @return The retrieved value to be output to the CSV.
     */
    @Override
    protected String compileAttribute(MedicalEquipmentSR serviceRequest, String header) {
        String output = "";
        switch (header) {
            case "requestID":
                output = serviceRequest.getRequestID();
                break;
            case "creatorID":
                output = serviceRequest.getCreatorID();
                break;
            case "assigneeID":
                output = serviceRequest.getAssigneeID();
                break;
            case "location":
                output = serviceRequest.getLocation();
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
            case "equipType":
                output = serviceRequest.getEquipmentType().toString();
                break;
            case "equipID":
                output = serviceRequest.getEquipmentID();
                break;
            default:
                break;
        }
        return output;
    }


}
