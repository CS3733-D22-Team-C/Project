package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.lab_system;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class LabSystemSRFormEvaluator extends ServiceRequestFormEvaluator {

    public LabSystemSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getLabSystemSRValidationTestResult(int locationID, int assigneeID, SingleSelectionModel status, SingleSelectionModel priority, SingleSelectionModel labType, int patientID)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(locationID, assigneeID, status, priority));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(locationID));
        errorList.add(checkLabTypeFilled(labType));
        errorList.add(checkPatientIDFilled(patientID));

        return errorList;
    }

    /**
     * Determine if the Lab Type of Lab System Service Request has been filled
     * @param labType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkLabTypeFilled(SingleSelectionModel labType)
    {
        if(labType.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[7];
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine if the Patient ID of Lab System Service Request has been filled.
     * @param patientID
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkPatientIDFilled(int patientID)
    {
        int patientIDLength = (int)(Math.log10(patientID)+1);

        if(patientIDLength == 0)
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[8];
        }
        else
        {
            return null;
        }
    }
}