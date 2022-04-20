package edu.wpi.cs3733.D22.teamC.fileio.csv.service_request.translator;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.translator.TranslatorSR;
import edu.wpi.cs3733.D22.teamC.fileio.csv.CSVReader;

import java.sql.Timestamp;

public class TranslatorSRCSVReader extends CSVReader<TranslatorSR> {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

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
                object.setLocation(value);
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
                object.setPatientID(value);
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
