package edu.wpi.cs3733.D22.teamC.fileio.csv;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;

import java.sql.Timestamp;

public class MedicalEquipmentSRCSVReader extends CSVReader<MedicalEquipmentSR>{
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    /**
     * Maps ServiceRequest (header, value) pairs to a value to change for the object.
     * @param serviceRequest The object to be modified.
     * @param header The header to be mapped to an attribute.
     * @param value The value for the current attribute.
     * @return The Location modified with the value at the corresponding attribute.
     */
    @Override
    protected MedicalEquipmentSR parseAttribute(MedicalEquipmentSR serviceRequest, String header, String value){
        switch(header) {
            case "requestID":
                serviceRequest.setRequestID(value);
                break;
            case "creatorID":
                Employee employee = employeeDAO.getByID(value);
                serviceRequest.setCreator(employee);
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
            case "equipID":
                serviceRequest.setEquipmentID(value);
                break;
            case "equipType":
                serviceRequest.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(value));
                break;
        }
        return serviceRequest;
    }

    @Override
    protected MedicalEquipmentSR createObject(){ return new MedicalEquipmentSR();}
}
