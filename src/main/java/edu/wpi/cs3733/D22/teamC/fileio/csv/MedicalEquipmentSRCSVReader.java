package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;

import java.sql.Timestamp;

public class MedicalEquipmentSRCSVReader extends CSVReader<MedicalEquipmentServiceRequest>{
    /**
     * Maps ServiceRequest (header, value) pairs to a value to change for the object.
     * @param serviceRequest The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected MedicalEquipmentServiceRequest parseAttribute(MedicalEquipmentServiceRequest serviceRequest, String header, String value){
        switch(header) {
            case "requestID":
                serviceRequest.setRequestID(value);
                break;
            case "creatorID":
                serviceRequest.setCreatorID(value);
                break;
            case "assigneeID":
                serviceRequest.setAssigneeID(value);
                break;
            case "location":
                serviceRequest.setLocation(value);
                break;
            case "creationTimestamp":
                serviceRequest.setCreationTimestamp(Timestamp.valueOf(value));
                break;
            case "status":
                serviceRequest.setStatus(value);
                break;
            case "priority":
                serviceRequest.setPriority(value);
                break;
            case "requestType":
                serviceRequest.setRequestType(value);
                break;
            case "description":
                serviceRequest.setDescription(value);
                break;
            case "equipID":
                serviceRequest.setEquipmentID(value);
                break;
            case "equipType":
                serviceRequest.setEquipmentType(value);
                break;
        }
        return serviceRequest;
    }

    @Override
    protected MedicalEquipmentServiceRequest createObject(){ return new MedicalEquipmentServiceRequest();}
}
